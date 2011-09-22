package org.ldaphelper.ldapclient;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.AttributesMapper;


public class DynamicAttributeMapper implements AttributesMapper {

    private DynaProperty[] props;

    private final Log log = LogFactory.getLog(getClass());

    /*
     * create a DynamicAttributeMapper containing the specified DynaProperties
     */
    public DynamicAttributeMapper(DynaProperty[] props) {
        this.props = props;
    }

    /*
     * you must use the constructor for properties
     */
    private DynamicAttributeMapper() {
    }


    /*
     * maps ldap attributes to a DynaBean
     */
    public Object mapFromAttributes(Attributes attributes) throws NamingException {

        DynaClass userDynaClass = new BasicDynaClass("user", null, props);
        LazyLdapBean user = new LazyLdapBean(userDynaClass);

        /*
        try {
            user = userDynaClass.newInstance();
        } catch (IllegalAccessException ex) {
            throw new NamingException("IllegalAccessException");
        } catch (InstantiationException ex) {
            throw new NamingException("InstantiationException");
        }
        */

        //DynaProperty[] props = bean.getDynaClass().getDynaProperties();
        for(DynaProperty propName : props)
        {
            //log.debug("DynamicAttributeMapper property " + propName.toString());
            
            //if (propName.getName().equalsIgnoreCase("userPassword"))
            //    continue;
            Attribute attr = attributes.get(propName.getName());
            if (attr != null)
            {
                log.debug("Set Property " + propName.getName() + " to " + attr.get().toString());
                user.set(propName.getName(), (String)attr.get().toString());
            }
        }
        return user;
    }
}
