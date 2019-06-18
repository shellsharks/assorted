import json, requests

# Create a Feedly account
# Go to https://developer.feedly.com/v3/developer/ to get developer tokens
# Input tokens below
user_id, access_token, expiry_date, refresh_token = '','','',''

url = "https://cloud.feedly.com/"
ver = 'v3/'

#api to target
api = ''

# Input any necessary params here.
params = {'key1':'value1','key2':'value2',...}

request_headers = {'Authorization':'Bearer '+ access_token}

# replace "get" below with the verb necssary for the api endpoint
# Use body=params for POST and params=params for GET
r = requests.get(url + ver + api, headers=request_headers)

print(r.json())
