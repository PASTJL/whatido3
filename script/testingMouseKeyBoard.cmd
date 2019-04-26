if exist D:\opt\ (set Disk=D) ELSE (set Disk=C)
Set PROJECT_HOME=%Disk%:\opt\whatido
Set JAVA_HOME=%Disk%:\opt\jdk-11.0.2
Set CLASSPATH=%PROJECT_HOME%\lib\jnativehook-2.1.0.jar;.;%PROJECT_HOME%\lib\whatido-3.0.1.jar;

"%JAVA_HOME%\bin\java" -Droot=%PROJECT_HOME% -Dhome=%PROJECT_HOME% -cp %CLASSPATH%  com.jlp.whatido.MyMouseKeyboardListener  
