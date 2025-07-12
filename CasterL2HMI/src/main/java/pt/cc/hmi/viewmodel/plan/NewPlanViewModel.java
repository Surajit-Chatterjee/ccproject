package pt.cc.hmi.viewmodel.plan;

import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import pt.cc.hmi.config.ConfigUtil;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;
import pt.cc.plan.Plan;

public class NewPlanViewModel extends ViewModel {

	private ViewModelServiceProvider serviceProvider;


	public NewPlanViewModel(Parent parentView, ViewModelServiceProvider serviceProvider) {
		super(parentView);
		this.serviceProvider = serviceProvider;
	}

	

	

	// move it to somewhere
	private void validate() {
		ConfigUtil.invokeErrorView(serviceProvider.getCasterProperties(), "Plan Name is empty");
	}

	public void createPlan() {
		String name = getPlanName();
		if (planNameProperty.isEmpty().get()) {
			validate();
			return;
		}

		Plan plan = serviceProvider.getPlanPool().createPlan(name);
		serviceProvider.getPlanPool().addNewPlan(plan);
		unbind(planNameProperty);

		close();
	}

	private void unbind(StringProperty property) {
		property.unbind();
		property.setValue(null);
	}

}