package com.wcb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Entity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Database interface.
 * 
 * @author Kningt Wang
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class SimpleDao implements InitializingBean, BaseDao {

   final Log log = LogFactory.getLog(getClass());

   private SimpleJDBCTemplate jdbcTemplate;

   public SimpleJDBCTemplate getJdbcTemplate() {
      return jdbcTemplate;
   }

   public void setJdbcTemplate(SimpleJDBCTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   final Map<String, EntityOperation<?>> entityMap = new HashMap<String, EntityOperation<?>>();

   final Map<String, EntityOperation<?>> tableMap = new HashMap<String, EntityOperation<?>>();

   @SuppressWarnings( { "unchecked", "rawtypes" })
   public void afterPropertiesSet() throws Exception {
      log.info("Init db...");
      // scan package:
      Set<Class<?>> classes = new ClasspathScanner("com/wcb/test/bean",
         new ClasspathScanner.ClassFilter() {
            public boolean accept(Class<?> clazz) {
               boolean accept = clazz.isAnnotationPresent(Entity.class);
               return accept;
            }
         }).scan();
      for(Class<?> clazz : classes) {
         log.info("Found entity class: " + clazz.getName());
         EntityOperation<?> op = new EntityOperation(clazz);
         entityMap.put(clazz.getName(), op);
         tableMap.put(op.tableName.toLowerCase(), op);
      }
   }

   EntityOperation<?> getEntityOperation(Class<?> entityClass) {
      return getEntityOperationByEntityName(entityClass.getName());
   }

   EntityOperation<?> getEntityOperationByTableName(String tableName) {
      if(tableName == null || tableName.length() < 1) {
         throw new DbException("Unknown table: " + tableName);
      }

      EntityOperation<?> op = tableMap.get(tableName.toLowerCase());

      if(op == null) {
         throw new DbException("Unknown table: " + tableName);
      }

      return op;
   }

   EntityOperation<?> getEntityOperationByEntityName(String entityClassName) {
      EntityOperation<?> op = entityMap.get(entityClassName);
      if(op == null) {
         throw new DbException("Unknown entity: " + entityClassName);
      }
      return op;
   }

   final static RowMapper<Long> longRowMapper = new RowMapper<Long>() {
      public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
         return rs.getLong(1);
      }
   };

   final static RowMapper<Integer> intRowMapper = new RowMapper<Integer>() {
      public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
         return rs.getInt(1);
      }
   };
   
   final static RowMapper<String> stringRowMapper = new RowMapper<String>() {
	      public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	         return rs.getString(1);
	      }
	   };
	   
   final static RowMapper<Object> objectRowMapper = new RowMapper<Object>() {
		   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			   return rs.getObject(1);
		   }
	   };

   final Pattern SELECT_FROM = Pattern
      .compile("^(select|SELECT) .* (from|FROM) +(\\w+) ?.*$");

   /**
    * Execute any update SQL statement.
    * 
    * @param sql
    *           SQL query.
    * @param params
    *           SQL parameters.
    * @return Number of affected rows.
    */
   public int executeUpdate(String sql, Object... params) {
      return jdbcTemplate.update(sql, params);
   }

   public List<Map<String, Object>> queryBySQL(String sql) {
      return jdbcTemplate.queryForList(sql);
   }

   /**
    * Delete an entity by its id property. For example:
    * 
    * User user = new User(12300); // id=12300 db.deleteEntity(user);
    * 
    * @param entity
    *           Entity object instance.
    */
   public void deleteEntity(Object entity) throws DbException {
      EntityOperation<?> op = getEntityOperation(entity.getClass());
      try {
         SQLOperation sqlo = op.deleteEntity(entity);
         jdbcTemplate.update(sqlo.sql, sqlo.params);

      } catch (Exception e) {
         throw new DbException(e);
      }
   }

   /**
    * Update the entity with all updatable properties.
    * 
    * @param entity
    *           Entity object instance.
    */
   public void updateEntity(Object entity) {
      EntityOperation<?> op = getEntityOperation(entity.getClass());
      try {
         SQLOperation sqlo = op.updateEntity(entity);
         jdbcTemplate.update(sqlo.sql, sqlo.params);
      } catch (Exception e) {
         throw new DbException(e);
      }
   }

   /**
    * Update the entity with specified properties.
    * 
    * @param entity
    *           Entity object instance.
    * @param properties
    *           Properties that are about to update.
    */
   public void updateProperties(Object entity, String... properties) {
      if(properties.length == 0)
         throw new DbException("Update properties required.");
      EntityOperation<?> op = getEntityOperation(entity.getClass());
      SQLOperation sqlo = null;
      try {
         sqlo = op.updateProperties(entity, properties);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
      jdbcTemplate.update(sqlo.sql, sqlo.params);
   }

   /**
    * Query for long result. For example: <code>
     * long count = db.queryForLong("select count(*) from User where age>?", 20);
     * </code>
    * 
    * @param sql
    *           SQL query statement.
    * @param args
    *           SQL query parameters.
    * @return Long result.
    */
   public long queryForLong(String sql, Object... args) {
      log.info("Query for long: " + sql);
      List<Long> list = jdbcTemplate.query(sql, args, longRowMapper);
      if(list.isEmpty())
         throw new DbException("empty results.");
      if(list.size() > 1)
         throw new DbException("non-unique results.");
      return list.get(0);
   }

   /**
    * Query for int result. For example: <code>
     * int count = db.queryForLong("select count(*) from User where age>?", 20);
     * </code>
    * 
    * @param sql
    *           SQL query statement.
    * @param args
    *           SQL query parameters.
    * @return Int result.
    */
   public int queryForInt(String sql, Object... args) {
      log.info("Query for int: " + sql);
      List<Integer> list = jdbcTemplate.query(sql, args, intRowMapper);
      if(list.isEmpty())
         throw new DbException("empty results.");
      if(list.size() > 1)
         throw new DbException("non-unique results.");
      return list.get(0);
   }

   /**
    * Query for one single object. For example: <code>
     * User user = db.queryForObject("select * from User where name=?", "Michael");
     * </code>
    * 
    * @param sql
    *           SQL query statement.
    * @param args
    *           SQL query parameters.
    * @return The only one single result, or null if no result.
    */
   public <T> T queryForEntity(String sql, Object... args) {
      log.info("Query for object: " + sql);
      List<T> list = queryForList(sql, true, args);
      if(list.isEmpty())
         return null;
      if(list.size() > 1)
         throw new DbException("non-unique results.");
      return list.get(0);
   }

   /**
    * Query for list. For example: <code>
     * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 20);
     * </code>
    * 
    * @param <T>
    *           Return type of list element.
    * @param sql
    *           SQL query.
    * @param params
    *           SQL parameters.
    * @return List of query result.
    */
   @SuppressWarnings("unchecked")
   public <T> List<T> queryForList(String sql, Object... params) {
      return this.queryForList(sql, false, params);
   }

   /**
    * Query for list. For example: <code>
     * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 20);
     * </code>
    * 
    * @param <T>
    *           Return type of list element.
    * @param sql
    *           SQL query.
    * @param params
    *           SQL parameters.
    * @return List of query result.
    */
   @SuppressWarnings("unchecked")
   public <T> List<T> queryForList(String sql, boolean isEntity,
      Object... params) {
      log.info("Query for list: " + sql);

      if(isEntity) {
         Matcher m = SELECT_FROM.matcher(sql);
         if(!m.matches()) {
            throw new DbException("SQL grammar error: " + sql);
         }
         System.out.println("the tablename has bean getted == "+m.group(3));
         EntityOperation<?> op = getEntityOperationByTableName(m.group(3));
         
         return (List<T>) jdbcTemplate.query(sql, params, op.rowMapper);
      }

      return (List<T>) jdbcTemplate.queryForList(sql, params);// .query(sql,
                                                              // params,
                                                              // op.rowMapper);
   }
   
   
  

   /**
    * Query for limited list. For example: <code>
    * // first 5 users:
    * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 0, 5, 20);
     * </code>
    * 
    * @param <T>
    *           Return type of list element.
    * @param sql
    *           SQL query.
    * @param first
    *           First result index.
    * @param max
    *           Max results.
    * @param params
    *           SQL parameters.
    * @return List of query result.
    */
   public <T> List<T> queryForLimitedList(String sql, int first, int max,
      Object... args) {
      return this.queryForLimitedList(sql, first, max, false, args);
   }

   /**
    * Query for limited list. For example: <code>
    * // first 5 users:
    * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 0, 5, 20);
     * </code>
    * 
    * @param <T>
    *           Return type of list element.
    * @param sql
    *           SQL query.
    * @param first
    *           First result index.
    * @param max
    *           Max results.
    * @param params
    *           SQL parameters.
    * @return List of query result.
    */
   public <T> List<T> queryForLimitedList(String sql, int first, int max,
      boolean isEntity, Object... args) {
	  
      log.info("Query for limited list (first=" + first + ", max=" + max
         + "): " + sql);
      Object[] newArgs = new Object[args.length + 2];
      for(int i = 0; i < args.length; i++) {
         newArgs[i] = args[i];
      }
      newArgs[newArgs.length - 2] = first;
      newArgs[newArgs.length - 1] = max;
      return queryForList(buildLimitedSelect(sql), isEntity, newArgs);
   }
   /**
    * oracle环境下分页查询实现 (组合查询)
    * @param sql
    * @param first
    * @param max
    * @param isEntity
    * @param args
    * @return
    */
   public <T> List<T> queryForLimitedList4Oracle(Class<T> clazz,String sql, int first, int max,
		      boolean isEntity, Object... args) {
			  
		      log.info("Query for limited list (first=" + first + ", max=" + max
		         + "): " + sql);
		      Object[] newArgs = new Object[args.length + 2];
		      for(int i = 0; i < args.length; i++) {
		         newArgs[i] = args[i];
		      }
		      newArgs[newArgs.length - 2] = max;
		      newArgs[newArgs.length - 1] = first;
		      
		      String limitsql = buildLimitedSelect4Oracle(sql);
		      if(isEntity) {
		          EntityOperation<?> op = getEntityOperation(clazz);
		          return (List<T>) jdbcTemplate.query(limitsql, newArgs, op.rowMapper);
		       }
		       return (List<T>) jdbcTemplate.queryForList(limitsql, newArgs);
		   }
   
   /**
    * 简单查询
    * @param sql
    * @param first
    * @param max
    * @param isEntity
    * @param args
    * @return
    */
   public <T> List<T> queryForLimitedList4Oracle(String sql, int first, int max,
		      boolean isEntity, Object... args) {
			  
		   Object[] newArgs = new Object[args.length + 2];
		   for(int i = 0; i < args.length; i++) {
			   newArgs[i] = args[i];
		   }
		   newArgs[newArgs.length - 2] = max;
		   newArgs[newArgs.length - 1] = first;
	      if(isEntity) {
	         Matcher m = SELECT_FROM.matcher(sql);
	         if(!m.matches()) {
	            throw new DbException("SQL grammar error: " + sql);
	         }
	         System.out.println("the tablename has bean getted == "+m.group(3));
	         EntityOperation<?> op = getEntityOperationByTableName(m.group(3));
	         
	         return (List<T>) jdbcTemplate.query(sql, newArgs, op.rowMapper);
	      }
	      return (List<T>) jdbcTemplate.queryForList(sql, newArgs);
	   }
   /**
    * Get entity by its id.
    * 
    * @param <T>
    *           Entity class type.
    * @param clazz
    *           Entity class type.
    * @param idValue
    *           Id value.
    * @return Entity instance, or null if no such entity.
    */
   @SuppressWarnings("unchecked")
   public <T> T getById(Class<T> clazz, Object idValue) {
      EntityOperation<?> op = getEntityOperationByEntityName(clazz.getName());
      SQLOperation sqlo = op.getById(idValue);
      List<T> list = (List<T>) jdbcTemplate.query(sqlo.sql, sqlo.params,
         op.rowMapper);
      if(list.isEmpty())
         return null;
      if(list.size() > 1)
         throw new DbException("non-unique results.");
      return list.get(0);
   }

   /**
    * Create an entity in database, writing all insertable properties.
    * 
    * @param entity
    *           Entity object instance.
    */
   public long createEntity(Object entity) {
      EntityOperation<?> op = getEntityOperation(entity.getClass());
      SQLOperation sqlo = null;
      try {
         sqlo = op.insertEntity(entity);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

      return jdbcTemplate.save(sqlo.sql, sqlo.params);
   }

   /**
    * Create an Table in database
    * 
    * @param sql
    *           example:create table mytable (id integer,name varchar(20))
    */
   public void createTabel(String sql) throws Exception {

      jdbcTemplate.execute(sql);

   }

   /**
    * Delete an entity by its id value.
    * 
    * @param clazz
    *           Entity class type.
    * @param idValue
    *           Id value.
    */
   public void deleteById(Class<?> clazz, Object idValue) {
      EntityOperation<?> op = getEntityOperation(clazz);
      SQLOperation sqlo = op.deleteById(idValue);
      jdbcTemplate.update(sqlo.sql, sqlo.params);
   }

   String buildLimitedSelect(String select) {
      StringBuilder sb = new StringBuilder(select.length() + 20);
      boolean forUpdate = select.toLowerCase().endsWith(" for update");
      if(forUpdate) {
         sb.append(select.substring(0, select.length() - 11));
      } else {
         sb.append(select);
      }
      sb.append(" limit ?,?");
      if(forUpdate) {
         sb.append(" for update");
      }
      return sb.toString();
   }

   String buildLimitedSelect4Oracle(String sql){
	   log.info("the Orignal sql is :" +sql);
	   StringBuilder sbd = new StringBuilder();
	   sbd.append(" select * from (");
	   sbd.append(" select A.*  , ROWNUM  RN from ( ");
	   sbd.append(sql);
	   sbd.append(" )  A where ROWNUM <= ? )");
	   sbd.append(" where  RN > ?");
	   
	   String newsql = sbd.toString();
	   log.info("the created limit select sql is :" +newsql);
	   return newsql;
   }
   
   public void batchUpdate(String sql, final List<Object[]> dataList)
      throws Exception {
      jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
         public void setValues(PreparedStatement ps, int i) throws SQLException {
            Object[] obj = dataList.get(i);

            if(obj != null) {
               for(int j = 0; j < obj.length; j++) {
                  ps.setObject(j + 1, obj[j]);
               }
            }
         }

         public int getBatchSize() {
            return dataList.size();
         }
      });
   }
   
/**
 * 查询String 类型数据
 */
public String queryForString(String sql, Object... objects) {
	// TODO Auto-generated method stub
	log.info("Query for long: " + sql);
    List<String> list = jdbcTemplate.query(sql, objects, stringRowMapper);
    if(list.isEmpty())
       throw new DbException("empty results.");
    if(list.size() > 1)
       throw new DbException("non-unique results.");
    return list.get(0);
}

/**
 * 查询所有对象
 */
public <T> List<T> queryForList4Oracle(Class<T> clazz, String sql,
		boolean isEntiy, Object[] args) {
	
	log.info("Query for list: " + sql);

    if(isEntiy) {
  	 EntityOperation<?> op = getEntityOperation(clazz);
       return (List<T>) jdbcTemplate.query(sql, args, op.rowMapper);
    }

    return (List<T>) jdbcTemplate.queryForList(sql, args);
}

public Object queryForObject(String sql, Object... args) {
	return jdbcTemplate.queryForObject(sql,args, objectRowMapper);
}

 
}
