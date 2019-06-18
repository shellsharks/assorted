import oauth2 as oauth

# Input the keys which were generated when you created your Twitter application via the developer portal.
api_key,api_secret_key,access_token,access_token_secret = '','','',''

consumer = oauth.Consumer(key=api_key, secret=api_secret_key)
access = oauth.Token(key=access_token, secret=access_token_secret)
client = oauth.Client(consumer, access)

url = 'https://api.twitter.com/'
ver = "1.1/"
api = "" # target the api of your choice
params = "key="+"value" #then comma separate additional values

# Send the formatted request (change the VERB or params as needed)
# Use body=params for POST and params=params for GET
response, data = client.request(url+ver+api, method="POST", body=params)
