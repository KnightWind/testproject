package com.wcb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 
 * @author xiaopang
 *
 */
public class SimpleJDBCTemplate extends JdbcTemplate {
   
	public Connection getConnection(){
		try {
			
			return getDataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
   /**
    * save 
    * @param sql
    * @param args
    * @return the key
    * @throws DataAccessException
    */
   public long save(String sql, Object... args) throws DataAccessException {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      update(new SimplePreparedStatementCreator2(sql), 
         newArgPreparedStatementSetter(args), keyHolder);
      
      return keyHolder.getKey().longValue();
   }
   
   public int update(final PreparedStatementCreator psc,
      final PreparedStatementSetter pss, final KeyHolder generatedKeyHolder)
      throws DataAccessException 
   {
      logger.debug("Executing SQL update and returning generated keys");

      return execute(psc, new PreparedStatementCallback<Integer>() {
         public Integer doInPreparedStatement(PreparedStatement ps)
            throws SQLException {

            try {
               if(pss != null) {
                  pss.setValues(ps);
               }
               
               int rows = ps.executeUpdate();

               List<Map<String, Object>> generatedKeys = generatedKeyHolder
                  .getKeyList();
               generatedKeys.clear();
               ResultSet keys = ps.getGeneratedKeys();
               
               if(keys != null) {
                  try {
                     RowMapperResultSetExtractor<Map<String, Object>> rse = 
                        new RowMapperResultSetExtractor<Map<String, Object>>(
                        getColumnMapRowMapper(), 1);
                     generatedKeys.addAll(rse.extractData(keys));
                  } finally {
                     JdbcUtils.closeResultSet(keys);
                  }
               }

               if (logger.isDebugEnabled()) {
                  logger.debug("SQL update affected " + rows + " rows");
               }
               
               return rows;
            } finally {
               if(pss instanceof ParameterDisposer) {
                  ((ParameterDisposer) pss).cleanupParameters();
               }
            }
         }
      });
   }
   
   /**
    * Simple adapter for PreparedStatementCreator, 
    * allowing to use a plain SQL statement.
    * Note: in JdbcTemplate, there is SimplePreparedStatementCreator 
    * inner class which equals SimplePreparedStatementCreator2. 
    * But the class is private.    
    */
   private static class SimplePreparedStatementCreator2 
      implements PreparedStatementCreator, SqlProvider 
   {
      private final String sql;

      public SimplePreparedStatementCreator2(String sql) {
         this.sql = sql;
      }

      public PreparedStatement createPreparedStatement(Connection con) 
         throws SQLException 
      {
         return con.prepareStatement(this.sql);
      }
      
      public String getSql() {
         return this.sql;
      }
   }
}
