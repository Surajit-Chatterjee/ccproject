package pt.cc.hmi.viewmodel.heat;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import pt.cc.heat.Heat;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;
import pt.cc.plan.Plan;

public class NewHeatViewModel extends ViewModel {
	
	private ComboBox<String> planList, gradeList;

	@SuppressWarnings("unchecked")
	public NewHeatViewModel(Parent parentView, ViewModelServiceProvider serviceProvider) {
		super(parentView);		
		planList = (ComboBox<String>) serviceProvider.getComboBox("planNameListModel");
		gradeList = (ComboBox<String>) serviceProvider.getComboBox("gradeListModel");
		gradeList.setItems(grades);
		fillPlanList();
		System.out.println("NewHeatViewModel called from Cosntructor");
	}

	public void createNewHeat() {
		Heat heat = serviceProvider.getHeatPool().createNewHeat();
		heat.setHeatName(getHeatName());
		heat.setSteelGrade(gradeList.getSelectionModel().getSelectedItem());
		String selectedPlan = (String) planList.getSelectionModel().getSelectedItem();
		Plan plan = serviceProvider.getPlanPool().findPlan(selectedPlan);
		if (plan != null) {
			heat.setPlanId(Integer.valueOf(plan.getPlanId()));
			heat.setPlanName(plan.getPlanName());
		}
		heat.setNetWeight(getNetWeight());

		serviceProvider.getHeatPool().addNewHeat(heat);
		//logger.logInfo("Heat Created");
		close();

	}

	private List<Plan> findAvailablePlan() {
		List<Plan> availablePlans = new ArrayList<Plan>();
		serviceProvider.getPlanPool().getAllPlan().forEach(plan -> {
			List<Heat> assignedHeat = serviceProvider.getHeatPool().findHeat(Integer.valueOf(plan.getPlanId()));
			if (assignedHeat.isEmpty()) {
				availablePlans.add(plan);
			}

		});
		// logger.logInfo("availablePlans " + availablePlans);

		if (serviceProvider.getCasterProperties().isHeatBasedPlanning())
			return availablePlans;
		return serviceProvider.getPlanPool().getAllPlan();
	}

	private void fillPlanList() {
		findAvailablePlan().forEach(plan -> {
			planList.getItems().add(plan.getPlanName());
		});
	}

}
