/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient;

import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;

/**
 *
 */
public class LazyLdapBean extends LazyDynaBean {

    public LazyLdapBean(DynaClass dynaClass) {
        super(dynaClass);
    }

    public LazyLdapBean(String name) {
        super(name);
    }

    public LazyLdapBean() {
        super();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        DynaProperty[] props = this.getDynaClass().getDynaProperties();
        sb.append("[");
        for(DynaProperty prop : props)
        {
            
            if (get(prop.getName()) == null)
            {
                sb.append(prop.getName());
                sb.append(":");
                sb.append("null");
            }
            else
            {
                sb.append(prop.getName());
                sb.append(":");
                sb.append(get(prop.getName()).toString());
            }
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }



}
