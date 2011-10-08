/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwebservices.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.oimwebservices.model.MapEntry;
import org.oimwebservices.services.interfaces.OIMLookupManager;
import org.oimwrapper.api.OIMlookupUtilities;
import org.oimwrapper.exceptions.OIMHelperException;

/**
 *
 */
@WebService(serviceName = "OIMLookupManager", endpointInterface = "org.oimwebservices.services.interfaces.OIMLookupManager")
public class OIMLookupManagerImpl extends OIMManagerBase implements OIMLookupManager {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    public List<MapEntry> getLookupValues(String lookup) {

        logger.info("getLookupValues");
        List<MapEntry> mapList = new ArrayList<MapEntry>();
        try {
            createClientSession();
            OIMlookupUtilities oiml = new OIMlookupUtilities(client.getClient());
            HashMap lookupvals = new HashMap();
            Map valmap = oiml.getLookupValues(lookup);
            if (valmap == null)
            {
                logger.error("No Lookup Found for " + lookup);
                return null;
            }
            Set<String> keys = valmap.keySet();
            for(String key : keys)
            {
                MapEntry me = new MapEntry();
                String val = (String)valmap.get(key);
                me.setKey(key);
                me.setValue(val);
                mapList.add(me);
            }
            return mapList;

        } catch (OIMHelperException e) {
            return null;
        }
    }

}
