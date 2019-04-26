REM Lancement avec java version >= 11
if exist D:\opt\ (set Disk=D) ELSE (set Disk=C)
Set JAVA_HOME=%Disk%:\opt\jdk-11.0.2
Set JFX_HOME=%Disk%:\opt\javafx-sdk-11.0.2
Set PROJECT_HOME=%Disk%:\opt\whatido
Set CLASSPATH=%PROJECT_HOME%\lib\jnativehook-2.1.0.jar;.;%PROJECT_HOME%\lib\whatido-3.0.1.jar;%JFX_HOME%/lib/javafx.base.jar;%JFX_HOME%/lib/javafx.graphics.jar;%JFX_HOME%/lib/javafx.controls.jar;


start "" "%JAVA_HOME%\bin\javaw" -Xms128m -Xmx256m -Droot=%PROJECT_HOME% -Djava.library.path=%JFX_HOME%\lib --add-modules javafx.controls,javafx.base,javafx.media,javafx.graphics --module-path %JFX_HOME%\lib;%CLASSPATH% -cp %CLASSPATH% -Dhome=%PROJECT_HOME% com.jlp.whatido.Main 
 
Exit