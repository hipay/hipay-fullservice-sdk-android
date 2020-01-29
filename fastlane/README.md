fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android SetEnv
```
fastlane android SetEnv
```
Set environment variables
### android Test
```
fastlane android Test
```
Runs all the tests
### android BuildDemo
```
fastlane android BuildDemo
```
Build demo
### android DeployDemo
```
fastlane android DeployDemo
```
Deploy a new version to AppCenter
### android BuildFramework
```
fastlane android BuildFramework
```
Build framework
### android DeployFramework
```
fastlane android DeployFramework
```
Deploy framework on Bintray

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
