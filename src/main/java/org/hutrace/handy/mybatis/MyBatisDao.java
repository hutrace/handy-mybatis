package org.hutrace.handy.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hutrace.handy.mapping.InterfaceDao;

/**
 * <p>MyBatis公共DAO
 * @author hutrace
 * @see MyBatisSqlSession
 * @since 1.8
 * @version 1.0
 */
public class MyBatisDao extends MyBatisSqlSession implements InterfaceDao {

	@Override
	public <T> T selectOne(String statement) throws Throwable {
		return selectOne(statement, null);
	}
	
	@Override
	public <T> T selectOne(String statement, Object parameter) throws Throwable {
		return sqlSession().selectOne(statement, parameter);
	}
	
	@Override
	public <T> List<T> selectList(String statement) throws Throwable {
		return selectList(statement, null);
	}
	
	@Override
	public <T> List<T> selectList(String statement, Object parameter) throws Throwable {
		return sqlSession().selectList(statement, parameter);
	}
	
	@Override
	public int selectInt(String statement) throws Throwable {
		return selectInt(statement, null);
	}
	
	@Override
	public int selectInt(String statement, Object parameter) throws Throwable {
		Object obj = selectOne(statement, parameter);
		if(obj == null) {
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	@Override
	public int update(String statement) throws Throwable {
		return update(statement, null);
	}
	
	@Override
	public int update(String statement, Object parameter) throws Throwable {
		return sqlSession().update(statement, parameter);
	}
	
	@Override
	public int delete(String statement) throws Throwable {
		return delete(statement, null);
	}
	
	@Override
	public int delete(String statement, Object parameter) throws Throwable {
		return sqlSession().delete(statement, parameter);
	}
	
	@Override
	public int insert(String statement) throws Throwable {
		return insert(statement, null);
	}
	
	@Override
	public int insert(String statement, Object parameter) throws Throwable {
		return sqlSession().insert(statement, parameter);
	}
	
	@Override
	public <T> Map<String, Object> queryPage(String statement, Object parameter) throws Throwable {
		int count = selectInt(statement + "Count", parameter);
		List<T> list = selectList(statement, parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("list", list);
		return map;
	}

	@Override
	public long selectLong(String statement) throws Throwable {
		return selectLong(statement, null);
	}

	@Override
	public long selectLong(String statement, Object parameter) throws Throwable {
		Object obj = selectOne(statement);
		if(obj == null) {
			return 0;
		}else {
			return Long.parseLong(obj.toString());
		}
	}

	@Override
	public String selectString(String statement) throws Throwable {
		return selectString(statement, null);
	}

	@Override
	public String selectString(String statement, Object parameter) throws Throwable {
		Object obj = selectOne(statement);
		return obj == null ? null : obj.toString();
	}
	
}
