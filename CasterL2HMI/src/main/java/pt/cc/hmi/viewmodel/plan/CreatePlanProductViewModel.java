package pt.cc.hmi.viewmodel.plan;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import pt.cc.exceptions.PlanIsNullException;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;
import pt.cc.plan.DefaultPlan;
import pt.cc.plan.DefaultPlanProduct;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanProduct;

public class CreatePlanProductViewModel extends ViewModel {

	protected ComboBox<Double> thickness;
	protected ComboBox<Integer> strandNr;
	protected List<Integer> strandList = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public CreatePlanProductViewModel(Parent parentView, Object object, ViewModelServiceProvider serviceProvider) {
		super(parentView, object, serviceProvider);
		thickness = (ComboBox<Double>) serviceProvider.getComboBox("thickness");
		strandNr = (ComboBox<Integer>) serviceProvider.getComboBox("strandNr");
		setStrandNumber();
	}

	public final void createProduct() {
		Plan plan = (Plan) getObject();
		System.out.println("Plan selected is " + plan.getPlanName());

		DefaultPlan defPlan = (DefaultPlan) serviceProvider.getPlanPool().findPlanForUpdate(plan.getPlanName());
		if (defPlan == null) {
			throw new PlanIsNullException("Plan is Null");
		}
		DefaultPlanProduct newPlanProduct = createPlanProduct();
		newPlanProduct.setPlanId(defPlan.getPlanId());
		defPlan.getPlannedProducts().add(newPlanProduct);
		serviceProvider.getPlanPool().commitPlan(defPlan);
		System.out.println("Plan updated " + defPlan);
		close();

	}

	private DefaultPlanProduct createPlanProduct() {
		DefaultPlanProduct planProduct = (DefaultPlanProduct) serviceProvider.getPlanPool().createPlanProduct();
		planProduct.setName("1");
		planProduct.setAimLength(aimLengthProperty.doubleValue());
		planProduct.setMinLength(minLengthProperty.doubleValue());
		planProduct.setMaxLength(maxLengthProperty.doubleValue());
		planProduct.setWidth(widthProperty.doubleValue());
		planProduct.setThickness(thickness.getSelectionModel().getSelectedItem().doubleValue());
		planProduct.setStrandNumber(strandNr.getSelectionModel().getSelectedItem().intValue());
		return planProduct;
	}

	protected void setStrandNumber() {
		strandList.clear();
		for (int strandNr = 1; strandNr <= serviceProvider.getCasterProperties().getNrOfStrand(); strandNr++) {
			strandList.add(Integer.valueOf(strandNr));
		}
		strandNr.setItems(getStrandList(strandList));
		thickness.setItems(thicknessList);
	}

	protected ObservableList<Integer> getStrandList(List<Integer> strands) {
		return FXCollections.<Integer>observableArrayList(strands);
	}

}
