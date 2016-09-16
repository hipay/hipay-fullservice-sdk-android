curl \
-F "ipa=@../hipayfullservice-android/example/build/outputs/apk/example-release.apk" \
-F "notes=CircleCI build $CIRCLE_BUILD_NUM" \
-F "notes_type=1" \
-F "notify=0" \
-F "status=2" \
-F "strategy=add" \
-F "commit_sha=$CIRCLE_SHA1" \
-F "build_server_url=$CIRCLE_BUILD_URL" \
-F "repository_url=$CIRCLE_REPOSITORY_URL" \
-H "X-HockeyAppToken: $HOCKEY_APP_TOKEN" \
https://rink.hockeyapp.net/api/2/apps/$(cat $CIRCLE_ARTIFACTS/app_identifier)/app_versions/upload
