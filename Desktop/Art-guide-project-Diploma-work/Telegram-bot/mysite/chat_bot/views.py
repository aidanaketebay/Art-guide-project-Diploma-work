from django.shortcuts import render, redirect
from django.views.generic import View
from django.http import JsonResponse
from django.utils.decorators import method_decorator
from django.views.decorators.csrf import csrf_exempt
from . import config
from django.db.models import Q
from django.core.exceptions import ObjectDoesNotExist
from textblob import TextBlob


import os

import sys
sys.path.insert(1, "/home/Aiasem/mysite/chat_bot/")
import find_sim

from .models import ArtGallery, InfoGuide

import telebot
from telebot import types

image_names_kz = []
image_names_ru = []
image_names_eng = []
image_codes = []

def index(request):
    pass
    global image_names_kz, image_names_ru, image_names_eng, image_codes

    # find_sim.dataset_process()
    return render(request, "chat_bot/index.html", {'test': image_names_eng})

language_used = 'en'
result = ""
TOKEN = config.token
bot = telebot.TeleBot(TOKEN, threaded=False)



for q in ArtGallery.objects.all():
    image_names_kz.append(q.image_title_kz)
    image_names_ru.append(q.image_title_ru)
    image_names_eng.append(q.image_title_eng)
    image_codes.append(str(q.unique_code))
# так как вебхук уже подключен будем ждать новую информацию в TelegramBotView

class TelegramBotView(View):
    @method_decorator(csrf_exempt)
    def dispatch(self, request, *args, **kwargs):
        return super(TelegramBotView, self).dispatch(request, *args, **kwargs)

    def post(self, request):
        bot.process_new_updates([telebot.types.Update.de_json(request.body.decode('utf-8'))]) # тут мы уже получили новую инфу, эти изменения обновляем в боте
        return JsonResponse({'code': 'accepted'})

# КОД БОТА

@bot.message_handler(commands=['start'])
def handle_start(message):
    user_id = message.from_user.id
    startObj = InfoGuide.objects.get(command_title = message.text.replace('/',""))
    text_eng = startObj.eng
    probel = '_'* 45
    text_ru = startObj.ru
    text_kz = startObj.kz
    text_fin = text_eng + "\n" + probel + "\n" + text_ru + "\n" + probel + "\n" +text_kz

    bot.send_message(user_id, text_fin )

    # bot.send_message(user_id, "HIIIII" )


@bot.message_handler(commands=['help','imagename', 'camera', 'uniquecode'])
def get_help_info(message):
    user_id = message.from_user.id
    text = message.text.replace('/',"")
    bot.send_message(user_id, command_text(text))

@bot.message_handler(commands=['language'])
def handle_language(message):

    user_id = message.from_user.id


    eng_button = types.InlineKeyboardButton(text="English", callback_data="en")
    ru_button = types.InlineKeyboardButton(text="Русский", callback_data="ru")
    kz_button = types.InlineKeyboardButton(text="Қазақша", callback_data="kk")
    keyboard = types.InlineKeyboardMarkup()
    keyboard.add(eng_button)
    keyboard.add(ru_button)
    keyboard.add(kz_button)

    bot.send_message(user_id, "Please choose the language:", reply_markup=keyboard)

@bot.message_handler(content_types=['photo']) # если отправили фото
def handle_image(message):

    global result
    user_id = message.from_user.id

    try:
        file_info = bot.get_file(message.photo[len(message.photo)-1].file_id)
        downloaded_file = bot.download_file(file_info.file_path)
        src = find_sim.query_image
        bot.send_message(user_id, command_text("wait"))
        with open(src, 'wb') as new_file:
            new_file.write(downloaded_file)

        base = os.path.basename(find_sim.calculation())

        # bot.send_message(user_id, base)
        if(base == ""):
            text = command_text("error")
            bot.send_message(user_id, text)
        else:
            result = os.path.splitext(base)[0]
            result = result.replace('_'," ")
            result = ArtGallery.objects.get(image_title_eng = result)

            text = command_text("info")
            bot.send_message(user_id, text , reply_markup = gen_markup())

    except Exception:
        bot.send_message(user_id, command_text("error"))



