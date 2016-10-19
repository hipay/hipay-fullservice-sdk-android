import json
import httplib, urllib
import os, sys

public_identifier = None;
bundle_identifier = "com.hipay.fullservice.example." + os.environ.get('CIRCLE_BRANCH')
bundle_identifier = bundle_identifier.replace('/', '.')
bundle_identifier = bundle_identifier.replace('-', '')

log_file = open(os.environ.get('CIRCLE_ARTIFACTS') + '/get_app_identifer.log', 'w');

hockeyConnection = httplib.HTTPSConnection("rink.hockeyapp.net")
hockeyConnection.request("GET", "/api/2/apps", None, {"X-HockeyAppToken": os.environ.get('HOCKEY_APP_TOKEN')})
response = hockeyConnection.getresponse()

if response.status != 200:
    raise Exception('Failed to fetch apps')

appsResponse = json.loads(response.read().decode('utf-8'))

# Check if app exists
for app in appsResponse["apps"]:
    if app["bundle_identifier"] == bundle_identifier:
    	public_identifier = app["public_identifier"]
        log_file.write("Found app public identifier:\n" + str(public_identifier) + "\n\n\n")

if public_identifier is None:
    raise Exception('No app found')

# Return identifier
log_file.write("Public identifier to be used:\n" + str(public_identifier) + "\n\n\n")

sys.stdout.write(public_identifier)

