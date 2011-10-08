package org.oim.jmsprovision;
import org.oim.jmsprovision.BaseJMSClient;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.naming.*;
import javax.jms.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import javax.rmi.PortableRemoteObject;


public class JMSBannerClientTester extends BaseJMSClient {
   private static InitialContext ctx = null;
   private static TopicConnectionFactory tcf = null;
   private static TopicConnection tc = null;
   private static TopicSession tsess = null;
   private static TopicPublisher tp = null;
   private static Topic t = null;
   //private static QueueSender qsndr = null;
   private static TextMessage message = null;
   // NOTE: The next two lines set the name of the Queue Connection Factory
   //       and the Queue that we want to use.

   private Logger logger = Logger.getLogger(this.getClass().getName());

   public JMSBannerClientTester()  {
       super();
   }

   public static void main(String args[]) {
       
       JMSBannerClientTester xmltester = new JMSBannerClientTester();
       HashMap messageData = new HashMap();

       String xmlString = null;
               
       try {
           xmlString = processFile(args[0]);
       } catch (Exception e) {
           xmltester.logger.error(e);
           return;
       }

       if (xmltester.loadProperties() == null)
       {
            xmltester.logger.error("Failed to load properties");
            return;
       }
       messageData.put("xml", xmlString);
       xmltester.sendMessage(messageData);
       
   }

   public void sendMessage(HashMap messageData) {
       // create InitialContext
       Hashtable properties = new Hashtable();
       properties.put(Context.INITIAL_CONTEXT_FACTORY,getProperty("INITIAL_CONTEXT_FACTORY"));
       // NOTE: The port number of the server is provided in the next line,
       //       followed by the userid and password on the next two lines.
       properties.put(Context.PROVIDER_URL, getProperty("PROVIDER_URL"));
       properties.put(Context.SECURITY_PRINCIPAL, getProperty("SECURITY_PRINCIPAL"));
       properties.put(Context.SECURITY_CREDENTIALS, getProperty("SECURITY_CREDENTIALS"));
       try {
           ctx = new InitialContext(properties);
       } catch (NamingException ne) {
           logger.error(ne.getMessage(), ne);
           System.exit(0);
       }
       logger.debug("Got InitialContext " + ctx.toString());
       // create QueueConnectionFactory
       try {
           tcf = (TopicConnectionFactory) PortableRemoteObject.narrow(ctx.lookup(getProperty("JMSQFACTORY_NAME")),TopicConnectionFactory.class);
       }
       catch (NamingException ne) {
           logger.error(ne.getMessage(), ne);
           System.exit(0);
       }
       System.out.println("Got QueueConnectionFactory " + tcf.toString());
       // create QueueConnection
       try {
           tc = tcf.createTopicConnection();
       }
       catch (JMSException jmse) {
           jmse.printStackTrace(System.err);
           System.exit(0);
       }
       System.out.println("Got QueueConnection " + tc.toString());
       // create QueueSession
       try {
           tsess = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
           System.exit(0);
       }
       System.out.println("Got QueueSession " + tsess.toString());
       // lookup Queue
       try {
           t = (Topic) PortableRemoteObject.narrow(ctx.lookup(getProperty("OIMJMS_TOPIC_NAME")), Topic.class);
       }
       catch (NamingException ne) {
           logger.error(ne.getMessage(), ne);
           System.exit(0);
       }
       System.out.println("Got Queue " + t.toString());
       // create QueueSender
       try {
           tp = tsess.createPublisher(t);
       }
       catch (JMSException jmse) {
           jmse.printStackTrace(System.err);
           System.exit(0);
       }
       System.out.println("Got QueueSender " + tp.toString());
       // create TextMessage
       try {
           message = tsess.createTextMessage();
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
           System.exit(0);
       }
       logger.error("Got TextMessage " + message.toString());
       // set message text in TextMessage
       try {
           Set keys = messageData.keySet();
           Iterator ki = keys.iterator();
           while(ki.hasNext())
           {
               String key = (String)ki.next();
               String val = (String)messageData.get(key);
               message.setStringProperty(key,val);
           }

       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
           System.exit(0);
       }
       System.out.println("Set text in TextMessage " + message.toString());
       // send message
       try {
           tp.send(message);
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
           System.exit(0);
       }
       System.out.println("Sent message ");
       // clean up
       try {
           message = null;
           tp.close();
           tp = null;
           t = null;
           tsess.close();
           tsess = null;
           tc.close();
           tc = null;
           tcf = null;
           ctx = null;
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
       }
       logger.debug("Cleaned up and done.");
   }


    public static String processFile(String fileName) throws Exception {
        if (fileName == null)
            throw new Exception("Invalid File Name");
        
        String doc = FileUtils.readFileToString(new File(fileName), null);
        return doc;

        
    }
    
}