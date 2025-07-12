package pt.cc.heat;

import org.springframework.context.ApplicationEvent;

public class HeatPoolEvent extends ApplicationEvent {
	private HeatPoolEventState state;
	private Heat heat;

	public HeatPoolEvent(Object source, HeatPoolEventState state, Heat heat) {
		super(source);
		this.state = state;
		this.heat = heat;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeatPoolEventState getState() {
		return state;
	}

	public Heat getHeat() {
		return heat;
	}

}
