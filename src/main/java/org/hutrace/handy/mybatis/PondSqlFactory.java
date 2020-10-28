package org.hutrace.handy.mybatis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * <p>SqlSession工厂池
 * @author hu trace
 * @see SqlSessionFactory
 * @see SqlSession
 * @since 1.8
 * @version 1.0
 * @time 2019年9月24日
 */
public class PondSqlFactory {

	static Map<String, SqlSessionFactory> sqlFactoryMap;
//	static Map<Thread, SqlSession> sqlSessions;
	
	static ThreadLocal<SqlSession> threadSession;
	
	static {
		sqlFactoryMap = new ConcurrentHashMap<>();
		threadSession = new ThreadLocal<>();
	}
	
	/**
	 * <p>添加SqlSessionFactory
	 * @param key
	 * @param value
	 */
	public static void put(String key, SqlSessionFactory value) {
		sqlFactoryMap.put(key, value);
	}
	
	/**
	 * <p>获取SqlSessionFactory
	 * @param key
	 * @return
	 */
	public static SqlSessionFactory get(String key) {
		return sqlFactoryMap.get(key);
	}
	
	/**
	 * <p>获取SqlSession
	 * @param key
	 * @return
	 */
	public static SqlSession addSqlSessions(String sqlFactoryId) {
		SqlSession session;
		try {
			session = sqlFactoryMap.get(sqlFactoryId).openSession(false);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		threadSession.set(session);
		return session;
	}
	
	/**
	 * <p>获取SqlSession
	 * @param key
	 * @return
	 */
	public static SqlSession getSqlSessions() {
		return threadSession.get();
	}
	
	/**
	 * <p>删除SqlSession
	 * @param key
	 * @return
	 */
	public static void removeSqlSessions() {
		threadSession.remove();
	}
	
}
