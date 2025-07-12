/**
 * 
 */
package pt.cc.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * 
 */
@EnableAsync
@Component("Caster")
public class DefaultCasterAdapter extends AbstractInitializer implements Caster {

	@Autowired
	private ICasterProperties casterProperties;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	public int getCasterId() {
		return casterProperties.getCasterId();
	}

	@Override
	public String getCasterName() {
		return casterProperties.getCasterName();
	}

}
