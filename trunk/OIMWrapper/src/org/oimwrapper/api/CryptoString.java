/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api;

import com.thortech.xl.crypto.*;
import org.apache.log4j.Logger;

/**
 *
 */
public class CryptoString {
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String secretKey = "DBSecretKey";
    
    private String plainText;

    public CryptoString()
    {
    }

    public String encrypt(String val) throws Exception {

        String encrypted = null;
        try {
            String encstr = val;
            encrypted = tcCryptoUtil.encrypt(encstr, secretKey, "UTF-8");
            return encrypted;
        } catch (Exception e) {
            logger.error("Crypto encrypt Error " + e.getMessage(),e);
            throw e;
        }
    }

    public String decrypt(String val) throws Exception {
        String encrypted = null;
        try {
            String encstr = val;
            encrypted = tcCryptoUtil.decrypt(encstr, secretKey, "UTF-8");
            return encrypted;
        } catch (Exception e) {
            logger.error("Crypto decrypt Error " + e.getMessage(),e);
            throw e;
        }
    }

            
}
