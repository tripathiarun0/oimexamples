package org.oimwebservices.services.interfaces;

import java.util.List;
import javax.jws.WebService;
import org.oimwebservices.model.OIMUser;



@WebService()
public interface OIMUserManager extends OIMManagerBaseInterface {
	
	public OIMUser getUser(String userId);
	public OIMUser getUserByKey(long userKey);
	public List<String> getUserRoles(String userId);
	public List<String> getUserRolesByKey(long userKey);
}
