import boto3
import keychain
import clipboard
import sys
import console
import webbrowser
import photos
import base64
import io
import shortcuts
import re
import json
from PIL import Image

def getClient(service):
  #accesskey = "PUT AWS ACCESS KEY HERE"
  secretkey = keychain.get_password("aws", accesskey)
  if secretkey == None:
    secretkey = console.password_alert("Enter secret key")
    keychain.set_password("aws",accesskey,secretkey)
  client = boto3.client(service,aws_access_key_id=accesskey,aws_secret_access_key=secretkey)
  return client

def getBuckets(client):
  #Retrieve the list of existing buckets
  response = client.list_buckets()
  buckets = []
  for bucket in response['Buckets']:
    buckets.append(bucket["Name"])
  return buckets

def getObjects(client,bucket,object='/'):
  objects = []
  #GET OBJECTS IN BUCKETS
  for bucket in getBuckets(client):
    objs = client.list_objects(Bucket=bucket)
    #print(objs)
    for obj in objs['Contents']:
      
      #if an actual file is chosen
      if object == obj['Key'] and object[-1] != "/":
        getUrl(bucket,obj['Key'])
      #if were at the top level of the bucket
      if object == '/':
        #if it isn't a nested object
        if not re.match('^.*/.*$',obj['Key']):
          objects.append(obj)
        #pull in all folders
        if obj['Key'][-1] == "/" and obj['Key'].count("/") == 1:
          objects.append(obj)
      else: #if in a Folder below top directory
        #If the object is in the folder specified
        if re.match('%s.*' % object,obj['Key']) and obj['Key'] != object:
          if obj['Key'].split(object)[1].count("/") < 1:
            objects.append(obj)
          elif obj['Key'].split(object)[1].count("/") == 1 and obj['Key'].split(object)[1][-1] == "/":
            objects.append(obj)
          
          #if not re.match('^%s/[^/]*$' % object,obj['Key']):
          #  objects.append(obj)
        
  return objects

def getUrl(bucket,object):
  #https://shellsharks-images.s3.amazonaws.com/Test/Test2/1236BF12-EB13-4965-9B7F-90C5F5608AD4.png
  url = "https://%s.s3.amazonaws.com/%s" % (bucket,object)
  shortcuts.open_shortcuts_app(name="S3", shortcut_input="data::"+url)
  sys.exit()

'''
#TRYING TO UPLOAD PHOTO
all_assets = photos.get_assets()
last_asset = all_assets[-1]
print(dir(last_asset))
img = last_asset.get_image()
img.show()

#image picker
img = photos.pick_asset(assets=photos.get_assets(media_type='photo')).get_image()

byteImgIO = io.BytesIO()
img.save(byteImgIO, "PNG")
byteImgIO.seek(0)
client.upload_fileobj(byteImgIO, buckets[0], "whatup3", ExtraArgs={ "ContentType": "image/jpeg"})
'''

#shortcuts.open_shortcuts_app(name="S3", shortcut_input=buckets)

if __name__ == "__main__":
  
  inputdata = sys.argv[1].split(",")
  action = inputdata[0]
  bucket = inputdata[1]
  input = inputdata[2]
  #print(action,bucket,input)
    
  s3client = getClient('s3')
  
  if bucket == "None":
    buckets = getBuckets(s3client)
    shortcuts.open_shortcuts_app(name="S3", shortcut_input="buckets::,"+",".join(buckets))
    sys.exit()
    
  if input == "root": input = "/"
  
  if action == "getobjects":
    print("Getting objects...",bucket,input)
    objects = getObjects(s3client,bucket,object=input)
    #print(objects)
    objkeys = []
    for obj in objects: objkeys.append(obj['Key'])
    shortcuts.open_shortcuts_app(name="S3", shortcut_input=bucket+"-objects::,"+",".join(objkeys))
    sys.exit()
  
  if action == "upload":
    objects = getObjects(s3client,bucket,object=input)
    objkeys = []
    for obj in objects: objkeys.append(obj['Key'])
    shortcuts.open_shortcuts_app(name="S3", shortcut_input=bucket+"-objects::,"+",".join(objkeys))
    sys.exit()

  