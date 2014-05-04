package com.wcb.dao;

import java.util.List;
import java.util.Map;

 
public interface BaseDao {
	/**
	 * Execute any update SQL statement.
	 * 
	 * @param sql
	 *            SQL query.
	 * @param params
	 *            SQL parameters.
	 * @return Number of affected rows.
	 */
	public int executeUpdate(String sql, Object... params) throws Exception;

	/**
	 * Delete an entity by its id property. For example:
	 * 
	 * User user = new User(12300); // id=12300 db.deleteEntity(user);
	 * 
	 * @param entity
	 *            Entity object instance.
	 */
	public void deleteEntity(Object entity) throws Exception;

	/**
	 * Update the entity with all updatable properties.
	 * 
	 * @param entity
	 *            Entity object instance.
	 */
	public void updateEntity(Object entity) throws Exception;

	/**
	 * Update the entity with specified properties.
	 * 
	 * @param entity
	 *            Entity object instance.
	 * @param properties
	 *            Properties that are about to update.
	 */
	public void updateProperties(Object entity, String... properties)
			throws Exception;

	/**
	 * Query for long result. For example: <code>
	     * long count = db.queryForLong("select count(*) from User where age>?", 20);
	     * </code>
	 * 
	 * @param sql
	 *            SQL query statement.
	 * @param args
	 *            SQL query parameters.
	 * @return Long result.
	 */
	public long queryForLong(String sql, Object... args);

	/**
	 * Query for int result. For example: <code>
	     * int count = db.queryForLong("select count(*) from User where age>?", 20);
	     * </code>
	 * 
	 * @param sql
	 *            SQL query statement.
	 * @param args
	 *            SQL query parameters.
	 * @return Int result.
	 */
	public int queryForInt(String sql, Object... args);
	
	
	
	
	public Object queryForObject(String sql, Object... args);

	/**
	 * Query for one single object. For example: <code>
	     * User user = db.queryForObject("select * from User where name=?", "Michael");
	     * </code>
	 * 
	 * @param sql
	 *            SQL query statement.
	 * @param args
	 *            SQL query parameters.
	 * @return The only one single result, or null if no result.
	 */
	public <T> T queryForEntity(String sql, Object... args);

	/**
	 * Query for list. For example: <code>
	     * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 20);
	     * </code>
	 * 
	 * @param <T>
	 *            Return type of list element.
	 * @param sql
	 *            SQL query.
	 * @param params
	 *            SQL parameters.
	 * @return List of query result.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryForList(String sql, Object... params);
	
	public <T> List<T> queryForList(String sql, boolean isEntity, Object... params);

	/**
	 * Query for limited list. For example: <code>
	 * // first 5 users:
	 * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 0, 5, 20);
	     * </code>
	 * 
	 * @param <T>
	 *            Return type of list element.
	 * @param sql
	 *            SQL query.
	 * @param first
	 *            First result index.
	 * @param max
	 *            Max results.
	 * @param params
	 *            SQL parameters.
	 * @return List of query result.
	 */
	public <T> List<T> queryForLimitedList(String sql, int first, int max,
			Object... args);
	
	public <T> List<T> queryForLimitedList(String sql, int first, int max, boolean isEntity, Object... args);

	/**
	 * oracle 环境专用 
	 * @param sql
	 * @param first
	 * @param max
	 * @param isEntity
	 * @param args
	 * @return
	 */
	public <T> List<T> queryForLimitedList4Oracle(Class<T> clazz,String sql, int first, int max, boolean isEntity, Object... args);

	
	
	/**
	 * Get entity by its id.
	 * 
	 * @param <T>
	 *            Entity class type.
	 * @param clazz
	 *            Entity class type.
	 * @param idValue
	 *            Id value.
	 * @return Entity instance, or null if no such entity.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getById(Class<T> clazz, Object idValue);

	/**
	 * Create an entity in database, writing all insertable properties.
	 * 
	 * @param entity
	 *            Entity object instance.
	 */
	public long createEntity(Object entity) throws Exception;

	/**
	 * Create an table by sql.
	 * 
	 * @param sql
	 *
	 */
	
	public void createTabel(String sql) throws Exception;

    /**
     * Delete an entity by its id value.
     * 
     * @param clazz
     *            Entity class type.
     * @param idValue
     *            Id value.
     */
	
	public void deleteById(Class<?> clazz, Object idValue) throws Exception;

	public void batchUpdate(String sql, final List<Object[]> dataList) throws Exception;
	
	public List<Map<String, Object>> queryBySQL(String sql) throws Exception;
	
	
	public String queryForString(String sql, Object... objects);
	
	
	
	
	public <T>List<T> queryForList4Oracle(Class<T> clazz,String sql, boolean isEntiy, Object[] args);
	

}
