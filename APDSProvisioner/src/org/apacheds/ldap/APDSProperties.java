/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.ldap;

import java.util.List;
import org.apache.commons.beanutils.DynaProperty;

/**
 *
 * @author fforester
 */
public class APDSProperties {

    public static final DynaProperty[] APDSPROPERTIES = {
            new DynaProperty("cn", String.class),
            new DynaProperty("sn", String.class),
            new DynaProperty("givenname", String.class),
            new DynaProperty("mail", String.class),
            new DynaProperty("description", String.class),
            new DynaProperty("uid", String.class),
            new DynaProperty("userPassword", String.class)
    };

    public static final DynaProperty[] APDS_RECON_PROPERTIES = {
            new DynaProperty("cn", String.class),
            new DynaProperty("sn", String.class),
            new DynaProperty("givenname", String.class),
            new DynaProperty("mail", String.class),
            new DynaProperty("description", String.class),
            new DynaProperty("uid", String.class),
            new DynaProperty("userGroups", List.class)
    };

    public static final String passwordAttribute = "userPassword";
    public static final String userGroupsAttribute = "userGroups";



}
