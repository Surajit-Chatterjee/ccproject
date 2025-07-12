/**
 * 
 */
package pt.cc.hmi.viewmodel.plan;

import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import pt.cc.hmi.config.ConfigUtil;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;
import pt.cc.plan.Plan;

/**
 * 
 */
public class CopyPlanViewModel extends ViewModel {

	/**
	 * @param parentView
	 * @param object
	 * @param serviceProvider
	 */
	public CopyPlanViewModel(Parent parentView, Object object, ViewModelServiceProvider serviceProvider) {
		super(parentView, object, serviceProvider);
	}

	public void copyPlan() {

		if (newPlanNameProperty.isEmpty().get()) {
			validate();
			return;
		}
		String newPlanName = newPlanNameProperty.get();

		Plan selectedPlan = (Plan) getObject();
		System.out.println("copy plan called selectedPlan " + (Plan) getObject());

		System.out.println("newPlanName " + newPlanName);

		serviceProvider.getPlanPool().copyPlan(selectedPlan, newPlanName);
		// here we will open the create plan dialog window

		unbind(planNameProperty);
		unbind(newPlanNameProperty);
		close();
	}

	// move it to somewhere
	private void validate() {
		ConfigUtil.invokeErrorView(serviceProvider.getCasterProperties(), "Plan Name is empty");
	}

	private void unbind(StringProperty property) {
		property.unbind();
		property.setValue(null);
	}
}
