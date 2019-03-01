curl \
-F "ipa=@./hipayfullservice-android/example/build/outputs/apk/release/example-release.apk" \
-F "notes=GitlabCI build $CI_JOB_ID" \
-F "notes_type=1" \
-F "notify=0" \
-F "status=2" \
-F "strategy=add" \
-F "commit_sha=$CI_COMMIT_SHA1" \
-F "build_server_url=$CIJOB_URL" \
-F "repository_url=$CI_REPOSITORY_URL" \
-H "X-HockeyAppToken: $HOCKEY_APP_TOKEN" \
https://rink.hockeyapp.net/api/2/apps/$(cat $CI_ARTIFACTS/app_identifier)/app_versions/upload
