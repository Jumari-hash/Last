#!/bin/sh

<<<<<<< HEAD
##############################################################################
# Gradle startup script for UN*X
##############################################################################

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

GRADLE_USER_HOME=${GRADLE_USER_HOME:-$HOME/.gradle}

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \"-Dorg.gradle.appname=$APP_BASE_NAME\" -classpath \"$CLASSPATH\" org.gradle.wrapper.GradleWrapperMain \"$@\"
=======
# Gradle wrapper script for Unix

APP_BASE_NAME=`basename "$0"`
DIRNAME=`dirname "$0"`

# Locate Java
if [ -z "$JAVA_HOME" ]; then
  JAVA_CMD=$(which java)
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

if [ ! -x "$JAVA_CMD" ]; then
  echo "ERROR: JAVA_HOME is not set correctly or java not found in PATH."
  exit 1
fi

CLASSPATH="$DIRNAME/gradle/wrapper/gradle-wrapper.jar"

exec "$JAVA_CMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
>>>>>>> 00dac746f4768d9795e6d4ce86dc9ae59fc26435
