package pt.cc.log;

import java.io.Serializable;

public interface ILogger extends Serializable {

	public void logInfo(String message);

	public void logError(String message);

	public void logError(String message, Throwable e);
}
