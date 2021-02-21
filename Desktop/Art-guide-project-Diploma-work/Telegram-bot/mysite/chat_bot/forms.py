from django.forms import ModelForm
from apps.models import ArtGallery

class ArtGaleryForm(forms.ModelForm):
    class Meta:
        model= ArtGallery
        fields= ["name", "imagefile", "filepath"]