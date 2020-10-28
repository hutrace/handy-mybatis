package org.hutrace.handy.mybatis;

import org.apache.ibatis.session.SqlSession;

/**
 * 公共SqlSession获取类
 * <p>通过继承此类可直接获取当前需要操作的SqlSession
 * @author hu trace
 *
 */
public class MyBatisSqlSession {
	
	/**
	 * 获取当前需要操作的SqlSession
	 * @return
	 */
	protected SqlSession sqlSession() {
		return PondSqlFactory.getSqlSessions();
	}
	
}
