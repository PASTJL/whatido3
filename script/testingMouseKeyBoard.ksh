#!/bin/bash
export PROJECT_HOME=/opt/whatido
export CLASSPATH=${PROJECT_HOME}/lib/jnativehook-2.1.0.jar:.:${PROJECT_HOME}/lib/whatido-3.0.0.jar;
export JAVA_HOME=/opt/jdk1.8.0_121/jre/bin/
${JAVA_HOME}/java -Droot=${PROJECT_HOME} -Dhome=${PROJECT_HOME} -cp ${CLASSPATH} com.jlp.whatido.MyMouseKeyboardListener 
