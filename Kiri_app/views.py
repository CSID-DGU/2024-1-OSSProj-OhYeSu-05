from django.shortcuts import render, redirect
# Create your views here.
from rest_framework import viewsets
from .serializers import UserSerializer, RoomSerializer, ChatSerializer
# 시리얼라이저 profile, userpref, match, report, block 추가
from .serializers import ProfileSerializer, UPrefSerializer, MatchSerializer, ReportSerializer, BlockSerializer
from .models import AppUser, Room, Chat
# 모델 profile, userpref, match, report, block 추가
from .models import Profile, UserPref, Match, Report, Block
from rest_framework import status
from rest_framework.decorators import api_view
from django.http.response import HttpResponse
from rest_framework.response import Response
from django.contrib import auth

from django.contrib.auth.models import User
from django.contrib.auth import authenticate,login
from django.contrib.sessions.models import Session
import numpy as np,random


# from django.contrib.auth import authenticate
#확인
class UserViewSet(viewsets.ModelViewSet):
    queryset = AppUser.objects.all()
    serializer_class = UserSerializer

    #회원가입 시 호출되는 함수
    @api_view(['POST'])
    def signup(request):
        id = request.data.get('id')
        password = request.data.get('password')
        name = request.data.get('name')
        # 학번 추가
        studentid = request.data.get('studentId')
        #회원가입시 아이디, 비번, 이름 등록

        # studentid 추가
        if id and password and name and studentid:
            try:
                # Check if a user with the provided username already exists
                existing_user = AppUser.objects.get(id = id)
                return Response({'success': 'False'}, status=status.HTTP_400_BAD_REQUEST)
            except AppUser.DoesNotExist:
                # Create a new user
                user_count = AppUser.objects.count() + 1
                # studentid 추가
                user = AppUser.objects.create(userID = user_count,id = id, password=password, name = name, studentId = studentid)
                room_count = Room.objects.count()
                roomID = '0' * room_count
                user.roomID = roomID
                user.save()
                return Response({'success': True}, status=status.HTTP_201_CREATED)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)


    #로그인 시 호출되는 함수
    @api_view(['POST'])
    def login_api(request):
        id = request.data.get('id')
        password = request.data.get('password')

        if id and password:
            try:
                # 로그인 성공
                existing_user = AppUser.objects.get(id=id, password=password)
                user_id = existing_user.id
                request.session['user_id'] = user_id
                user_id1=request.session.get('user_id')
                print(user_id1)
                return Response({'success': True, 'user_id': user_id}, status=status.HTTP_200_OK)
            except AppUser.DoesNotExist:
                #로그인 실패
                return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)

    @api_view(['POST'])
    def get_user_id(request):
        # 세션에서 사용자 ID 가져오기
        user_id = request.session.get('user_id')
        print(user_id)
        if user_id:
            return Response({'user_id': user_id}, status=status.HTTP_200_OK)
        else:
            return Response({'success': False}, status=status.HTTP_404_NOT_FOUND)

    # KSH : 로그아웃 기능 구현 예정
    @api_view(['POST'])
    def logout(request):
        request.session.flush()
        return Response({'success': True}, status=status.HTTP_200_OK)

#수치로의 변환 함수들
def convert_genre(장르):
    if 장르 == '모험':
        return 0
    elif 장르 == '코믹':
        return 1
    elif 장르 =='판타지':
        return 2
    elif 장르 == '로맨스':
        return 3
    elif 장르 == '스릴러':
        return 4
    elif 장르 == '드라마':
        return 5
    elif 장르 == '호러':
        return 6
    elif 장르 == '공상과학':
        return 7
    elif 장르 == '미스테리':
        return 8
    elif 장르 == '액션':
        return 9
    else:
        return -1

def convert_difficulty(난이도):
    if 난이도 == '상':
        return 3
    elif 난이도 == '중':
        return 2
    elif 난이도 == '하':
        return 1
    else:
        return 0

def convert_fear(공포도):
    if 공포도 == '상':
        return 3
    elif 공포도 == '중':
        return 2
    elif 공포도 == '하':
        return 1
    else:
        return 0

