import os, sys
from tempfile import mkstemp 
from shutil import move 
from os import remove, close

# Update the project versionCode 
file_name = "hipayfullservice-android/example/build.gradle" 
file_path =  "../" + file_name 
 
#Create temp file 
fh, abs_path = mkstemp() 
with open(abs_path,'w') as new_file: 
    with open(file_path) as old_file: 
        for line in old_file: 
            if "versionCode" in line: 
      	        new_file.write(line.replace(line.strip(), 'versionCode ' + os.environ.get('CIRCLE_BUILD_NUM', '1'))) 
            else: 
                new_file.write(line) 
 
close(fh) 
#Remove original file 
remove(file_path) 
#Move new file 
move(abs_path, file_path)
