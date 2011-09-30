/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oim.api;

import Thor.API.Exceptions.tcAPIException;
import Thor.API.Exceptions.tcColumnNotFoundException;
import Thor.API.Exceptions.tcFormNotFoundException;
import Thor.API.Exceptions.tcNotAtomicProcessException;
import Thor.API.Exceptions.tcProcessNotFoundException;
import Thor.API.Exceptions.tcVersionNotDefinedException;
import Thor.API.Exceptions.tcVersionNotFoundException;
import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import Thor.API.tcResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author fforester
 */
public class FormsAPI {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private tcFormDefinitionOperationsIntf formDefOp;
    private tcFormInstanceOperationsIntf formInstOp;

    public FormsAPI(tcFormDefinitionOperationsIntf FormDefOp, tcFormInstanceOperationsIntf FormInstOp) {
        formDefOp = FormDefOp;
        formInstOp = FormInstOp;
    }

    /**
     * Return the Form Version
     *
     * @param ProcessInstanceKey
     * @exception OIMException
     */
    public int getFormVersion(long ProcessInstanceKey)  throws OIMException {
        logger.debug("Entering OIMForms.getFormVersion()");
        int result = 0;
        try {
            try {
                result = formInstOp.getProcessFormVersion(ProcessInstanceKey);
            } catch (tcNotAtomicProcessException ex) {
                logger.error("tcNotAtomicProcessException",ex);
                throw new OIMException("tcNotAtomicProcessException",ex);
            } catch (tcFormNotFoundException ex) {
                logger.error("tcFormNotFoundException",ex);
                throw new OIMException("tcFormNotFoundException",ex);
            } catch (tcAPIException ex) {
                logger.error("tcAPIException",ex);
                throw new OIMException("tcAPIException",ex);
            }
        } catch (tcVersionNotFoundException e) {
            try {
                long formDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
                result = formInstOp.getActiveVersion(formDefKey);
            } catch (tcVersionNotDefinedException ex) {
                logger.error("tcVersionNotDefinedException",ex);
                throw new OIMException("tcVersionNotDefinedException",ex);
            } catch (tcAPIException ex) {
                logger.error("tcAPIException",ex);
                throw new OIMException("tcAPIException",ex);
            } catch (tcProcessNotFoundException ex) {
                logger.error("tcProcessNotFoundException",ex);
                throw new OIMException("tcProcessNotFoundException",ex);
            } catch (tcFormNotFoundException ex) {
                logger.error("tcFormNotFoundException",ex);
                throw new OIMException("tcFormNotFoundException",ex);
            }
        }
        logger.debug("Exiting OIMForms.getFormVersion()");
        return result;

    }


