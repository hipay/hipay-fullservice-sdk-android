# Fastlane documentation

## Installation

Copy the file `fastlane/.env.sample` in order to create a `fastlane/.env` file.

Complete the following variables `KEYSTORE`, `KEYSTORE_PASSWORD` and `KEY_PASSWORD` using your password vault.

## Available Actions

### Android

#### android SetEnv

```sh
[bundle exec] fastlane android SetEnv
```

Set environment variables

#### android Test

```sh
[bundle exec] fastlane android Test
```

Runs all the tests

#### android BuildDemo

```sh
[bundle exec] fastlane android BuildDemo
```

Build demo

#### android DeployDemo

```sh
[bundle exec] fastlane android DeployDemo
```

Deploy a new version to AppCenter

#### android BuildFramework

```sh
[bundle exec] fastlane android BuildFramework
```

Build framework

#### android DeployFramework

```sh
[bundle exec] fastlane android DeployFramework
```

Deploy framework on MavenCentral

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
