#Basic authentication steps for IBM AppScan Console...

import json, requests

fqdn = '' # The Fully Qualified Domain Name of your AppScan Installation
domain = '' # The domain of the username you are authenticating with
port = '' # The port of the AppScan installation (likely 9443)
username = '' # The username for the API account
password = '' # The password for the API account

url = 'https://'+fqdn+':'+port+'/ase/api/'
request_headers = {"Content-Type":"application/json"}
body_parameters = {"userId":domain+'\\'+username, "password":password, "featureKey":"AppScanEnterpriseUser"}

r = requests.post(url+'login', headers=request_headers, json=body)

# Subsequent api calls must be made with a custom header 'asc_xsrf_token' being set to the sessionId response returned during the initial login. These calls must also include the 'asc_session_id' cookie.

session_id = r.json()['sessionId']
request_headers.update({'asc_xsrf_token':session_id})
session = r.cookies

api = '' # The API endpoint (example: 'status')
data = {} # A list of any data key/value parameters

# replace "get" below with the verb necssary for the api endpoint
r = requests.get(url + api, headers=request_headers, cookies=session)

json_response = r.json()['response']
