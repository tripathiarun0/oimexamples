pscp -pw oracle dist\OIMPlugins.jar oracle@10.10.1.164:plugins/lib/OIMPlugins.jar
pscp -pw oracle plugin.xml oracle@10.10.1.164:plugins/plugin.xml
pscp -pw oracle EventHandlers.xml oracle@10.10.1.164:eventhandlers/db/ffcustom/EventHandlers.xml