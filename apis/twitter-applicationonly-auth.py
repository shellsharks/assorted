import json, requests, urllib, base64

url = 'https://api.twitter.com/'
api = 'oauth2/token'

# Input the keys which were generated when you created your Twitter application via the developer portal.
api_key,api_secret_key,access_token,access_token_secret = '','','',''

# ---STEP 1---
# URL encode the consumer key and the consumer secret according to RFC 1738. Note that at the time of writing,
# this will not actually change the consumer key and secret,
# but this step should still be performed in case the format of those values changes in the future.
consumer_key = urllib.quote(api_key)
consumer_secret_key = urllib.quote(api_secret_key)

# Concatenate the encoded consumer key, a colon character ":", and the encoded consumer secret into a single string.
bearer_token_creds = consumer_key + ":" + consumer_secret_key

# Base64 encode the string from the previous step.
encoded_token = base64.b64encode(bearer_token_creds)

# ---STEP 2---
# The request must include an Authorization header with the value of Basic <base64 encoded value from step 1>.
# The request must include a Content-Type header with the value of application/x-www-form-urlencoded;charset=UTF-8.
request_headers = {'Authorization':"Basic "+encoded_token, 'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}

# The body of the request must be grant_type=client_credentials.
body = {'grant_type':'client_credentials'}

# The request must be a HTTP POST request.
r = requests.post(url+api, headers=request_headers, data=body)

# ---STEP 3---
# The bearer token may be used to issue requests to API endpoints which support application-only auth.
# To use the bearer token, construct a normal HTTPS request and include an Authorization header with the value of
# Bearer <base64 bearer token value from step 2>. Signing is not required.
bearer_token = r.json()['access_token']
request_headers = {'Authorization':'Bearer '+bearer_token}

# ---FINALLY--- Target the api endpoint of your choice
api = ""
ver = '1.1/'

# input any parameters here
params = {'key1':'value1','key2':'value2',...}

# send request (you must http VERB as needed)
# Use body=params for POST and params=params for GET
r = requests.get(url+ver+api, headers=request_headers, params=params)
