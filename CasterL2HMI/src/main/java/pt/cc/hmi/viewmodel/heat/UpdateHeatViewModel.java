package pt.cc.hmi.viewmodel.heat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import pt.cc.heat.Heat;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;
import pt.cc.plan.Plan;

public class UpdateHeatViewModel extends ViewModel {

	private ViewModelServiceProvider serviceProvider;
	private ComboBox<String> planList, gradeList;
	//private Heat selectedHeat;
	private AtomicReference<Heat> selectedHeat = new AtomicReference<Heat>();

	public UpdateHeatViewModel(Parent parentView, Object object) {
		super(parentView);
	}

	public UpdateHeatViewModel(Parent parentView, ViewModelServiceProvider serviceProvider) {
		super(parentView);
		this.serviceProvider = serviceProvider;
	}

	@SuppressWarnings("unchecked")
	public UpdateHeatViewModel(Parent parentView, Object object, ViewModelServiceProvider serviceProvider) {
		this(parentView, object);
		this.serviceProvider = serviceProvider;
		if (object instanceof Heat) {
			selectedHeat.set((Heat) object);		
			planList = (ComboBox<String>) serviceProvider.getComboBox("planNameListModel");
			fillPlanList();
			gradeList = (ComboBox<String>) serviceProvider.getComboBox("gradeListModel");
			gradeList.setItems(grades);
			setSelectedHeatProperty();
		}

	}

	private void setSelectedHeatProperty() {
		if (selectedHeat.get() != null) {			
			heatNameProperty.set(selectedHeat.get().getHeatName());			
			planList.setValue(selectedHeat.get().getPlanName());		
			gradeList.setValue(selectedHeat.get().getSteelGrade());
			netWeightProperty.set(selectedHeat.get().getNetWeight());
			heatNameProperty.addListener((observable, oldValue, newValue) -> {
				heatNameProperty.set(newValue);
			});
			netWeightProperty.addListener((observable, oldValue, newValue) -> {
				netWeightProperty.setValue(newValue);
			});

		}
	}

	public void updateHeat(Heat selectedHeat) {
		// TODO validation of fields		
		Heat heat = serviceProvider.getHeatPool().findForUpdate(selectedHeat.getHeatId());
		if (heat != null) {
			heat.setHeatName(heatNameProperty.get());
			heat.setSteelGrade(gradeList.getSelectionModel().getSelectedItem());
			String selectedPlan = planList.getSelectionModel().getSelectedItem();
			heat.setNetWeight(getNetWeight());
			if (selectedPlan != null) {
				Plan plan = serviceProvider.getPlanPool().findPlan(selectedPlan);
				if (plan != null) {
					heat.setPlanId(Integer.valueOf(plan.getPlanId()));
					heat.setPlanName(plan.getPlanName());
					serviceProvider.getHeatPool().commit(heat,
							heat.getHeatName() + " assigned with plan " + plan.getPlanName());

				}
			}

		}		
		close();
	}

	public void setSelectedHeatHeat(Heat selectedHeat) {
		this.selectedHeat.set(selectedHeat);
	}

	private void fillPlanList() {
		findAvailablePlan().forEach(plan -> {
			planList.getItems().add(plan.getPlanName());
		});
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
}
