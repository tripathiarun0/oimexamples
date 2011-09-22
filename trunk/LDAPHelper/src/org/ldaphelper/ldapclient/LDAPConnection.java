/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient;

import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 *
 */
public class LDAPConnection {
    
    private String connectUrl;
    private String connectBase;
    private String connectUserDn;
    private String connectPassword;
    private LdapContextSource lcs;
    protected LdapTemplate ldapTemplate;

    public void setConnectBase(String connectBase) {
        this.connectBase = connectBase;
    }

    public void setConnectPassword(String connectPassword) {
        this.connectPassword = connectPassword;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public void setConnectUserDn(String connectUserDn) {
        this.connectUserDn = connectUserDn;
    }

    

    public void connect() throws LDAPHelperException
    {
        //System.out.println("connectUrl " + connectUrl);
        //System.out.println("connectBase " + connectBase);
        //System.out.println("connectUserDn " + connectUserDn);
        //System.out.println("connectPassword " + connectPassword);
        
        try {
            lcs = new LdapContextSource();

            lcs.setUrl(connectUrl);
            lcs.setBase(connectBase);
            lcs.setUserDn(connectUserDn);
            lcs.setPassword(connectPassword);
            lcs.setPooled(true);
            lcs.setDirObjectFactory(org.springframework.ldap.core.support.DefaultDirObjectFactory.class);
            lcs.afterPropertiesSet();
            ldapTemplate = new LdapTemplate(lcs);

        } catch (Exception e) {
            e.printStackTrace();
            throw new LDAPHelperException(e);
        }

        

    }

}
