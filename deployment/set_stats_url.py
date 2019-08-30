import os, sys
from tempfile import mkstemp 
from shutil import move 
from os import remove, close

# Update the project versionCode 
file_name = "/hipayfullservice-android/hipayfullservice/src/main/java/com/hipay/fullservice/core/monitoring/CheckoutDataNetwork.java" 
file_path =  "./" + file_name
 
#Create temp file 
fh, abs_path = mkstemp() 
with open(abs_path,'w') as new_file: 
    with open(file_path) as old_file: 
        for line in old_file: 
            if "private static final String statsURLStage" in line: 
      	        new_file.write(line.replace(line.strip(), 'private static final String statsURLStage = "' + os.environ.get('PI_CI_DATA_URL', '') + '";'))
            else: 
                new_file.write(line) 
 
close(fh) 
#Remove original file 
remove(file_path) 
#Move new file 
move(abs_path, file_path)
