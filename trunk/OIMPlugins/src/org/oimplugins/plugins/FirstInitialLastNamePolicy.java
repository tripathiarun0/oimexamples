/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimplugins.plugins;

import java.util.Locale;
import java.util.Map;
import oracle.iam.identity.exception.UserNameGenerationException;
import oracle.iam.identity.usermgmt.api.UserNamePolicy;
import oracle.iam.identity.usermgmt.utils.UserNameGenerationUtil;
import oracle.iam.identity.usermgmt.utils.UserNamePolicyUtil;
import org.apache.log4j.Logger;

/**
 *
 *  this policy replaces the XL.DefaultUserNamePolicyImpl = oracle.iam.identity.usermgmt.impl.plugins.DefaultComboPolicy
 */
public class FirstInitialLastNamePolicy implements UserNamePolicy {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public String getUserNameFromPolicy(Map<String, String> reqData) throws UserNameGenerationException {

        logger.debug("Custom FirstInitialLastNamePolicy.getUserNameFromPolicy");
        String userName = null;

        String firstName = (String)reqData.get("First Name");
        String lastName = (String)reqData.get("Last Name");

        if ((firstName == null) || (firstName.length() == 0)) {

            UserNameGenerationException exception = new UserNameGenerationException("First Name is Invalid", "INVALIDFIRSTNAME");
            throw exception;
        }

        if ((lastName == null) || (lastName.length() == 0)) {
            UserNameGenerationException exception = new UserNameGenerationException("Last Name is Invalid", "INVALIDLASTNAME");
            throw exception;
        }

        String firstInitial = firstName.substring(0, 1);
        userName = firstInitial.concat(lastName);
        userName = UserNameGenerationUtil.trimWhiteSpaces(userName);

        if ((UserNamePolicyUtil.isUserExists(userName)) || (UserNamePolicyUtil.isUserNameReserved(userName))) {
            String baseName = userName;
            boolean userNameGenerated = false;

            for (int i = 1; i < 2147483647; i++) {
                userName = generateNextUserName(baseName, i);
                if (UserNameGenerationUtil.isUserNameExistingOrReserved(userName)) {
                    continue;
                }
                userNameGenerated = true;
                break;
            }

            if (!userNameGenerated) {
                throw new UserNameGenerationException("Failed To Generate Unique User Name", "GENERATEUSERNAMEFAILED");
            }
        }
        
        return userName;
    }

    public boolean isUserNameValid(String userName, Map<String, String> reqData) {
        return isUserNameLexicallyValid(userName, reqData);
    }

    public String getDescription(Locale locale) {
        return "First Initial Last Name No Random Character UserNamePolicy";
    }

    private String generateNextUserName(String baseName, int index) {
        String userName = "";
        if (baseName.indexOf("@") != -1) {
            String emailId = baseName.substring(0, baseName.indexOf("@"));
            String domain = baseName.substring(baseName.indexOf("@"), baseName.length());

            userName = emailId + index + domain;
        } else {
            userName = baseName + index;
        }
        return userName;
    }

    private boolean isUserNameLexicallyValid(String userName, Map<String, String> reqData) {
        boolean isUserNameValid = false;

        if ((userName == null) || (userName.length() == 0)) {
            return isUserNameValid;
        }

        String firstName = (String) reqData.get("First Name");
        if ((firstName == null) || (firstName.length() == 0)) {
            return isUserNameValid;
        }

        String lastName = (String) reqData.get("Last Name");
        if ((lastName == null) || (lastName.length() == 0)) {
            return isUserNameValid;
        }

        if (lastName.length() >= userName.length()) {
            return isUserNameValid;
        }

        String middleName = userName.substring(1, userName.length() - lastName.length());

        if (!areAllValidCharacters(middleName)) {
            return isUserNameValid;
        }

        String extractedFirstInitial = userName.substring(0, 1);
        String extractedLastName = userName.substring(userName.length() - lastName.length());

        if (!Character.isLetter(extractedFirstInitial.charAt(0))) {
            return isUserNameValid;
        }

        if ((extractedFirstInitial.equalsIgnoreCase(firstName.substring(0, 1))) && (extractedLastName.equalsIgnoreCase(lastName))) {
            isUserNameValid = true;
        }
        return isUserNameValid;
    }

    private boolean areAllValidCharacters(String middleName) {
        boolean areAllCharacters = true;
        for (int i = 0; i < middleName.length(); i++) {
            if (!Character.isLetter(middleName.charAt(i))) {
                areAllCharacters = false;
                break;
            }
        }
        return areAllCharacters;
    }
}