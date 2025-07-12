package pt.cc.hmi.viewmodel.heat;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import pt.cc.heat.Heat;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;
import pt.cc.plan.Plan;

public class ExchangeHatPlanViewModel extends ViewModel {
	private ComboBox<Plan> planList = null;

	@SuppressWarnings("unchecked")
	public ExchangeHatPlanViewModel(Parent parentView, Object object, ViewModelServiceProvider serviceProvider) {
		super(parentView, object, serviceProvider);
		planList = (ComboBox<Plan>) serviceProvider.getComboBox("planList");
		planList.setConverter(new StringConverter<Plan>() {
			@Override
			public String toString(Plan plan) {
				return plan.getPlanName();
			}

			@Override
			public Plan fromString(String string) {
				return null;
			}
		});
		fillPlanList();
	}

	public void exchange(Heat selectedHeat) {
		// TODO validation of fields
		if (selectedHeat != null) {
			Plan selectedNewPlan = planList.getSelectionModel().getSelectedItem();
			exchange(selectedHeat, selectedNewPlan);
			close();
		}

	}

	private void fillPlanList() {
		findAvailablePlan().forEach(plan -> {
			planList.getItems().add(plan);
		});
	}

	private void exchange(Heat selectedHeat, Plan selectedNewPlan) {
		serviceProvider.getHeatPool().exchengeHeatPlan(selectedHeat, selectedNewPlan);
	}

	private List<Plan> findAvailablePlan() {
		return serviceProvider.getPlanPool().getAllPlan();
	}

}
