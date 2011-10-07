/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oimwrapper.db;

import java.sql.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;

/**
 * Stores the elements of a statement so they can be prepared and processed.
 */
public class QueryHolder {

    /**
     * The base statement for the QueryHolder.
     */
    protected String statement;
    /**
     * A Map of the indexes and values to set in the statement.
     */
    protected Map values;

    /**
     * Default Constructor.
     *
     * @param Statement The statement to use for the base of the QueryHolder.
     */
    public QueryHolder(String Statement) {
        statement = Statement;
        values = new HashMap();
    }

    /**
     * Gets the base statement for the QueryHolder.
     *
     * @return The base statement for the QueryHolder.
     */
    public String getQuery() {
        return statement;
    }

    /**
     * Prepares the statement with all the values specified.
     *
     * @param Statement The prepared statement object to use.
     * @exception Exception
     */
    public void prepare(PreparedStatement Statement) throws Exception {
        for (Iterator iterator = values.keySet().iterator(); iterator.hasNext();) {
            Integer index = (Integer) iterator.next();
            Object value = values.get(index);
            if (value instanceof String) {
                Statement.setString(index.intValue(), (String) value);
            } else if (value instanceof Long) {
                Statement.setLong(index.intValue(), ((Long) value).longValue());
            } else if (value instanceof Integer) {
                Statement.setInt(index.intValue(), ((Integer) value).intValue());
            } else if (value instanceof java.util.Date) {
                Statement.setDate(index.intValue(), new java.sql.Date(((java.util.Date) value).getTime()));
            } else {
                throw new Exception("Unknown type : " + value.getClass().getName());
            }
        }
    }

    /**
     * Sets a value at the given index.
     *
     * @param Index The index to set the value at.
     * @param Value The value to set.
     */
    public void set(int Index, String Value) {
        values.put(new Integer(Index), Value);
    }

    /**
     * Sets a value at the given index.
     *
     * @param Index The index to set the value at.
     * @param Value The value to set.
     */
    public void set(int Index, int Value) {
        values.put(new Integer(Index), new Integer(Value));
    }

    /**
     * Sets a value at the given index.
     *
     * @param Index The index to set the value at.
     * @param Value The value to set.
     */
    public void set(int Index, long Value) {
        values.put(new Integer(Index), new Long(Value));
    }

    /**
     * Sets a value at the given index.
     *
     * @param Index The index to set the value at.
     * @param Value The value to set.
     */
    public void set(int Index, java.util.Date Value) {
        values.put(new Integer(Index), Value);
    }

    /**
     * Retrieves a multi-part task attribute containing a QueryHolder.
     *
     * @param Caller The taskBase instance that the QueryHolder is being retrieved from.
     * @param AttributeName The name of the task attribute to retrieve the QueryHolder from.
     * @return The value of the QueryHolder.
     * @exception Exception
     */
    public static QueryHolder getQueryAttribute(Map taskMap, String AttributeName) throws Exception {
        String statement = (String) taskMap.get(AttributeName);
        for (int i = 1; !StringUtils.isBlank((String) taskMap.get(AttributeName + Integer.toString(i))); i++) {
            statement += " " + taskMap.get(AttributeName + Integer.toString(i));
        }
        //Caller.logger.debug("statement = " + statement);
        return new QueryHolder(statement);
    }
}
