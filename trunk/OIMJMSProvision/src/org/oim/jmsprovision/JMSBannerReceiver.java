package org.oim.jmsprovision;

import org.oim.jmsprovision.BaseJMSClient;
import org.oim.jmsprovision.JMSTrustedRecon;
import org.oimjmsprovision.exceptions.InvalidDataException;
import org.oimjmsprovision.exceptions.InvalidUserStatusException;
import org.oimjmsprovision.exceptions.ProcessEventFailedException;
import org.oimjmsprovision.exceptions.ReconEventFailedException;
import org.oimjmsprovision.exceptions.RoleMaintenanceException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.*;
import javax.jms.*;
import javax.rmi.PortableRemoteObject;
import org.apache.log4j.Logger;

public class JMSBannerReceiver extends BaseJMSClient implements MessageListener {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private TopicConnectionFactory tconFactory;
    private TopicConnection tcon;
    private TopicSession tsession;
    private TopicSubscriber tsubscriber;
    private Topic topic;
    private boolean quit = false;

    // error que 
    // set the weblogic delivery count to 0
    // this way we can handle the exceptions properly
    private QueueConnectionFactory errorqcf = null;
    private QueueConnection errorqc = null;
    private QueueSession errorqsess = null;
    private Queue errorq = null;
    private QueueSender errorqsndr = null;
    

    public void onMessage(Message message) {
        logger.debug("Here is the message ");

        String xmlString = null;
        String errMsg = null;

        try
        {
            Enumeration e = message.getPropertyNames();
            while(e.hasMoreElements())
            {
                String key = (String)e.nextElement();
                logger.debug(key + " = " + message.getStringProperty(key));
            }
            xmlString = message.getStringProperty("xml");

        }
        catch(JMSException je)
        {
            errMsg = "JMSError " + je.getErrorCode();
            logger.error(errMsg);
            //throw new RuntimeException(errMsg,je);
            return;
        }

        if (xmlString == null)
        {
            errMsg = "JMSError xmlString is NULL";
            logger.error(errMsg);
            //throw new RuntimeException(errMsg);
            return;
        }

        if (xmlString.equalsIgnoreCase("quit")) {
            synchronized (this) {
                quit = true;
                this.notifyAll();
                return;
            }
        }

        JMSTrustedRecon prov = new JMSTrustedRecon();
        logger.debug("Initilizing Banner Provisioner");
        try
        {
            prov.initClient();
            prov.getParameters();
        }
        catch(Exception e)
        {
            errMsg = "Banner Recon Init Failed";
            logger.error(errMsg,e);
            return;
            // write to error que before throwing an exception to avoid
            // the exception loop
            //throw new RuntimeException(errMsg,e);
        }

        logger.debug("Processing Banner XML String ");
        boolean requeue = true;
        try
        {
            InputStream is = prov.processString(xmlString);
            List<Map> ulist = prov.parseMessage(is);
            prov.processUser(ulist);
            requeue = false;
        }
        catch(ReconEventFailedException e)
        {
            errMsg = "ReconEvent Failed - Record needs to be queued";
            logger.error(errMsg,e);
        }
        catch(ProcessEventFailedException e)
        {
            errMsg = "ProcessEvent Failed - Record in OIM needs to be retried";
            logger.error(errMsg,e);
            //requeue = false;
        }
        catch(InvalidDataException e)
        {
            errMsg = "Data Validation Failed - Record needs to be queued";
            logger.error(errMsg,e);
        }
        catch(InvalidUserStatusException e)
        {
            errMsg = "User Status Error - Record needs to be queued";
            logger.error(errMsg,e);
        }
        catch(RoleMaintenanceException e)
        {
            errMsg = "Role Maintenance Failed - Record needs to be queued";
            logger.error(errMsg,e);
        }
        
        catch(Exception e)
        {
            errMsg = "Unknown Error - Record needs to be queued";
            logger.error(errMsg,e);
        }

        if (requeue)
        {
            logger.error("Queueing Error Record");
            try
            {
                if (errMsg == null)
                    errMsg = "Banner Error";
                message = errorqsess.createTextMessage();
                message.setStringProperty("xml",xmlString);
                message.setStringProperty("bannerror",errMsg);
                errorqsndr.send(message);
            }
            catch(JMSException e)
            {
                logger.error("Requeue Failed",e);
            }
        }
        

    }

    public void init(Context ctx, String topicName, String errorqname) throws NamingException, JMSException {
        String JMSQFACTORY_NAME = getProperty("JMSQFACTORY_NAME");

        errorqcf = (QueueConnectionFactory)ctx.lookup(JMSQFACTORY_NAME);
        errorqc = errorqcf.createQueueConnection();
        errorqsess = errorqc.createQueueSession(false, 0);
        errorq = (Queue)ctx.lookup(errorqname);
        errorqsndr = errorqsess.createSender(errorq);
        
        // setup Banner Topic
        tconFactory = (TopicConnectionFactory) PortableRemoteObject.narrow(ctx.lookup(JMSQFACTORY_NAME), TopicConnectionFactory.class);
        tcon = tconFactory.createTopicConnection();
        tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(topicName), Topic.class);
        tsubscriber = tsession.createSubscriber(topic);
        tsubscriber.setMessageListener(this);
        tcon.start();
    }

    public void close() throws JMSException {

        errorqsndr.close();
        errorqsess.close();
        errorqc.close();
        
        tsubscriber.close();
        tsession.close();
        tcon.close();
    }

    public static void main(String[] args) throws Exception {
        
        
        JMSBannerReceiver tr = new JMSBannerReceiver();
        if (tr.loadProperties() == null)
        {
            tr.logger.error("Failed to load properties");
            return;
        }
        InitialContext ic = tr.getInitialContext();

        
        tr.init(ic, tr.getProperty("OIMJMS_TOPIC_NAME"),tr.getProperty("OIMJMS_ERRORQUEUE_NAME"));
        
        tr.logger.debug("JMS Ready To Receive Messages (To quit, send a \"quit\" message).");
        synchronized (tr) {
            while (!tr.quit) {
                try {
                    tr.wait();
                } catch (InterruptedException ie) {
                }
            }
        }
        tr.close();
    }

    private InitialContext getInitialContext() throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, getProperty("INITIAL_CONTEXT_FACTORY"));
        env.put(Context.PROVIDER_URL, getProperty("PROVIDER_URL"));
        env.put(Context.SECURITY_PRINCIPAL, getProperty("SECURITY_PRINCIPAL"));
        env.put(Context.SECURITY_CREDENTIALS, getProperty("SECURITY_CREDENTIALS"));
        env.put("weblogic.jndi.createIntermediateContexts", "true");
        return new InitialContext(env);
    }
}
