#!/usr/bin/env sh
#
# Gradle start up script for POSIX shells.
#

##############################################################################
# Check if JAVA_HOME is set, otherwise use java from PATH
##############################################################################
if [ -n "$JAVA_HOME" ]; then
    JAVA_BIN="$JAVA_HOME/bin/java"
else
    JAVA_BIN="java"
fi

##############################################################################
# Resolve the location of the script, so it can run from any directory
##############################################################################
PRG="$0"
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
APP_HOME=`dirname "$PRG"`

##############################################################################
# Locate Gradle Wrapper JAR and Properties
##############################################################################
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_PROPERTIES="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading Gradle Wrapper..."
    mkdir -p "$APP_HOME/gradle/wrapper"
    curl -s -L -o "$WRAPPER_JAR" "https://services.gradle.org/distributions/gradle-8.7-bin.zip"
fi

##############################################################################
# Run Gradle
##############################################################################
exec "$JAVA_BIN" -Dorg.gradle.appname=gradlew -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
