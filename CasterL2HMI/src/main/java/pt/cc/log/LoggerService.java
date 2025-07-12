package pt.cc.log;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoggerService implements ILogger {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void logInfo(Class<?> clazz, String message) {
		// Logger logger = LoggerFactory.getLogger(clazz);
		StackTraceElement caller = Thread.currentThread().getStackTrace()[2];

		log.info("[Class: {}] [Method: {}] [Line: {}] - {}", caller.getClassName(), caller.getMethodName(),
				Integer.valueOf(caller.getLineNumber()), message);
	}

	public void logError(Class<?> clazz, String message, Throwable e) {
		// Logger logger = LoggerFactory.getLogger(clazz);
		StackTraceElement caller = Thread.currentThread().getStackTrace()[2];

		log.error("[Class: {}] [Method: {}] [Line: {}] - {}", caller.getClassName(), caller.getMethodName(),
				Integer.valueOf(caller.getLineNumber()), message, e);
	}

	@Override
	public void logInfo(String message) {
		// Logger logger = LoggerFactory.getLogger(clazz);
		StackTraceElement caller = Thread.currentThread().getStackTrace()[2];

		log.info("[Class: {}] [Method: {}] [Line: {}] - {}", caller.getClassName(), caller.getMethodName(),
				Integer.valueOf(caller.getLineNumber()), message);
	}

	@Override
	public void logError(String message) {
		// Logger logger = LoggerFactory.getLogger(clazz);
		StackTraceElement caller = Thread.currentThread().getStackTrace()[1];
		log.error("[Class: {}] [Method: {}] [Line: {}] - {}", caller.getClassName(), caller.getMethodName(),
				Integer.valueOf(caller.getLineNumber()), message);
	}

	@Override
	public void logError(String message, Throwable e) {
		// Logger logger = LoggerFactory.getLogger(clazz);
		StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
		log.error("[Class: {}] [Method: {}] [Line: {}] - {}", caller.getClassName(), caller.getMethodName(),
				Integer.valueOf(caller.getLineNumber()), message, e);
	}

}
