# Generated by Django 4.2.2 on 2024-06-01 10:00

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Kiri_app', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='appuser',
            old_name='gender',
            new_name='Gender',
        ),
        migrations.RenameField(
            model_name='appuser',
            old_name='id',
            new_name='ID',
        ),
        migrations.RenameField(
            model_name='appuser',
            old_name='name',
            new_name='Name',
        ),
        migrations.RenameField(
            model_name='appuser',
            old_name='password',
            new_name='Password',
        ),
        migrations.RenameField(
            model_name='appuser',
            old_name='studentId',
            new_name='StudentID',
        ),
    ]
