package pt.cc.adapters;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
import pt.cc.log.ILogger;

public abstract class GenericAbstractAdapter{

	@Autowired
	protected ILogger logger;

	@PostConstruct
	public void init() {
		// TODO something
	}
}
