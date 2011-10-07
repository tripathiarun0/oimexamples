/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oimwrapper.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 */
public class QueryProcessor implements ResultProcessor {

    /**
     * A Map of the results keys and values.
     */
    protected Map results;
    protected Map connectionInfo;
    private String keyField;
    public static Logger logger = Logger.getLogger("OIM.QUERYPROCESSOR");

    /**
     * Default Constructor.
     */
    public QueryProcessor() {
        results = new HashMap();
    }

    /**
     * Gets the results found from the QueryHolder.
     *
     * @return A Map of the results keys and values.
     */
    public Map getResults() {
        return results;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }
    //public int countResults() {
    //    return results.size();
    //}

    /**
     * Processes the entries present in a result set.
     *
     * @param Results The result set to process.
     * @exception Exception
     */
    public void process(ResultSet Results) throws Exception {

        logger.debug("Entering ::process()");
        try {
            while (Results.next()) {
                ResultSetMetaData columnInfo = Results.getMetaData();
                Map record = new HashMap();
                for (int i = 1; i <= columnInfo.getColumnCount(); i++) {
                    String column = columnInfo.getColumnName(i);
                    String value = Results.getString(column) == null ? "" : Results.getString(column);
                    logger.debug("Adding field " + column + " " + value);
                    record.put(column, value);
                }
                if (keyField != null) {
                    String cbVal = (String) record.get(keyField);
                    ArrayList list2 = (ArrayList) results.get(cbVal);
                    if (list2 == null) {
                        list2 = new ArrayList();
                    }
                    list2.add(record);
                    results.put(cbVal, list2);
                }

            }
        } catch (Exception e) {
            logger.error("Execute Exception", e);
        }
        logger.debug("Exiting ::process()");
    }

    public void openConnection(Map ITResourceData) {
        //userQueryProcessor processor = new userQueryProcessor(this);
        logger.debug("Entering ::openConnection()");
        connectionInfo = new HashMap();
        connectionInfo.putAll(ITResourceData);

    }

    public void closeConnection() {
    }

    public Map runQuery(String sql) {
        try {
            logger.debug("Entering ::runQuery");
            QueryHolder Query = new QueryHolder(sql);
            //lookupQueryProcessor processor = new lookupQueryProcessor();
            logger.debug("Connection Info " + connectionInfo);
            logger.debug("Excuting Query");
            DBConnectionPool.execute(connectionInfo, logger, Query, this);
            logger.debug("Query Results " + results);
            logger.debug("Query Results 2 " + getResults());
            Map tmp = getResults();
            return tmp;
        } catch (Exception e) {
            logger.error("runQuery Error + " + e.getMessage(), e);
            return null;
        }
    }
}
