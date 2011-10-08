/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwebservices.services.interfaces;

import java.util.List;
import javax.jws.WebService;
import org.oimwebservices.model.MapEntry;

/**
 *
 * @author fforester
 */
@WebService()
public interface OIMLookupManager extends OIMManagerBaseInterface {

    public List<MapEntry> getLookupValues(String lookup);

}
