# gets a list of the Security Feeds

import json, requests

# https://developer.feedly.com/v3/developer/
user_id, access_token, expiry_date, refresh_token = '','','',''

with open('secret_feedly-config.json') as json_data_file:

    config = json.load(json_data_file)
    user_id = config['user_id'] # (User ID)
    access_token = config['access_token'] # (Access Token)
    expiry_date = config['expiry_date'] # (Access Token Expiry Date)
    refresh_token = config['refresh_token'] # (Refresh Token)

url = "https://cloud.feedly.com/"
ver = 'v3/'
api = 'collections'

request_headers = {'Authorization':'Bearer '+ access_token}

r = requests.get(url + ver + api, headers=request_headers)

for category in r.json():
    if category['label']=="Security":
        for blog in category['feeds']:
            print("* " + blog['website'])
