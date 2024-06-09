# 실시간 채팅 추가
# Kiri_app/routing.py

from django.urls import re_path
from . import consumers

websocket_urlpatterns = [
    re_path(r'ws/chat/(?P<room_id>\w+)/$', consumers.ChatConsumer.as_asgi()),
    # re_path('ws/chat/<str:room_id>/', consumers.ChatConsumer.as_asgi()),
]


