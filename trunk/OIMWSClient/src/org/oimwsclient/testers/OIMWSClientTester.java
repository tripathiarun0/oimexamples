/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwsclient.testers;




import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.wslookupmanager.MapEntry;
import org.wslookupmanager.OIMLookupManager;
import org.wslookupmanager.OIMLookupManager_Service;
import org.wsusermanager.OIMUserManager;
import org.wsusermanager.OIMUserManager_Service;
import org.wsusermanager.OimUser;

/**
 *
 * @author fforester
 */
public class OIMWSClientTester {

    private static String userUrl = "http://localhost:8080/OIMWebServices/OIMUserManager?wsdl";
    private static String lookupUrl = "http://localhost:8080/OIMWebServices/OIMLookupManager?wsdl";

    public static void main(String[] args)
    {

        System.out.println("Do Users");

        URL url = null;
        try {
            url = new URL(userUrl);
        } catch (MalformedURLException ex) {
        }

        OIMUserManager_Service oius = new OIMUserManager_Service(url);
        OIMUserManager oiu = oius.getOIMUserManagerImplPort();

        OimUser oimUser = oiu.getUser("GF10001");

        if (oimUser == null)
        {
            System.out.println("Get User Failed");
            return;
        }

        System.out.println("Got User By ID " + printUser(oimUser));
        String strUserKey = oimUser.getUserKey();
        oimUser = oiu.getUserByKey(Long.parseLong(strUserKey));

        System.out.println("Got User By Key " + printUser(oimUser));

        System.out.println("Do Lookup");

        try {
            url = new URL(lookupUrl);
        } catch (MalformedURLException ex) {
        }

        OIMLookupManager_Service lms = new OIMLookupManager_Service(url);
        OIMLookupManager lm = lms.getOIMLookupManagerImplPort();

        List<MapEntry> meList = lm.getLookupValues("Lookup.USR_PROCESS_TRIGGERS");
        System.out.println("Trigger Lookup " + meList);

        for(MapEntry me : meList)
        {
            System.out.println("key " + me.getKey() + " val " + me.getValue());
        }

    }

    public static String printUser(OimUser user)
    {
        return "OIMUser [userId=" + user.getUserId() + ", userKey=" + user.getUserKey()
		+ ", firstName=" + user.getFirstName() + ", lastName=" + user.getLastName()
		+ ", email=" + user.getEmail() + "]";
    }

}
