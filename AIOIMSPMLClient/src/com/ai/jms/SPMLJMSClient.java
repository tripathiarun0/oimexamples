package com.ai.jms;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.naming.*;
import javax.jms.*;
import org.apache.log4j.Logger;


public class SPMLJMSClient extends BaseJMSClient {
   private static InitialContext ctx = null;
   private static QueueConnectionFactory qcf = null;
   private static QueueConnection qc = null;
   private static QueueSession qsess = null;
   private static Queue q = null;
   private static QueueSender qsndr = null;
   private static TextMessage message = null;
   // NOTE: The next two lines set the name of the Queue Connection Factory
   //       and the Queue that we want to use.
   
   private Logger logger = Logger.getLogger(this.getClass().getName());

   public SPMLJMSClient()  {
       super();
   }

   public static void main(String args[]) {

       HashMap messageData = new HashMap();

       messageData.put("name","Freddy Woods");
       messageData.put("firstName", "Fredddy");
       messageData.put("lastName","Woods");
       messageData.put("userId", "fwoods");
       messageData.put("email", "fwoods@idmnation.com");
       SPMLJMSClient spmlc = new SPMLJMSClient();
       spmlc.sendMessage(messageData);
   }
   
   public void sendMessage(HashMap messageData) {
       // create InitialContext
       Hashtable properties = new Hashtable();
       properties.put(Context.INITIAL_CONTEXT_FACTORY,INITIAL_CONTEXT_FACTORY);
       // NOTE: The port number of the server is provided in the next line,
       //       followed by the userid and password on the next two lines.
       properties.put(Context.PROVIDER_URL, PROVIDER_URL);
       properties.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
       properties.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
       try {
           ctx = new InitialContext(properties);
       } catch (NamingException ne) {
           logger.error(ne.getMessage(), ne);
           System.exit(0);
       }
       logger.debug("Got InitialContext " + ctx.toString());
       // create QueueConnectionFactory
       try {
           qcf = (QueueConnectionFactory)ctx.lookup(AIQFACTORY_NAME);
       }
       catch (NamingException ne) {
           logger.error(ne.getMessage(), ne);
           System.exit(0);
       }
       System.out.println("Got QueueConnectionFactory " + qcf.toString());
       // create QueueConnection
       try {
           qc = qcf.createQueueConnection();
       }
       catch (JMSException jmse) {
           jmse.printStackTrace(System.err);
           System.exit(0);
       }
       System.out.println("Got QueueConnection " + qc.toString());
       // create QueueSession
       try {
           qsess = qc.createQueueSession(false, 0);
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
           System.exit(0);
       }
       System.out.println("Got QueueSession " + qsess.toString());
       // lookup Queue
       try {
           q = (Queue) ctx.lookup(AISPML_QUEUE_NAME);
       }
       catch (NamingException ne) {
           logger.error(ne.getMessage(), ne);
           System.exit(0);
       }
       System.out.println("Got Queue " + q.toString());
       // create QueueSender
       try {
           qsndr = qsess.createSender(q);
       }
       catch (JMSException jmse) {
           jmse.printStackTrace(System.err);
           System.exit(0);
       }
       System.out.println("Got QueueSender " + qsndr.toString());
       // create TextMessage
       try {
           message = qsess.createTextMessage();
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
           qsndr.send(message);
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
           System.exit(0);
       }
       System.out.println("Sent message ");
       // clean up
       try {
           message = null;
           qsndr.close();
           qsndr = null;
           q = null;
           qsess.close();
           qsess = null;
           qc.close();
           qc = null;
           qcf = null;
           ctx = null;
       }
       catch (JMSException jmse) {
           logger.error(jmse.getMessage(), jmse);
       }
       logger.debug("Cleaned up and done.");
   }

   
}