from . import views
from django.urls import path
from .views import TelegramBotView
from . import config

TOKEN = "bot" + config.token
urlpatterns = [
    path('', views.index, name='index'),
    path(TOKEN, TelegramBotView.as_view()),
] # роут на новые изменения с нашего бота, получаем и отправляем в view.py -> TelegramBotView

