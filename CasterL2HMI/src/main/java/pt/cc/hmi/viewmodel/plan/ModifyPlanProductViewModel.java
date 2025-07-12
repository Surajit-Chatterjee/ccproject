/**
 * 
 */
package pt.cc.hmi.viewmodel.plan;

import javafx.scene.Parent;
import pt.cc.exceptions.PlanIsNullException;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.plan.DefaultPlan;
import pt.cc.plan.DefaultPlanProduct;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanProduct;

/**
 * 
 */
public class ModifyPlanProductViewModel extends CreatePlanProductViewModel {

	/**
	 * @param parentView
	 * @param object
	 * @param serviceProvider
	 */
	public ModifyPlanProductViewModel(Parent parentView, Object object, ViewModelServiceProvider serviceProvider) {
		super(parentView, object, serviceProvider);
		PlanProduct selectedPlanProduct = (PlanProduct) getObject();
		setSelectedHeatProperty(selectedPlanProduct);
		System.out.println("Modify called selectedPlanProduct " + selectedPlanProduct);
	}

	public void modify() {

		DefaultPlanProduct planProduct = (DefaultPlanProduct) getObject();
		System.out.println("Plan selected is " + planProduct);

		DefaultPlan plan = (DefaultPlan) serviceProvider.getPlanPool()
				.findPlanForUpdate(planProduct.getPlanProductId());
		if (plan == null) {
			throw new PlanIsNullException("Plan is Null");
		}
		PlanProduct newPlanProduct = modifyPlanProduct(planProduct, plan);
		plan.getPlannedProducts().remove(newPlanProduct);
		plan.getPlannedProducts().add(newPlanProduct);
		serviceProvider.getPlanPool().commitPlan(plan);
		System.out.println("Plan updated " + plan);
		close();

		
	}

	public DefaultPlanProduct modifyPlanProduct(DefaultPlanProduct planProduct, Plan plan) {
		//DefaultPlanProduct planProduct = (DefaultPlanProduct) serviceProvider.getPlanPool().createPlanProduct();
		planProduct.setPlanId(plan.getPlanId());
		planProduct.setName("1");
		planProduct.setAimLength(aimLengthProperty.doubleValue());
		planProduct.setMinLength(minLengthProperty.doubleValue());
		planProduct.setMaxLength(maxLengthProperty.doubleValue());
		planProduct.setWidth(widthProperty.doubleValue());
		planProduct.setThickness(thickness.getSelectionModel().getSelectedItem().doubleValue());
		planProduct.setStrandNumber(strandNr.getSelectionModel().getSelectedItem().intValue());
		return planProduct;
	}

	private void setSelectedHeatProperty(PlanProduct selectedPlanProduct) {
		if (selectedPlanProduct != null) {
			aimLengthProperty.set(selectedPlanProduct.getAimLength());
			aimLengthProperty.addListener((observable, oldValue, newValue) -> {
				aimLengthProperty.setValue(newValue);
			});
			minLengthProperty.set(selectedPlanProduct.getMinLength());
			minLengthProperty.addListener((observable, oldValue, newValue) -> {
				minLengthProperty.setValue(newValue);
			});
			maxLengthProperty.set(selectedPlanProduct.getMaxLength());
			maxLengthProperty.addListener((observable, oldValue, newValue) -> {
				maxLengthProperty.setValue(newValue);
			});
			widthProperty.set(selectedPlanProduct.getWidth());
			widthProperty.addListener((observable, oldValue, newValue) -> {
				widthProperty.setValue(newValue);
			});
			strandNr.setValue(Integer.valueOf(selectedPlanProduct.getStrandNumber()));
			thickness.setValue(Double.valueOf(selectedPlanProduct.getThickness()));

		}
	}

}