def convert_activity(활동성):
    if 활동성 == '상':
        return 3
    elif 활동성 == '중':
        return 2
    elif 활동성 == '하':
        return 1
    else:
        return 0

    #ModelViewSet은 기본적으로 CRUD를 제공함.
class RoomViewSet(viewsets.ModelViewSet):
    queryset = Room.objects.all()
    serializer_class = RoomSerializer

    response_data = []

    @api_view(['POST'])
    def roomcreate(request):

        user_id = request.data.get('user_id')
        date = request.data.get('date')
        region = request.data.get('region')
        title = request.data.get('title')
        roomIntro = request.data.get('roomIntro')
        genre = request.data.get('genre')
        difficulty = request.data.get('difficulty')
        fear = request.data.get('fear')
        activity = request.data.get('activity')

        if date and region and title and roomIntro and difficulty and fear and activity:

            room_count = Room.objects.count() + 1
            room = Room.objects.create(
                roomID = room_count,
                date=date,
                region=region,
                title=title,
                roomIntro=roomIntro,
                genre=convert_genre(genre),
                difficulty=convert_difficulty(difficulty),
                fear=convert_fear(fear),
                activity=convert_activity(activity)
            )

            #user_id = request.session.get('user_id')
            print(user_id)
            if user_id:
                try:
                    room_creator = AppUser.objects.get(id=user_id)
                    room_index = room.roomID              # 방의 인덱스를 가져옴 (0부터 시작)
                    users = AppUser.objects.all()
                    for user in users:
                        roomID = user.roomID            # 사용자의 roomID 가져오기
                        if user == room_creator:        # 방을 만든 사용자인 경우
                            roomID = roomID[:room_index] + '1'  # 해당 위치에 1을 추가
                        else:                           # 방을 만든 사용자가 아닌 경우
                            roomID = roomID[:room_index] + '0' # 해당 위치에 0을 추가
                        user.roomID = roomID            # 사용자의 roomID를 업데이트
                        user.save()
                    return Response({'success': True}, status=status.HTTP_201_CREATED)
                except AppUser.DoesNotExist:
                    return Response({'success1': False}, status=status.HTTP_404_NOT_FOUND)
            else:
                return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)

    @api_view(['POST'])
    def roomsearch(request):
        user_option = {
            'area1': request.data.get('area1'),
            'area2': request.data.get('area2'),
            'area3': request.data.get('area3'),
            'startdate': request.data.get('startdate'),
            'enddate': request.data.get('enddate'),
            'genre': request.data.get('genre'),
            'difficulty': request.data.get('difficulty'),
            'fear': request.data.get('fear'),
            'activity': request.data.get('activity')
        }

        user_option['startdate'] = int(user_option['startdate'].replace('.', ''))
        user_option['enddate'] = int(user_option['enddate'].replace('.', ''))

        genre_mapping = {
            '모험': 0,
            '코믹': 1,
            '판타지': 2,
            '로맨스': 3,
            '스릴러': 4,
            '드라마': 5,
            '호러': 6,
            '공상과학': 7,
            '미스테리': 8,
            '액션': 9
        }

        user_option['genre'] = genre_mapping.get(user_option['genre'], -1)

        #난이도 값 치환
        if user_option['difficulty'] == '상':
            user_option['difficulty'] = 3
        elif user_option['difficulty'] == '중':
            user_option['difficulty'] = 2
        elif user_option['difficulty'] == '하':
            user_option['difficulty'] = 1
        else:
            user_option['difficulty'] = None
        #공포도 값 치환
        if user_option['fear'] == '상':
            user_option['fear'] = 3
        elif user_option['fear'] == '중':
            user_option['fear'] = 2
        elif user_option['fear'] == '하':
            user_option['fear'] = 1
        else:
            user_option['fear'] = None
        #활동성 값 치환
        if user_option['activity'] == '상':
            user_option['activity'] = 3
        elif user_option['activity'] == '중':
            user_option['activity'] = 2
        elif user_option['activity'] == '하':
            user_option['activity'] = 1
        else:
            user_option['activity'] = None

        rooms = Room.objects.all()

        for room in rooms:
            room.date = int(room.date.replace('.', ''))



        filtered_rooms = []
        #filtered_rooms가 장르랑 난,공,활 유사도 측정해야햐는 방들

        for room in rooms:
            for i in range(1, 4):
                if room.region == user_option[f'area{i}']:
                    if room.date >= user_option['startdate'] and room.date <= user_option['enddate']:
                        filtered_rooms.append(room)
                    break  # 일치하는 경우를 찾았으므로 루프를 종료합니다.

        filtered_genre_similarity = []

        genre_similarity=[[1,0,0.6604,0,0,0,0,0.5007,0,0.7116],
                          [0,1,0,0.3459,0,0,0,0,0,0],
                          [0.6604,0,1,0,0,0,0.042,0.1507,0.0503,0.1823],
                          [0,0.3459,0,1,0,0.2879,0,0,0,0],
                          [0,0,0,0,1,0,0.6037,0.2711,0.7465,0.4714],
                          [0,0,0,0.288,0,1,0,0,0.0855,0],
                          [0,0,0.042,0,0.6037,0,1,0.365,0.3394,0],
                          [0.5007,0,0.1507,0,0.2711,0,0.365,1,0.0564,0.6076],
                          [0,0,0.0503,0,0.7465,0.0855,0.3394,0.0564,1,0],
                          [0.7116,0,0.1823,0,0.4714,0,0,0.6076,0,1]
                          ]

        if user_option['genre'] == -1:
            for room in filtered_rooms:
                new_array = [room.roomID, 0]
                filtered_genre_similarity.append(new_array)
        else:
            for room in filtered_rooms:
                column_index = room.genre
                similarity_value = genre_similarity[user_option['genre']][column_index]
                new_array = [room.roomID, similarity_value]
                filtered_genre_similarity.append(new_array)

        #null값인 property 구별용 변수
        diff_null=0
        horr_null=0
        acti_null=0

        #어떤 property가 null값인지 구별
        if user_option.get('difficulty') is None:
            diff_null=1
        if user_option.get('fear') is None:
            horr_null=1
        if user_option.get('activity') is None:
            acti_null=1
        count = diff_null+horr_null+acti_null
        print("확인용 출력: ", diff_null, horr_null, acti_null)


        property_similarity=[]

        #null값인 property가 몇개인지 검사

        if count==0: #null값이 하나도 없을때

            #rooms배열에서 각 행마다 반복 -> 난공활 정보 가져오기
            for room in filtered_rooms:
                room_diff = room.difficulty
                room_horr = room.fear
                room_acti = room.activity

                #np용 배열로 저장
                room_vector = np.array([room_diff, room_horr, room_acti])
                user_vector = np.array([user_option['difficulty'], user_option['fear'], user_option['activity']])

                #유클리드 거리 계산
                euclidean_distance = np.linalg.norm(room_vector - user_vector)
                similarity = 1 / (1 + euclidean_distance)
                new_array = [room.roomID, similarity]
                #방1개에 대한 최종 유사도는 배열로 저장해준다
                property_similarity.append(new_array)
                print(property_similarity)

        elif count==1: #null값이 하나만 있을 때
            if diff_null==1: #null값이 난이도일때
                for room in filtered_rooms:
                    room_horr = room.fear
                    room_acti = room.activity

                    #np용 배열로 저장
                    room_vector = np.array([room_horr, room_acti])
                    user_vector = np.array([user_option['fear'], user_option['activity']])

                    #유클리드 거리 계산
                    euclidean_distance = np.linalg.norm(room_vector - user_vector)
                    similarity = 1 / (1 + euclidean_distance)
                    new_array = [room.방ID, similarity]
                    #방1개에 대한 최종 유사도는 배열로 저장해준다
                    property_similarity.append(new_array)
                    print(property_similarity)
            elif horr_null==1: #null값이 공포도일때
                for room in filtered_rooms:
                    room_diff = room.difficulty
                    room_acti = room.activity

                    #np용 배열로 저장
                    room_vector = np.array([room_diff, room_acti])
                    user_vector = np.array([user_option['difficulty'], user_option['activity']])

                    #유클리드 거리 계산
                    euclidean_distance = np.linalg.norm(room_vector - user_vector)
                    similarity = 1 / (1 + euclidean_distance)
                    new_array = [room.방ID, similarity]
                    #방1개에 대한 최종 유사도는 배열로 저장해준다
                    property_similarity.append(new_array)
                    print(property_similarity)
            elif acti_null==1:
                for room in filtered_rooms:
                    room_diff = room.difficulty
                    room_horr = room.fear

                    #np용 배열로 저장
                    room_vector = np.array([room_diff, room_horr])
                    user_vector = np.array([user_option['difficulty'], user_option['fear']])

                    #유클리드 거리 계산
                    euclidean_distance = np.linalg.norm(room_vector - user_vector)
                    similarity = 1 / (1 + euclidean_distance)
                    new_array = [room.roomID, similarity]
                    #방1개마다 최종 유사도는 배열로 저장해준다
                    property_similarity.append(new_array)
                    print(property_similarity)

        elif count==2: #null값이 2개일때
            if diff_null==1 and horr_null==1: #활동성 값만 계산해주면 될때
                for room in filtered_rooms:
                    room_acti = room.activity

                    #np용 배열로 저장
                    room_vector = np.array([room_acti])
                    user_vector = np.array([user_option['activity']])

                    #유클리드 거리 계산
                    euclidean_distance = np.linalg.norm(room_vector - user_vector)
                    similarity = 1 / (1 + euclidean_distance)
                    new_array = [room.roomID, similarity]
                    #방1개마다 대한 최종 유사도는 배열로 저장해준다
                    property_similarity.append(new_array)
                    print(property_similarity)
            elif diff_null==1 and acti_null==1: #공포도 값만 계산해주면 될때
                for room in filtered_rooms:
                    room_horr = room.fear

                    #np용 배열로 저장
                    room_vector = np.array([room_horr])
                    user_vector = np.array([user_option.horror])

                    #유클리드 거리 계산
                    euclidean_distance = np.linalg.norm(room_vector - user_vector)
                    similarity = 1 / (1 + euclidean_distance)
                    new_array = [room[0], similarity]
                    #방1개마다 대한 최종 유사도는 배열로 저장해준다
                    property_similarity.append(new_array)
                    print(property_similarity)
            elif horr_null==1 and acti_null==1: #난이도 값만 계산해주면 될때
                for room in filtered_rooms:
                    room_diff = room.difficulty

                    #np용 배열로 저장
                    room_vector = np.array([room_diff])
                    user_vector = np.array([user_option['difficulty']])

                    #유클리드 거리 계산
                    euclidean_distance = np.linalg.norm(room_vector - user_vector)
                    similarity = 1 / (1 + euclidean_distance)
                    new_array = [room.roomID, similarity]
                    #방1개마다 대한 최종 유사도는 배열로 저장해준다
                    property_similarity.append(new_array)
                    print(property_similarity)

        elif count==3:
            for room in filtered_rooms:
                new_array = [room.방ID, 0]
                property_similarity.append(new_array)

        if all(value == 0 for _, value in filtered_genre_similarity) and count == 3:
            random_room = random.sample(rooms, 3)
            print("랜덤으로 추천드리는 방입니다: ")
            for room in random_room:
                print(room.roomID, room.region, room.date, room.genre, room.difficulty, room.fear, room.activity)
        else :
            # filtered_genre_similarity와 property_similarity에서 방 ID와 유사도 값을 가져옴
            room_IDs = [item[0] for item in filtered_genre_similarity]
            genre_values = np.array([item[1] for item in filtered_genre_similarity])
            property_values = np.array([item[1] for item in property_similarity])
            print(len(genre_values))
            print(len(property_values))

            # 방 ID별로 유사도 값들을 합산
            sum_similarity = genre_values + property_values

            # 유사도 값들의 표준 편차 계산
            total_similarity = np.concatenate((genre_values, property_values))
            total_similarity = np.reshape(total_similarity, (2, len(room_IDs)))
            total_similarity_std = total_similarity.std(axis=0)

            # 유사도 값들의 합에서 표준 편차를 뺀 결과를 2차원 배열로 저장
            modified_values = np.column_stack((room_IDs, sum_similarity - total_similarity_std))
            # modified_values를 크기순으로 정렬하여 상위 3개 방 추천
            recommended_rooms_id = modified_values[np.argsort(modified_values[:, 1])[::-1]][:3]
            recommended_rooms=[]

            for room in filtered_rooms:
                for item in recommended_rooms_id:
                    if room.roomID== item[0]:
                        recommended_rooms.append(room)
                        break


            for room in recommended_rooms:
                room_data = {
                    'roomID': room.roomID,
                    'title': room.title,
                    'roomIntro': room.roomIntro,
                    'date': room.date,
                    'region': room.region,
                    'genre': room.genre,
                    'difficulty': room.difficulty,
                    'fear': room.fear,
                    'activity': room.activity,
                }
                RoomViewSet.response_data.append(room_data)

            return Response({'success': True}, status=status.HTTP_200_OK)


    @api_view(['GET'])
    def getroomlist(request):
        final_response_data = RoomViewSet.response_data
        if len(final_response_data) > 0:
            print(len(final_response_data))
            response_data = {
                'success': True,
                'data': final_response_data,
                # 'roomId' : final_response_data.roomID
                'roomId' : final_response_data[0]['roomID']
                # 'roomId' : final_response_data
            }
            return Response(response_data, status=status.HTTP_200_OK)
        else:
            print(len(final_response_data))
            response_data = {
                'success': False,
                'data': []
            }
            return Response(response_data, status=status.HTTP_400_BAD_REQUEST)


    @api_view(['POST'])
    def enterroomlist(request):
        user_id = request.session.get('user_id')
        room_id = request.data.get('room_id')

        if user_id and room_id:
            try:
                user = AppUser.objects.get(id = user_id)
                room_index = int(room_id) - 1   # 방의 인덱스를 계산 (0부터 시작)
                if room_index >= 0 and room_index < len(user.roomID):
                    user.roomID = user.roomID[:room_index] + '1' + user.roomID[room_index+1:]  # 해당 위치에 1로 업데이트
                    user.save()
                    return Response({'success': True}, status=status.HTTP_200_OK)
                else:
                    return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)
            except AppUser.DoesNotExist:
                return Response({'success': False}, status=status.HTTP_404_NOT_FOUND)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)