    /**
     * Checks a Field to be the given Type
     *
     * @param Field - The Form Field to Check
     * @param Type - The Type to Compare
     * @param FormDefKey - The key of the Form Definition that contains
     *                     the Field
     * @exception OIMException
     */
    public boolean isFieldType(String Field, String Type, int FormVersion, long FormDefKey) throws OIMException {
        boolean result = false;
        try {
            logger.debug("Entering OIMForms.isFieldType()");

            tcResultSet fields = formDefOp.getFormFields(FormDefKey, FormVersion);
            for (int i = 0; i < fields.getRowCount(); i++) {
                fields.goToRow(i);
                String fieldName = fields.getStringValue("Structure Utility.Additional Columns.Name");
                if (fieldName.equalsIgnoreCase(Field)) {
                    String fieldType = fields.getStringValue("Structure Utility.Additional Columns.Field Type");
                    result = fieldType.equalsIgnoreCase(Type);
                    break;
                }
            }
        } catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcColumnNotFoundException ex) {
            logger.error("tcColumnNotFoundException",ex);
            throw new OIMException("tcColumnNotFoundException",ex);
        }
        logger.debug("Exiting OIMForms.isFieldType()");
        return result;
    }

    /**
     * Return A Map of the Field names and values of a process form
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @exception OIMException
     */
    public Map getProcessFormValues(long ProcessInstanceKey) throws Exception {
        return getProcessFormValues(ProcessInstanceKey, false, "yyyy-MM-dd");
    }

    /**
     * Return A Map of the Field names and values of a process form
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @param HideEncrypted - Change encrypted values to all asterisks
     * @param DateFormat - Date fields will be converted to this format
     * @exception OIMException
     */
    public Map getProcessFormValues(long ProcessInstanceKey, boolean HideEncrypted, String DateFormat) throws OIMException {
        try {
            logger.debug("Entering OIMForms.getProcessFormValues()");
            Map result = new HashMap();
            SimpleDateFormat dateFormat = DateFormat == null ? null : new SimpleDateFormat(DateFormat);
            Map labels = getProcessFormDisplayNames(ProcessInstanceKey);
            tcResultSet results = formInstOp.getProcessFormData(ProcessInstanceKey);
            if (results.getRowCount() == 1) {
                results.goToRow(0);
                String[] columns = results.getColumnNames();
                for (int i = 0; i < columns.length; i++) {
                    if (!labels.containsKey(columns[i])) {
                        continue;
                    }
                    if (HideEncrypted && isEncryptedField(ProcessInstanceKey, columns[i])) {
                        result.put(columns[i], "********");
                    } else if (isDateField(ProcessInstanceKey, columns[i])) {
                        java.util.Date value = results.getDate(columns[i]);
                        if (value.getTime() == 0L) {
                            result.put(columns[i], "");
                        } else if (DateFormat == null) {
                            result.put(columns[i], (new Timestamp(value.getTime())).toString());
                        } else {
                            result.put(columns[i], dateFormat.format(results.getDate(columns[i])));
                        }
                    } else {
                        result.put(columns[i], results.getStringValue(columns[i]));
                    }
                }
            } else {
                logger.error("Too Many Records for Instance " + ProcessInstanceKey);
                throw new OIMException("Too many process instance results ", ProcessInstanceKey);
            }
            logger.debug("Exiting OIMForms.getProcessFormValues()");
            return result;
        } catch (tcColumnNotFoundException ex) {
            logger.error("tcColumnNotFoundException",ex);
            throw new OIMException("tcColumnNotFoundException",ex);
        } catch (tcNotAtomicProcessException ex) {
            logger.error("tcNotAtomicProcessException",ex);
            throw new OIMException("tcNotAtomicProcessException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        }
    }

    /**
     * Return A Value from a process form
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @param Attribute - Process Form Field to return
     * @param HideEncrypted - Change encrypted values to all asterisks
     * @param DateFormat - Date fields will be converted to this format
     * @exception OIMException
     */
    public String getProcessFormValue(long ProcessInstanceKey, String Attribute, boolean HideEncrypted, String DateFormat) throws OIMException {

        logger.debug("Entering OIMForms.getProcessFormValue()");
        Map data = null;
        try {
            data = getProcessFormValues(ProcessInstanceKey, HideEncrypted, DateFormat);
        } catch (OIMException ex) {
            logger.error("getProcessFormValue",ex);
            throw ex;
        }
        String result = data.get(Attribute) == null ? "" : (String) data.get(Attribute);
        logger.debug("Exiting OIMForms.getProcessFormValue()");
        return result;
    }


    /**
     * Return an of Child Table Names for this Process Instance
     *
     * @param ProcessInstanceKey - Process Instance Key of the Parent Process
     * @exception OIMException
     */
    public String[] getChildTables(long ProcessInstanceKey) throws OIMException {
        try {
            logger.debug("Entering OIMForms.getChildTables()");
            int formVersion = getFormVersion(ProcessInstanceKey);
            long formDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
            tcResultSet childFormDefs = formInstOp.getChildFormDefinition(formDefKey, formVersion);
            String[] result = new String[childFormDefs.getRowCount()];
            for (int i = 0; i < childFormDefs.getRowCount(); i++) {
                childFormDefs.goToRow(i);
                result[i] = childFormDefs.getStringValue("Structure Utility.Table Name");
            }
            logger.debug("Exiting OIMForms.getChildTables()");
            return result;
        } catch (tcColumnNotFoundException ex) {
            logger.error("tcColumnNotFoundException",ex);
            throw new OIMException("tcColumnNotFoundException",ex);
        } catch (tcVersionNotDefinedException ex) {
            logger.error("tcVersionNotDefinedException",ex);
            throw new OIMException("tcVersionNotDefinedException",ex);
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        } catch (OIMException ex) {
            logger.error("OIMException",ex);
            throw ex;
        }
    }

    

    

    /**
     * Checks Field for ITResource Type
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @param Field - The Form Field to Check
     *
     * @exception OIMException
     */
    public boolean isITResourceField(long ProcessInstanceKey, String Field) throws OIMException {

        logger.debug("Entering OIMForms.isITResourceField()");
        int formVersion;
        try {
            formVersion = getFormVersion(ProcessInstanceKey);
        } catch (OIMException ex) {
            logger.error("isITResourceField/getFormVersion");
            throw ex;
        }
        long formDefKey = 0l;
        try {
            formDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        }

        boolean result=false;;
        try {
            result = isFieldType(Field, "ITResourceLookupField", formVersion, formDefKey);
        } catch (OIMException ex) {
            logger.error("isITResourceField/isFieldType");
            throw ex;
        }
        logger.debug("Exiting OIMForms.isITResourceField()");
        return result;
    }

    /**
     * Checks Field for Password Type
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @param Field - The Form Field to Check
     *
     * @exception OIMException
     */
    public boolean isPasswordField(long ProcessInstanceKey, String Field) throws OIMException {

        logger.debug("Entering OIMForms.isPasswordField()");
        int formVersion = 0;
        try {
            formVersion = getFormVersion(ProcessInstanceKey);
        } catch (OIMException ex) {
            logger.error("isPasswordField/getFormVersion");
            throw ex;
        }
        long formDefKey = 0l;
        try {
            formDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        }

        boolean result = false;
        try {
            result = isFieldType(Field, "PasswordField", formVersion, formDefKey);
        } catch (OIMException ex) {
            logger.error("isPasswordField/isFieldType");
            throw ex;
        }
        logger.debug("Exiting OIMForms.isPasswordField()");
        return result;
    }

    /**
     * Checks Field for Encrypted Type
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @param Field - The Form Field to Check
     *
     * @exception OIMException
     */
    public boolean isEncryptedField(long ProcessInstanceKey, String Field) throws OIMException {

        logger.debug("Entering OIMForms.isEncryptedField()");
        boolean result = false;
        int formVersion;
        try {
            formVersion = getFormVersion(ProcessInstanceKey);
        } catch (OIMException ex) {
            logger.error("isEncryptedField/getFormVersion");
            throw ex;
        }
        try
        {
            long formDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
            tcResultSet fields = formDefOp.getFormFields(formDefKey, formVersion);
            for (int i = 0; i < fields.getRowCount(); i++) {
                fields.goToRow(i);
                String fieldName = fields.getStringValue("Structure Utility.Additional Columns.Name");
                if (fieldName.equalsIgnoreCase(Field)) {
                    String fieldEncrypted = fields.getStringValue("Structure Utility.Additional Columns.Encrypted");
                    result = fieldEncrypted.equalsIgnoreCase("1");
                    break;
                }
            }
        }
        catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcColumnNotFoundException ex) {
            logger.error("tcColumnNotFoundException",ex);
            throw new OIMException("tcColumnNotFoundException",ex);
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        }
        logger.debug("Exiting OIMForms.isEncryptedField()");
        return result;

    }

    /**
     * Checks Field for Date Type
     *
     * @param ProcessInstanceKey - Process Instance Key
     * @param Field - The Form Field to Check
     *
     * @exception OIMException
     */
    public boolean isDateField(long ProcessInstanceKey, String Field) throws OIMException {
        try {
            logger.debug("Entering OIMForms.isDateField()");
            int formVersion = getFormVersion(ProcessInstanceKey);
            long formDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
            boolean result = isFieldType(Field, "DateFieldDlg", formVersion, formDefKey);
            logger.debug("Exiting OIMForms.isITResourceField()");
            return result;
        } catch (OIMException ex) {
            logger.error("OIMException",ex);
            throw ex;
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcAPIException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        }
    }

    /**
     * Returns a Map of Column Names and their associated display names
     *
     * @param ProcessInstanceKey - Process Instance Key
     *
     * @exception OIMException
     */
    public Map getProcessFormDisplayNames(long ProcessInstanceKey) throws OIMException {

        logger.debug("Entering OIMForms.getProcessFormDisplayNames()");
        Map result = new HashMap();
        int formVersion;
        try {
            formVersion = getFormVersion(ProcessInstanceKey);
        } catch (OIMException ex) {
            logger.error("getProcessFormDisplayNames/getFormVersion");
            throw ex;
        }

        try
        {
            long processFormDefKey = formInstOp.getProcessFormDefinitionKey(ProcessInstanceKey);
            tcResultSet results = formDefOp.getFormFields(processFormDefKey, formVersion);
            for (int i = 0; i < results.getRowCount(); i++) {
                results.goToRow(i);
                String field = results.getStringValue("Structure Utility.Additional Columns.Name");
                String label = results.getStringValue("Structure Utility.Additional Columns.Field Label");
                result.put(field, label);
            }
        } catch (tcAPIException ex) {
            logger.error("tcAPIException",ex);
            throw new OIMException("tcAPIException",ex);
        } catch (tcFormNotFoundException ex) {
            logger.error("tcFormNotFoundException",ex);
            throw new OIMException("tcFormNotFoundException",ex);
        } catch (tcProcessNotFoundException ex) {
            logger.error("tcProcessNotFoundException",ex);
            throw new OIMException("tcProcessNotFoundException",ex);
        } catch (tcColumnNotFoundException ex) {
            logger.error("tcColumnNotFoundException",ex);
            throw new OIMException("tcColumnNotFoundException",ex);
        }
        logger.debug("Exiting OIMForms.getProcessFormDisplayNames()");
        return result;

    }
    
}
