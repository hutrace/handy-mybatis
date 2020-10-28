package org.hutrace.handy.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * <p>获取SqlSession
 * <p>通过{@link PondSqlFactory}获取SqlSession
 * @author hu trace
 * @see PondSqlFactory
 * @see SqlSessionFactory
 * @see SqlSession
 * @since 1.8
 * @version 1.0
 * @time 2019年6月10日
 */
public class DefaultSqlSeesion {

	/**
	 * 获取默认SqlSession
	 * <p>通过{@link SqlSessionFactory#openSession()}获取
	 * @return
	 */
	public static SqlSession get() {
		return get(MyBatisConfig.DEFAULT_ID);
	}

	/**
	 * 获取默认SqlSession
	 * <p>通过{@link SqlSessionFactory#openSession(boolean)}获取
	 * @return
	 */
	public static SqlSession get(boolean autoCommit) {
		return get(MyBatisConfig.DEFAULT_ID, autoCommit);
	}

	/**
	 * 获取自定义SqlSession
	 * <p>通过{@link SqlSessionFactory#openSession()}获取
	 * @return
	 */
	public static SqlSession get(String sqlSessionFactoryId) {
		return PondSqlFactory.get(sqlSessionFactoryId).openSession();
	}

	/**
	 * 获取自定义SqlSession
	 * <p>通过{@link SqlSessionFactory#openSession(boolean)}获取
	 * @return
	 */
	public static SqlSession get(String sqlSessionFactoryId, boolean autoCommit) {
		return PondSqlFactory.get(sqlSessionFactoryId).openSession(autoCommit);
	}
	
}
