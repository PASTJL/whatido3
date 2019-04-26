#!/bin/bash
export PROJECT_HOME=/opt/whatido
export JAVA_HOME=/opt/jdk-11.0.2
export JFX_HOME=/opt/javafx-sdk-11.0.2

export CLASSPATH=${PROJECT_HOME}/lib/jnativehook-2.1.0.jar:.:${PROJECT_HOME}/lib/whatido-3.0.1.jar:${JFX_HOME}/lib/javafx.base.jar:${JFX_HOME}/lib/javafx.graphics.jar:${JFX_HOME}/lib/javafx.controls.jar

${JAVA_HOME}/bin/java -Xms128m -Xmx256m -Droot=${PROJECT_HOME} -Djava.library.path=${JFX_HOME}/lib --add-modules javafx.controls,javafx.base,javafx.media,javafx.graphics --module-path ${JFX_HOME}/lib:${CLASSPATH} -cp ${CLASSPATH} -Dhome=${PROJECT_HOME} com.jlp.whatido.Main 