class ChatViewSet(viewsets.ModelViewSet):
    queryset = Chat.objects.all()
    serializer_class = ChatSerializer



# KSH : ProfileViewSet 추가
class ProfileViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer

    @api_view(['POST'])
    def profilecreate(request):
        userId = request.data.get('userId')

        Embti = request.data.get('Embti')
        Smbti = request.data.get('Smbti')
        Tmbti = request.data.get('Tmbti')
        Jmbti = request.data.get('Jmbti')

        firstLesson = request.data.get('firstLesson')
        smoke = request.data.get('smoke')
        sleepHabit = request.data.get('sleepHabit')
        grade = request.data.get('grade')
        shareNeeds = request.data.get('shareNeeds')
        inComm = request.data.get('inComm')
        heatSens = request.data.get('heatSens')
        coldSens = request.data.get('coldSens')
        drinkFreq = request.data.get('drinkFreq')
        cleanliness = request.data.get('cleanliness')
        noiseSens = request.data.get('noiseSens')
        sleepSche = request.data.get('sleepSche')

        if userId and Embti and Smbti and Tmbti and Jmbti and firstLesson and smoke and sleepHabit and grade and shareNeeds and inComm and heatSens and coldSens and drinkFreq and cleanliness and noiseSens and sleepSche:

            user_count = Profile.objects.count() + 1
            user_profile = Profile.objects.create(profileId = user_count, userId = userId,
                                                  Embti = Embti,
                                                  Smbti = Smbti,
                                                  Tmbti = Tmbti,
                                                  Jmbti = Jmbti,
                                                  firstLesson = firstLesson,
                                                  smoke = smoke,
                                                  sleepHabit = sleepHabit,
                                                  grade = grade,
                                                  shareNeeds = shareNeeds,
                                                  inComm = inComm,
                                                  heatSens = heatSens,
                                                  coldSens = coldSens,
                                                  drinkFreq = drinkFreq,
                                                  cleanliness = cleanliness,
                                                  noiseSens = noiseSens,
                                                  sleepSche = sleepSche)
            user_profile.save()

            user = AppUser.objects.get(userID = userId)
            user.isProfile = True
            user.save()

            return Response({'success': True}, status=status.HTTP_201_CREATED)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)

    @api_view(['PATCH'])
    def profileupdate(request):
        userId = request.data.get('userId')

        Embti = request.data.get('Embti')
        Smbti = request.data.get('Smbti')
        Tmbti = request.data.get('Tmbti')
        Jmbti = request.data.get('Jmbti')

        firstLesson = request.data.get('firstLesson')
        smoke = request.data.get('smoke')
        sleepHabit = request.data.get('sleepHabit')
        grade = request.data.get('grade')
        shareNeeds = request.data.get('shareNeeds')
        inComm = request.data.get('inComm')
        heatSens = request.data.get('heatSens')
        coldSens = request.data.get('coldSens')
        drinkFreq = request.data.get('drinkFreq')
        cleanliness = request.data.get('cleanliness')
        noiseSens = request.data.get('noiseSens')
        sleepSche = request.data.get('sleepSche')

        if userId and Embti and Smbti and Tmbti and Jmbti and firstLesson and smoke and sleepHabit and grade and shareNeeds and inComm and heatSens and coldSens and drinkFreq and cleanliness and noiseSens and sleepSche:

            user_profile = Profile.objects.get(userId = userId)
            user_profile.Embti = Embti
            user_profile.Smbti = Smbti
            user_profile.Tmbti = Tmbti
            user_profile.Jmbti = Jmbti
            user_profile.firstLesson = firstLesson
            user_profile.smoke = smoke
            user_profile.sleepHabit = sleepHabit
            user_profile.grade = grade
            user_profile.shareNeeds = shareNeeds
            user_profile.inComm = inComm
            user_profile.heatSens = heatSens
            user_profile.coldSens = coldSens
            user_profile.drinkFreq = drinkFreq
            user_profile.cleanliness = cleanliness
            user_profile.noiseSens = noiseSens
            user_profile.sleepSche = sleepSche
            user_profile.save()

            return Response({'success': True}, status=status.HTTP_200_OK)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)

