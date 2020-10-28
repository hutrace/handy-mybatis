package org.hutrace.handy.mybatis.log;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

class Slf4jLocationAwareLoggerImpl implements Log {

	private static Marker MARKER = MarkerFactory.getMarker(LogFactory.MARKER);

	private static final String FQCN = Slf4jImpl.class.getName();

	private LocationAwareLogger logger;

	Slf4jLocationAwareLoggerImpl(LocationAwareLogger logger) {
		this.logger = logger;
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void error(String s, Throwable e) {
		logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, e);
	}

	public void error(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, null);
	}

	public void debug(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.DEBUG_INT, s, null, null);
	}

	public void trace(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.TRACE_INT, s, null, null);
	}

	public void warn(String s) {
		logger.log(MARKER, FQCN, LocationAwareLogger.WARN_INT, s, null, null);
	}

}