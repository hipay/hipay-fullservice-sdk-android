import json
import httplib, urllib
import os, sys
import xml.etree.ElementTree as ET

from tempfile import mkstemp
from shutil import move
from os import remove, close

public_identifier = None;
bundle_identifier = "com.hipay.fullservice.example." + os.environ.get('CI_COMMIT_REF_SLUG')
bundle_identifier = bundle_identifier.replace('/', '.')
bundle_identifier = bundle_identifier.replace('-', '')

log_file = open(os.environ.get('CI_ARTIFACTS') + '/get_app_identifer.log', 'w');

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

# Create app if it doesn't exist
if public_identifier is None:

    params = urllib.urlencode({
			"title": "HiPay Demo (" + os.environ.get('CI_COMMIT_REF_SLUG') + ")",
			"bundle_identifier": bundle_identifier,
			"platform": "Android",
			"release_type": "2"
		})

    hockeyConnection.request("POST", "/api/2/apps/new", params, {
			"X-HockeyAppToken": os.environ.get('HOCKEY_APP_TOKEN')
			})

    response = hockeyConnection.getresponse()
    app = json.loads(response.read().decode('utf-8'))

    if response.status != 200 and response.status != 201:
    	raise Exception('Failed to create app')
    
    public_identifier = app["public_identifier"]

# Add team to app
hockeyConnection.request("PUT", "/api/2/apps/" + public_identifier + "/app_teams/54248", None, {
        "X-HockeyAppToken": os.environ.get('HOCKEY_APP_TOKEN')
        })

# Return identifier
log_file.write("Public identifier to be used:\n" + str(public_identifier) + "\n\n\n")

# Insert identifier in Android Manifest
filename = "hipayfullservice-android/example/src/main/AndroidManifest.xml"
path =  "./" + filename

ET.register_namespace('android', "http://schemas.android.com/apk/res/android")

tree = ET.parse(path)
root = tree.getroot()

for app in root.iter('application'):
	metadata = ET.SubElement(app, 'meta-data')
	metadata.set('android:name','net.hockeyapp.android.appIdentifier')
	metadata.set('android:value', str(public_identifier))
	
	ET.dump(app)

tree.write(path)

# Update the project applicationId 
file_name = "hipayfullservice-android/example/build.gradle" 
file_path =  "./" + file_name
 
#Create temp file 
fh, abs_path = mkstemp() 
with open(abs_path,'w') as new_file: 
    with open(file_path) as old_file: 
        for line in old_file: 
            if "applicationId" in line: 
      	        new_file.write(line.replace(line.strip(), 'applicationId "' + bundle_identifier + '"' )) 
            else: 
                new_file.write(line) 
 
close(fh) 
#Remove original file 
remove(file_path) 
#Move new file 
move(abs_path, file_path)

log_file.write("Bundle identifier to be used:\n" + bundle_identifier + "\n\n\n")


