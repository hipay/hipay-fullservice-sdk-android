import sys, os, xml.etree.ElementTree as ET

filename = "hipayfullservice-android/example/src/main/res/values/credentials.xml"

path =  "./" + filename

tree = ET.parse(path)
root = tree.getroot()

for string in root.iter('string'):
	if string.get('name') == 'username':
		string.text = os.environ.get('HIPAY_FULLSERVICE_STAGE_API_USERNAME', '')

	if string.get('name') == 'password':
		string.text = os.environ.get('HIPAY_FULLSERVICE_STAGE_API_PASSWORD', '')

tree.write(path)


