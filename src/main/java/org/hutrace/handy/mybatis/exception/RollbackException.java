package org.hutrace.handy.mybatis.exception;

import org.hutrace.handy.exception.HandyserveException;

/**
 * <p>
 * 事物回滚需要抛出的异常
 * <p>
 * 在业务层抛出此异常将会回滚数据持久层
 * 
 * @author hutrace
 * @since 1.8
 * @version 1.0
 */
public class RollbackException extends HandyserveException {

	private static final long serialVersionUID = -3387516993124229948L;

	/**
	 * <p>异常构造方法
	 * @param msg 异常信息，可通过{@link RollbackException#getMessage()}获取
	 */
	public RollbackException(String msg) {
		super(msg);
	}
	
	/**
	 * <p>异常构造方法
	 * @param code 异常码，可通过{@link #code()}获取
	 * @param msg 异常信息，可通过{@link RollbackException#getMessage()}获取
	 */
	public RollbackException(int code, String msg) {
		super(code, msg);
	}

	/**
	 * <p>异常构造方法
	 * @param code 异常码，可通过{@link #code()}获取
	 * @param msg 异常信息，可通过{@link RollbackException#getMessage()}获取
	 * @param e 其它异常
	 */
	public RollbackException(int code, String msg, Throwable e) {
		super(code, msg, e);
	}

	/**
	 * <p>异常构造方法
	 * @param msg 异常信息，可通过{@link RollbackException#getMessage()}获取
	 * @param e 其它异常
	 */
	public RollbackException(String msg, Throwable e) {
		super(msg, e);
	}

	/**
	 * <p>异常构造方法
	 * @param code 异常码，可通过{@link #code()}获取
	 * @param e 其它异常
	 */
	public RollbackException(int code, Throwable e) {
		super(code, e);
	}

	/**
	 * <p>异常构造方法
	 * @param e 其它异常
	 */
	public RollbackException(Throwable e) {
		super(e);
	}
	
	/**
	 * 获取异常码，可用于判断错误类型
	 * @return 异常码
	 */
	public int code() {
		return super.code();
	}

}
