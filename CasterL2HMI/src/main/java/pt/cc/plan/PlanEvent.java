package pt.cc.plan;

import org.springframework.context.ApplicationEvent;

public class PlanEvent extends ApplicationEvent {
	protected PlanPoolEventState state;
	protected Plan plan;

	public PlanEvent(Object source, PlanPoolEventState state, Plan plan) {
		super(source);
		this.state = state;
		this.plan = plan;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanPoolEventState getState() {
		return state;
	}

	public Plan getPlan() {
		return plan;
	}

}
