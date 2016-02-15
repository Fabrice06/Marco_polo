package marcopolo.dao;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DAO<T> {
	
	protected final static String SQL_GET_LAST_ID_INSERTED = "CALL SCOPE_IDENTITY()";
	
	protected static Log log = LogFactory.getLog(DAO.class);
	
	/**
	 * get object
	 * @param id
	 * @return object
	 */
	public abstract T find(Long id);
	
	
//	/**
//	 * Create data in DB
//	 * 
//	 * @param obj
//	 */
//	public abstract T create(T obj);
	
	
//	/**
//	 * Update data in DB 
//	 * 
//	 * @param obj
//	 */
//	public abstract T update(T obj);
	
	
	/**
	 * delete object
	 * @param id
	 */
	public abstract void delete(Long id);

	
}
