# create twitter developer account: https://developer.twitter.com/en/apply-for-access
# create twitter "app": https://developer.twitter.com/en/apps

import json, requests, urllib, base64, argparse, sys, configparser
import oauth2 as oauth

''' Config file needed, use the format below and save as 'secret_twitter-config.json'
{
  "api_key":"[api key]",
  "api_secret_key":"[api secret key]",
  "access_token":"[access token]",
  "access_token_secret":"[access token secret]"
}
'''

api_key,api_secret_key,access_token,access_token_secret = '','','',''

with open('secret_twitter-config.json') as json_data_file:

    config = json.load(json_data_file)

    #Consumer API keys
    api_key = config['api_key'] # (API key)
    api_secret_key = config['api_secret_key'] # (API secret key)

    #Access token & access token secret
    access_token = config['access_token'] # (Access token)
    access_token_secret = config['access_token_secret'] # (Access token secret)

parser = argparse.ArgumentParser()
parser.add_argument("-u", "--user", help="Use your Twitter username")
parser.add_argument("-l", "--list", nargs='?', const=1, default='none', help="No argument will display followed users on the command line, an argument will save list to the provided file name")
parser.add_argument("-c", "--create", help="Follow users from a provided file")
parser.add_argument("-d", "--destroy", help="Unfollow users from a provided file")
args = parser.parse_args()

#screen name
twitter_user_name = args.user
url = 'https://api.twitter.com/'

if args.create and args.destroy:
    print("Sorry you can only create or destroy by iteslf")
    sys.exit(1)

def listUsers():
    # Application-Only Authentication

    api = 'oauth2/token'

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

    # ---FINALLY--- Target friends/list.json
    api = 'friends/list.json'
    ver = '1.1/'
    count = 200
    cursor = -1
    following_count = 1
    users = []

    while cursor != 0:
        params = {'screen_name':twitter_user_name,'count':count, 'cursor':cursor}
        r = requests.get(url+ver+api, headers=request_headers, params=params)

        for user in r.json()['users']:
            #print(user['screen_name'])
            users.append(user['screen_name'])
            following_count = following_count + 1

        cursor = r.json()['next_cursor']

    print("\nExported all " + str(following_count) + " of " + twitter_user_name + " followed accounts.")
    return users

if args.create or args.destroy:

    ######## OAuth Authentication
    consumer = oauth.Consumer(key=api_key, secret=api_secret_key)
    access = oauth.Token(key=access_token, secret=access_token_secret)
    client = oauth.Client(consumer, access)

    ver = "1.1/"

    if args.create:
        api = "friendships/create.json"
        fileName = args.create
        users = [line.rstrip('\n') for line in open(fileName)]

    if args.destroy == "all":
        api = "friendships/destroy.json"
        users = listUsers()
    elif args.destroy:
        api = "friendships/destroy.json"
        fileName = args.destroy
        users = [line.rstrip('\n') for line in open(fileName)]

    print(users)

    for user in users:
        params = "screen_name="+user
        response, data = client.request(url+ver+api, method="POST", body=params)

if args.list!='none' and args.user:
    userlist = listUsers()

    if args.list == 1:
        for user in userlist:
            print(user),
    else:
        with open(args.list, 'w') as filehandle:
            filehandle.writelines("%s\n" % user for user in userlist)
