/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient;

/**
 *
 * An Object to hold the paging attributes for ldap
 */
public class PagingAttributes {

    private String base;
    private String userSearch;
    private String groupSearch;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getGroupSearch() {
        return groupSearch;
    }

    public void setGroupSearch(String groupSearch) {
        this.groupSearch = groupSearch;
    }

    public String getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }



}