@bot.message_handler(content_types=['voice'])
def handle_voice(message):
    global language_used, result
    global image_names_kz, image_names_ru, image_names_eng, image_codes

    error = False
    user_id = message.from_user.id

    try:
        file_info = bot.get_file(message.voice.file_id)
        downloaded_file = bot.download_file(file_info.file_path)
        src = find_sim.query_voice
        with open(src, 'wb') as new_file:
            new_file.write(downloaded_file)
        res = find_sim.voice_proccess(language_used)
    except Exception:
        bot.send_message(user_id, command_text("error"))
    try:
        res = res.replace(' ',"").lower()

        if res.isdigit():
            result = ArtGallery.objects.get(unique_code = res)
            text = command_text("info")
            bot.send_message(user_id, text , reply_markup = gen_markup())
            error = False
        else:
            for (b, c, d) in zip(image_names_eng, image_names_kz, image_names_ru):
                x = b.replace(" ", "").lower()
                y = c.replace(" ", "").lower()
                z = d.replace(" ", "").lower()

                if( (x in res) or (y in res) or (z in res) ):
                    result = ArtGallery.objects.get(
                        Q(image_title_eng__contains = b)  |
                        Q(image_title_kz__contains = c) |
                        Q(image_title_ru__contains = d)
                    )
                    text = command_text("info")
                    bot.send_message(user_id, text , reply_markup = gen_markup())
    except Exception:
        bot.send_message(user_id, command_text("error"))


@bot.message_handler(content_types=['text']) # обычные сообщения
def handle_text(message):
    global language_used, result
    global image_names_kz, image_names_ru, image_names_eng, image_codes
    user_id = message.from_user.id
    #lang = TextBlob(text)
    try:
        res = message.text.replace(' ',"").lower()
        if res.isdigit():
            result = ArtGallery.objects.get(unique_code = res)
            text = command_text("info")
            bot.send_message(user_id, text , reply_markup = gen_markup())
            error = False
        else:
            for (b, c, d) in zip(image_names_eng, image_names_kz, image_names_ru):
                x = b.replace(" ", "").lower()
                y = c.replace(" ", "").lower()
                z = d.replace(" ", "").lower()

                if( (x in res) or (y in res) or (z in res) ):
                    result = ArtGallery.objects.get(
                        Q(image_title_eng__contains = b)  |
                        Q(image_title_kz__contains = c) |
                        Q(image_title_ru__contains = d)
                    )
                    text = command_text("info")
                    bot.send_message(user_id, text , reply_markup = gen_markup())
        #             error = False
        #         else:
        #             error = True
        # if error:
        #     bot.send_message(user_id, command_text("error"))

    except Exception:
        bot.send_message(user_id, command_text("error"))

def gen_markup():

    keyboard = types.InlineKeyboardMarkup()

    audio_button = types.InlineKeyboardButton(text="Audio", callback_data="audio")
    text_button = types.InlineKeyboardButton(text="Text", callback_data="text")

    keyboard.row(audio_button, text_button)

    return keyboard



def command_text(text):
    global language_used
    text = InfoGuide.objects.get(command_title = text)
    if(language_used == "en"):
        text = text.eng
    elif(language_used == "kk"):
        text = text.kz
    elif(language_used == "ru"):
        text = text.ru
    return text


def audio_lang():
    global result,language_used
    if(language_used == "kk"):
        data = result.audio_kz
    elif(language_used == "ru"):
        data = result.audio_ru
    elif(language_used == "en"):
        data = result.audio_eng
    return data


def text_lang():
    global result, language_used
    if(language_used == "kk"):
        data = result.text_kz
    elif(language_used == "ru"):
        data = result.text_ru
    elif(language_used == "en"):
        data = result.text_eng
    return data


@bot.callback_query_handler(func=lambda call: True)
def query_handler(call):
    global result
    global language_used

    user_id = call.from_user.id

    if call.data == 'en':
        language_used = "en"
        # command_text("language")
        bot.answer_callback_query(call.id,"OK")


    if call.data == "ru":
        language_used = "ru"
        bot.answer_callback_query(call.id,"OK")


    if call.data == "kk":
        language_used = "kk"
        bot.answer_callback_query(call.id,"OK")


    if call.data == "audio":
        bot.send_voice(user_id, audio_lang())
        result = ""

    if call.data == "text":
        bot.send_message(user_id, text_lang())
        result = ""
