import requests, json

# Generate keys and input into variables below
# https://docs.tenable.com/cloud/Content/Settings/GenerateAPIKey.htm
access_key = ""
secret_key = ""

url = "https://cloud.tenable.com/"

# API endpoint to target
api = 'scanners'

#body parameters if necessary (for POST requests)
params = {}

headers = {'accept':'application/json', 'content-type':'application/json','X-ApiKeys':"accessKey="+access_key+";secretKey="+secret_key}

# Change 'get' to 'post' or other VERB if necessary
response = requests.get(url+api, headers=headers)

# From here do stuff with response
