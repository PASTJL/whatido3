Set PROJECT_HOME=C:\opt\whatido
Set CLASSPATH=%PROJECT_HOME%\lib\jnativehook-2.1.0.jar;.;%PROJECT_HOME%\lib\whatido-3.0.1.jar;
Set JAVA_HOME=C:\Program Files (x86)\Java\jre1.8.0_121\bin
start "" "%JAVA_HOME%\javaw" -Droot=%PROJECT_HOME% -Dhome=%PROJECT_HOME% -cp %CLASSPATH% com.jlp.whatido.Main 
Exit