# KSH : UserPrefViewSet 추가
class UserPrefViewSet(viewsets.ModelViewSet):
    queryset = UserPref.objects.all()
    serializer_class = UPrefSerializer

    @api_view(['POST'])
    def userprefcreate(request):
        UuserId = request.data.get('UuserId')

        Umbti = request.data.get('Umbti')
        UfirstLesson = request.data.get('UfirstLesson')
        Usmoke = request.data.get('Usmoke')
        UsleepHabit = request.data.get('UsleepHabit')
        Ugrade = request.data.get('Ugrade')
        Ugrade = request.data.get('Ugrade')
        UshareNeeds = request.data.get('UshareNeeds')
        UinComm = request.data.get('UinComm')
        UheatSens = request.data.get('UheatSens')
        UcoldSens = request.data.get('UcoldSens')
        UdrinkFreq = request.data.get('UdrinkFreq')
        Ucleanliness = request.data.get('Ucleanliness')
        UnoiseSens = request.data.get('UnoiseSens')
        UsleepSche = request.data.get('UsleepSche')

        if UuserId and Umbti and UfirstLesson and Usmoke and UsleepHabit and Ugrade and UshareNeeds and UinComm and UheatSens and UcoldSens and UdrinkFreq and Ucleanliness and UnoiseSens and UsleepSche:
            user_count = UserPref.objects.count() + 1
            user_pref = UserPref.objects.create(prefId = user_count, UuserId = UuserId,
                                                Umbti = Umbti,
                                                UfirstLesson = UfirstLesson,
                                                Usmoke = Usmoke,
                                                UsleepHabit = UsleepHabit,
                                                Ugrade = Ugrade,
                                                UshareNeeds = UshareNeeds,
                                                UinComm = UinComm,
                                                UheatSens = UheatSens,
                                                UcoldSens = UcoldSens,
                                                UdrinkFreq = UdrinkFreq,
                                                Ucleanliness = Ucleanliness,
                                                UnoiseSens = UnoiseSens,
                                                UsleepSche = UsleepSche)
            user_pref.save()

            user = AppUser.objects.get(userID = UuserId)
            user.isUserPref = True
            user.save()

            return Response({'success': True}, status=status.HTTP_201_CREATED)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)

    @api_view(['PATCH'])
    def userprefupdate(request):
        UuserId = request.data.get('UuserId')

        Umbti = request.data.get('Umbti')
        UfirstLesson = request.data.get('UfirstLesson')
        Usmoke = request.data.get('Usmoke')
        UsleepHabit = request.data.get('UsleepHabit')
        Ugrade = request.data.get('Ugrade')
        UshareNeeds = request.data.get('UshareNeeds')
        UinComm = request.data.get('UinComm')
        UheatSens = request.data.get('UheatSens')
        UcoldSens = request.data.get('UcoldSens')
        UdrinkFreq = request.data.get('UdrinkFreq')
        Ucleanliness = request.data.get('Ucleanliness')
        UnoiseSens = request.data.get('UnoiseSens')
        UsleepSche = request.data.get('UsleepSche')

        if UuserId and Umbti and UfirstLesson and Usmoke and UsleepHabit and Ugrade and UshareNeeds and UinComm and UheatSens and UcoldSens and UdrinkFreq and Ucleanliness and UnoiseSens and UsleepSche:
            user_pref = UserPref.objects.get(UuserId = UuserId)
            user_pref.Umbti = Umbti
            user_pref.UfirstLesson = UfirstLesson
            user_pref.Usmoke = Usmoke
            user_pref.UsleepHabit = UsleepHabit
            user_pref.Ugrade = Ugrade
            user_pref.UshareNeeds = UshareNeeds
            user_pref.UinComm = UinComm
            user_pref.UheatSens = UheatSens
            user_pref.UcoldSens = UcoldSens
            user_pref.UdrinkFreq = UdrinkFreq
            user_pref.Ucleanliness = Ucleanliness
            user_pref.UnoiseSens = UnoiseSens
            user_pref.UsleepSche = UsleepSche
            user_pref.save()

            return Response({'success': True}, status=status.HTTP_200_OK)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)


