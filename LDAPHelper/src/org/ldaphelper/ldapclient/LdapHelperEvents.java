package org.ldaphelper.ldapclient;

import java.util.List;

public interface LdapHelperEvents
{
    /*
     * event used to send paging events. an event is fired everytime
     * a page size is hit
     */
    public void pagingEvent(List<Object> results);
}

