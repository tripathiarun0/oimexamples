setlocal
set JAVA_HOME=c:\jdk1.6.0_23
set PATH=%JAVA_HOME%\bin;%PATH%

set SPRING=C:\javalibs\springldap\spring-ldap-core-1.3.1.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\springldap\spring-ldap-core-tiger-1.3.1.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\apfuse210lib\spring-core-3.0.5.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\apfuse210lib\spring-beans-3.0.5.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\apfuse210lib\spring-tx-3.0.5.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\apfuse210lib\spring-context-3.0.5.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\apfuse210lib\spring-asm-3.0.5.RELEASE.jar
set SPRING=%SPRING%;C:\javalibs\apfuse210lib\spring-expression-3.0.5.RELEASE.jar


set CLASSPATH=.;C:\javalibs\OIM11GClient\oimclient.jar;C:\javalibs\WLClient10.3\wlfullclient.jar;.\dist\APDSProvisioner.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIMWrapper\OIMWrapper.jar;C:\javalibs\OIM11Gext\log4j-1.2.8.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\LDAPHelper\LDAPHelper.jar;C:\javalibs\OIM11Gext\jakarta-commons\commons-beanutils.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIM11Gext\jakarta-commons\commons-lang-2.3.jar;C:\javalibs\OIM11Gext\jakarta-commons\commons-io-1.1.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIM11GClient\lib\spring.jar;C:\javalibs\OIM11GClient\lib\commons-logging.jar
set CLASSPATH=%CLASSPATH%;C:\javalibs\OIM11GClient\lib\eclipselink.jar;C:\javalibs\OIM11GClient\conf
set SYSOPTS=-DXL.HomeDir=C:\javalibs\OIM11GClient -Djava.security.auth.login.config=C:\javalibs\OIM11GClient\conf\authwl.conf

java %SYSOPTS% -cp %CLASSPATH%;%SPRING% org.fredforester.apacheds.recon.APDSRecon
