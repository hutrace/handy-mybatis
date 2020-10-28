package org.hutrace.handy.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.hutrace.handy.http.HttpRequest;
import org.hutrace.handy.http.HttpResponse;
import org.hutrace.handy.http.listener.ChannelListener;
import org.hutrace.handy.mybatis.exception.RollbackException;

/**
 * 面向切面（AOP）包含database的抽象类
 * <p>该类在{@link ChannelListener}上进一步封装
 * <p>实现了{@link SqlSession}，更方便的操作数据库
 * @author hu trace
 */
public abstract class DatabaseCompleteListener implements ChannelListener {
	
	protected String databaseConfigId = MyBatisConfig.DEFAULT_ID;
	
	public void setDatabaseConfigId(String databaseConfigId) {
		this.databaseConfigId = databaseConfigId;
	}

	@Override
	public void closed(HttpRequest request, HttpResponse response, Object parameter, Object result) {
		SqlSession sql = PondSqlFactory.addSqlSessions(databaseConfigId);
		try {
			complete(sql, request, response, parameter, result);
			sql.commit();
		}catch (RollbackException e) {
			e.printStackTrace();
			sql.rollback();
		}catch (Throwable e) {
			e.printStackTrace();
			sql.commit();
		}
		sql.close();
		PondSqlFactory.removeSqlSessions();
	}
	
	/**
	 * 流程完成的监听方法
	 * <p>该方法与业务层完全脱离
	 * @param request 不解释了撒
	 * @param response 不解释了撒
	 * @param sql {@link SqlSession}，你不需要自己处理它，该类以经处理了
	 * @param parameter 该次请求输入的参数
	 * @param result 该次请求响应的参数
	 * @throws RollbackException 你的流程中，如果你需要事物回滚，就抛出此异常
	 * @throws Exception 其它异常，不会事务回滚
	 */
	public abstract void complete(SqlSession sql, HttpRequest request, HttpResponse response,
			Object parameter, Object result) throws RollbackException, Exception;

}
