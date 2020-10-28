package org.hutrace.handy.mybatis;

import java.lang.reflect.InvocationTargetException;

import org.apache.ibatis.session.SqlSession;
import org.hutrace.handy.http.exception.ExceptionBean;
import org.hutrace.handy.http.explorer.Explorer;
import org.hutrace.handy.mybatis.annotation.DatabaseFactory;
import org.hutrace.handy.mybatis.exception.RollbackException;
import org.hutrace.handy.server.ServerRequest;
import org.hutrace.handy.server.ServerResponse;

/**
 * MyBatis的SqlSession资源管理器
 * @author hu trace
 *
 */
public class SqlSessionExplorer implements Explorer {

	@Override
	public void open(ServerRequest request, ServerResponse response) {
		Class<?> clazs = request.control();
		DatabaseFactory df = clazs.getAnnotation(DatabaseFactory.class);
		if(df == null) {
			PondSqlFactory.addSqlSessions(MyBatisConfig.DEFAULT_ID);
		}else {
			PondSqlFactory.addSqlSessions(df.value());
		}
	}

	@Override
	public void close(ServerRequest request, ServerResponse response, ExceptionBean eb) {
		SqlSession sqlSession = PondSqlFactory.getSqlSessions();
		boolean rollback = false;
		if(eb != null && eb.getEx() != null) {
			Throwable t = eb.getEx();
			if(t instanceof InvocationTargetException) {
				t = ((InvocationTargetException) t).getTargetException();
			}
			if(t instanceof RollbackException) {
				rollback = true;
				sqlSession.rollback();
			}
		}
		if(!rollback) {
			sqlSession.commit();
		}
		sqlSession.close();
		PondSqlFactory.removeSqlSessions();
	}

}
