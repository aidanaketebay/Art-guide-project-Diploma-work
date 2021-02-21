import cv2
from PIL import Image
import glob
import os
import numpy as np
import speech_recognition as sr
from pydub import AudioSegment


raw_directoryPath = '/home/Aiasem/mysite/media/media/'
proccessed_directoryPath = '/home/Aiasem/mysite/media/media/'
query_image = '/home/Aiasem/mysite/media/query/new.jpg'
query_voice = '/home/Aiasem/mysite/media/voice/new.ogg'
new_voice = '/home/Aiasem/mysite/media/voice/new.wav'

def glob_filetypes(root_dir, *patterns):
    return [path
            for pattern in patterns
            for path in glob.glob(os.path.join(root_dir, pattern))]

raw_directoryPath_Files = sorted(glob_filetypes(raw_directoryPath, "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif"))
proccessed_directoryPath_Files = sorted(glob_filetypes(proccessed_directoryPath, "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif"))

def voice_proccess(language_used):
    sound = AudioSegment.from_file(query_voice)
    sound.export(new_voice, format="wav")
    r = sr.Recognizer()
    with sr.AudioFile(new_voice) as source:
        audio = r.record(source)
    try:
        s = r.recognize_google(audio, language = language_used)
        print(s)
    except Exception as e:
    	s = e
    return s

def check_for_duplicates():

  raw_files = []
  proccessed_files = []

  for filename in raw_directoryPath_Files:
    file = os.path.basename(filename)
    print(file)
    raw_files.append(file)


  if not proccessed_directoryPath_Files:
      res = raw_files
  else:
    for filename in proccessed_directoryPath_Files:
        if(file not in raw_files):
            os.remove(filename)
        else:
            file = os.path.basename(filename)
            proccessed_files.append(file)

  res = list(set(raw_files) - set(proccessed_files))


  return res


def dataset_process():

  non_proccessed_files = check_for_duplicates()

  image_list = []
  resized_images = []

  for filename in non_proccessed_files:
    img = Image.open(raw_directoryPath + filename)
    image_list.append(img)


  for image in image_list:
    image = image.resize((500,500))
    image = image.convert("RGB")
    resized_images.append(image)

  i = 0
  for(i,new) in enumerate(resized_images):
    new.save('{}{}'.format(proccessed_directoryPath, non_proccessed_files[i]))
  return resized_images


def query_process(imagepath):
    # y=300
    # x=300
    # h=500
    # w=500
    # img_a = cv2.imread(imagepath)
    # img_a = img_a[y:y+h, x:x+w]
    # return img_a
    img = Image.open(imagepath)
    im = cv2.imread(imagepath)

    h = im.shape[0]
    w = im.shape[1]


    cropped_img = img.crop(((w-500)//2, (h-500)//2, (w+500)//2, (h+500)//2))
    image = np.asarray(cropped_img)
    # cv2.imshow('Image', image)
    # cv2.waitKey()
    return image


def surf_sim(img_a, path_b):
  orb = cv2.xfeatures2d.SURF_create()


  img_b = cv2.imread(path_b) #read dataset image
  img_b = cv2.cvtColor(img_b, cv2.COLOR_BGR2RGB)

  # find the keypoints and descriptors with SURF
  kp_a, desc_a = orb.detectAndCompute(img_a, None)
  kp_b, desc_b = orb.detectAndCompute(img_b, None)

  # initialize the bruteforce matcher
  bf = cv2.BFMatcher()

  # match.distance is a float between {0:100} - lower means more similar
  matches = bf.knnMatch(desc_a,desc_b, k=2)


  good = []
  for m,n in matches:
      if m.distance < 0.7*n.distance:
          good.append([m])
  return len(good)


def calculation():
    result = ""
    img_a = query_process(query_image)

    percentages_surf = []

    files = []


    for filename in proccessed_directoryPath_Files:
        result_surf = surf_sim(img_a, filename)
        # print(filename, "    ",result_surf)

        percentages_surf.append(result_surf)
        files.append(filename)



    if len(percentages_surf) != 0:
        indeex = percentages_surf.index(max(percentages_surf))
        result = files[indeex]
    else:
        result = ""

    return result

def main_func():
    #dataset_process()
    print(calculation())
main_func()