package org.hutrace.handy.mybatis;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.hutrace.handy.exception.AppLoaderException;
import org.hutrace.handy.language.SystemProperty;
import org.hutrace.handy.mybatis.log.Slf4jImpl;

/**
 * <p>MyBatis配置器
 * @author hutrace
 * @see PooledDataSource
 * @since 1.8
 * @version 1.0
 */
public class MyBatisConfig {
	
	public static final String DEFAULT_ID = "defaultDatabaseFactory";
	/**
	 * <p>当前连接工厂ID
	 */
	private String id;
	/**
	 * <p>数据库驱动
	 */
	private String driver;
	/**
	 * <p>数据库地址全路径
	 */
	private String url;
	/**
	 * <p>数据库登录名
	 */
	private String username;
	/**
	 * <p>数据库登密码
	 */
	private String password;
	/**
	 * <p>心跳查询语句
	 */
	private String poolPingQuery = "SELECT 1 FROM DUAL";
	/**
	 * <p>心跳间隔时间
	 */
	private int poolPingConnectionsNotUsedFor = 1000 * 60 * 60;
	/**
	 * <p>是否启用心跳机制
	 */
	private boolean poolPingEnabled = true;
	/**
	 * <p>在重新尝试连接之前等待的时间
	 */
	private int poolTimeToWait = 20000;
	/**
	 * <p>活动连接的最大数量
	 */
	private int poolMaximumActiveConnections = 10;
	/**
	 * <p>连接在再次被发出之前可以使用的最长时间
	 */
	private int poolMaximumCheckoutTime = 20000;
	/**
	 * <p>最大空闲连接数
	 */
	private int poolMaximumIdleConnections = 5;
	private Class<? extends Log> logImpl = Slf4jImpl.class;
	public void setId(String id) {
		this.id = id;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPoolPingQuery(String poolPingQuery) {
		this.poolPingQuery = poolPingQuery;
	}
	public void setPoolPingConnectionsNotUsedFor(int poolPingConnectionsNotUsedFor) {
		this.poolPingConnectionsNotUsedFor = poolPingConnectionsNotUsedFor;
	}
	public void setPoolPingEnabled(boolean poolPingEnabled) {
		this.poolPingEnabled = poolPingEnabled;
	}
	public void setPoolTimeToWait(int poolTimeToWait) {
		this.poolTimeToWait = poolTimeToWait;
	}
	public void setPoolMaximumActiveConnections(int poolMaximumActiveConnections) {
		this.poolMaximumActiveConnections = poolMaximumActiveConnections;
	}
	public void setPoolMaximumCheckoutTime(int poolMaximumCheckoutTime) {
		this.poolMaximumCheckoutTime = poolMaximumCheckoutTime;
	}
	public void setPoolMaximumIdleConnections(int poolMaximumIdleConnections) {
		this.poolMaximumIdleConnections = poolMaximumIdleConnections;
	}
	@SuppressWarnings("unchecked")
	public void setLogImplClass(String logImpl) throws ClassNotFoundException {
		this.logImpl = (Class<? extends Log>) Class.forName(logImpl);
	}
	public void setLogImpl(Class<? extends Log> logImpl) {
		this.logImpl = logImpl;
	}
	
	public void build() throws AppLoaderException {
		String _id = id == null ? DEFAULT_ID : id;
		if(PondSqlFactory.get(_id) != null) {
			throw new AppLoaderException(SystemProperty.get("mybatis.config.error.id", _id));
		}
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setPoolPingQuery(poolPingQuery);
		dataSource.setPoolPingConnectionsNotUsedFor(poolPingConnectionsNotUsedFor);
		dataSource.setPoolPingEnabled(poolPingEnabled);
		dataSource.setPoolTimeToWait(poolTimeToWait);
		dataSource.setPoolMaximumActiveConnections(poolMaximumActiveConnections);
		dataSource.setPoolMaximumCheckoutTime(poolMaximumCheckoutTime);
		dataSource.setPoolMaximumIdleConnections(poolMaximumIdleConnections);
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment(_id, transactionFactory, dataSource);
		Configuration config = new Configuration(environment);
		config.setLogImpl(logImpl);
		PondSqlFactory.put(_id, new SqlSessionFactoryBuilder().build(config));
	}
	
	@Override
	public String toString() {
		return "MyBatisConfig [id=" + id + ", driver=" + driver + ", url=" + url + ", username=" + username
				+ ", password=" + password + "]";
	}
	
}
