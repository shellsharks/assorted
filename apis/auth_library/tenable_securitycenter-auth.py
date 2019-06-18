#Basic authentication steps for Tenable Security center...

import requests

domain = '' # The domain of your Security Center Installation
username = '' # The username for the API account
password = '' # The password for the API account

url = 'https://'+domain+'/rest/'
body_parameters = {'username':username,'password':password}
r = requests.post(url + 'token', params=body_parameters)
session = r.cookies
token = str(r.json()['response']['token'])
header = {"X-SecurityCenter":token,"Content-Type":"application/json"}

api = '' # The API endpoint (example: 'status')
data = {} # A list of any data key/value parameters

# replace "get" below with the verb necssary for the api endpoint
r = requests.get(url + api, headers=header, cookies=session)

json_response = r.json()['response']
