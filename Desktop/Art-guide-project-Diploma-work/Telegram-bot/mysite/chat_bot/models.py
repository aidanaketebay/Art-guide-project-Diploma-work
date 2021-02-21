from django.db import models
import random
from django.db.models.signals import post_delete
from django.dispatch.dispatcher import receiver

a = 0
# Create your models here.
class ArtGallery(models.Model):
    global a
    a = str(random.sample(range(10000,99999),1)[0])
    unique_code = models.CharField(max_length = 200 ,default=a, editable=True)
    image_title_kz = models.CharField(max_length = 300)
    image_title_eng = models.CharField(max_length = 300)
    image_title_ru = models.CharField(max_length = 300)
    text_kz = models.TextField("Text in kazakh")
    text_eng = models.TextField("Text in eng")
    text_ru = models.TextField("Text in russian")

    gallery_image = models.FileField(upload_to = 'media/',null = True, verbose_name = "Gallery photo")
    audio_kz = models.FileField(upload_to = 'files/',null = True, verbose_name = "Audio in kazakh")
    audio_eng = models.FileField(upload_to = 'files/',null = True, verbose_name = "Audio in english")
    audio_ru = models.FileField(upload_to = 'files/',null = True, verbose_name = "Audio in russian")


    def __str__(self):
        global a
        a = str(random.sample(range(10000,99999),1)[0])
        return self.image_title_eng + ": " + str(self.gallery_image)

    class Meta:
        verbose_name = 'image'
        verbose_name_plural = 'images'

@receiver(post_delete, sender=ArtGallery)
def mymodel_delete(sender, instance, **kwargs):
    # Pass false so FileField doesn't save the model.
    instance.gallery_image.delete(False)
    instance.audio_kz.delete(False)
    instance.audio_eng.delete(False)
    instance.audio_ru.delete(False)

class InfoGuide(models.Model):
    command_title = models.CharField(max_length = 200)
    kz = models.TextField("Information in kazakh")
    eng = models.TextField("Information in eng")
    ru = models.TextField("Information in russian")

    def __str__(self):
        return self.command_title + ": " + str(self.eng)

    class Meta:
        verbose_name = 'command'
        verbose_name_plural = 'commands'

