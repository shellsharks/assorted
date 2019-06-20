import requests, json, configparser

access_key, secret_key = "",""

with open('secret_tenable-config.json') as json_data_file:

    config = json.load(json_data_file)
    access_key = config['access_key'] # (API key)
    secret_key = config['secret_key'] # (API secret key)

url = "https://cloud.tenable.com/"

api = 'scanners'

params = {}

headers = {'accept':'application/json', 'content-type':'application/json','X-ApiKeys':"accessKey="+access_key+";secretKey="+secret_key}

response = requests.get(url+api, headers=headers)

#print(response.json()['scanners'][1])

for scanner in response.json()['scanners']:
    print(scanner['name'])