# KSH :  MatchViewSet 추가
class MatchViewSet(viewsets.ModelViewSet):
    queryset = Match.objects.all()
    serializer_class = MatchSerializer




# KSH : ReportViewSet 추가
class ReportViewSet(viewsets.ModelViewSet):
    queryset = Report.objects.all()
    serializer_class = ReportSerializer

    @api_view(['POST'])
    def reportuser(request):
        reporterId = request.data.get('reporterId')
        reason = request.data.get('reason')
        timestamp = request.data.get('timestamp')
        reportedId = request.data.get('reportedId')

        if reporterId and reason and timestamp and reportedId:
            report_count = Report.objects.count() + 1
            report = Report.objects.create(reportId = report_count, reporterId = reporterId, reason = reason, timestamp = timestamp, reportedId = reportedId)
            report.save()
            return Response({'success': True}, status=status.HTTP_201_CREATED)
        else:
            return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)



# KSH : BlockViewSet 추가
class BlockViewSet(viewsets.ModelViewSet):
    queryset = Block.objects.all()
    serializer_class = BlockSerializer

    # KSH : blockuser 함수에 차단목록 추가/삭제 기능 한곳에 구현함
    @api_view(['POST', 'DELETE'])
    def blockuser(request):
        if request.method == 'POST':
            timestamp = request.data.get('timestamp')
            blockerId = request.data.get('blockerId')
            blockedId = request.data.get('blockedId')

            if timestamp and blockerId and blockedId:
                block_count = Block.objects.count() + 1
                block = Block.objects.create(blockId = block_count, timestamp = timestamp, blockerId = blockerId, blockedId = blockedId)
                block.save()
                return Response({'success': True}, status=status.HTTP_201_CREATED)
            else:
                return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)
        elif request.method == 'DELETE':
            blockerId = request.data.get('blockerId')
            blockedId = request.data.get('blockedId')

            if blockerId and blockedId:
                try:
                    block = Block.objects.get(blockerId = blockerId, blockedId = blockedId)
                    block.delete()
                    return Response({'success': True}, status=status.HTTP_200_OK)
                except Block.DoesNotExist:
                    return Response({'success': False}, status=status.HTTP_404_NOT_FOUND)
            else:
                return Response({'success': False}, status=status.HTTP_400_BAD_REQUEST)