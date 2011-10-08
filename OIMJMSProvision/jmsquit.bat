setlocal
set JAVA_HOME=c:\jdk1.6.0_23
set PATH=%JAVA_HOME%\bin;%PATH%

set CLASSPATH=.;C:\javalibs\OIM11GClient\oimclient.jar;C:\javalibs\WLClient10.3\wlfullclient.jar;.\dist\OIMBanner.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIMWrapper\OIMWrapper.jar;C:\javalibs\OIM11Gext\log4j-1.2.8.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIM11Gext\jakarta-commons\commons-lang-2.3.jar;C:\javalibs\OIM11Gext\jakarta-commons\commons-io-1.1.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIM11GClient\lib\spring.jar;C:\javalibs\OIM11GClient\lib\commons-logging.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIM11GClient\lib\eclipselink.jar;C:\javalibs\OIM11GClient\conf
set SYSOPTS=-DXL.HomeDir=C:\javalibs\OIM11GClient -Djava.security.auth.login.config=C:\javalibs\OIM11GClient\conf\authwl.conf

REM java  %SYSOPTS% -cp %CLASSPATH% com.ai.jms.JMSBannerClientTester UDCIdentityMessage_AddUser1.xml
java  %SYSOPTS% -cp %CLASSPATH% org.oim.jmsprovision.JMSBannerClientTester quit.xml
