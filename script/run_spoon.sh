#!/bin/sh

cd `dirname $0`/..
PROJECT_ROOT=$PWD

# Spoon settings
SPOON_VERSION=2.0.0-SNAPSHOT
SPOON_DIR=$PROJECT_ROOT/workspace/spoon-runner-${SPOON_VERSION}
SPOON_ZIP_URL=https://oss.sonatype.org/content/repositories/snapshots/com/squareup/spoon/spoon-runner/2.0.0-SNAPSHOT/spoon-runner-2.0.0-20181002.234303-49.zip
SPOON_OUTPUT_DIR=$PROJECT_ROOT/workspace/spoon-output

# APK files
APP_APK_FILE=$PROJECT_ROOT/example/build/outputs/apk/debug/example-debug.apk
TEST_APK_FILE=$PROJECT_ROOT/example/build/outputs/apk/androidTest/debug/example-debug-androidTest.apk

if [ ! -d "$PROJECT_ROOT/workspace" ]; then
  mkdir "$PROJECT_ROOT/workspace"
fi

if [ ! -d "$SPOON_DIR" ]; then
  curl $SPOON_ZIP_URL -o $PROJECT_ROOT/workspace/spoon_tmp.zip
  unzip $PROJECT_ROOT/workspace/spoon_tmp.zip -d $PROJECT_ROOT/workspace/
fi

if [ ! -f "$APP_APK_FILE" ]; then
  echo "App apk is missing."
  echo "run ./gradlew clean assembleDebug assembleDebugAndroidTest"
  exit 1
fi

if [ ! -f "$TEST_APK_FILE" ]; then
  echo "Test apk is missing."
  echo "run ./gradlew clean assembleDebug assembleDebugAndroidTest"
  exit 1
fi

$SPOON_DIR/bin/spoon-runner \
  "$TEST_APK_FILE" \
  "$APP_APK_FILE" \
  --output $SPOON_OUTPUT_DIR \
  --clear-app-data \
  --grant-all \
  --shard
