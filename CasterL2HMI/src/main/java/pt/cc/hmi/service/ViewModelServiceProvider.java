package pt.cc.hmi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TabPane;
import pt.cc.adapters.ICasterProperties;
import pt.cc.heat.HeatPool;
import pt.cc.plan.PlanPool;

@Service("serviceProvider")
public class ViewModelServiceProvider {

	@Autowired
	private HeatPool heatPool;
	@Autowired
	private PlanPool planPool;
	@Autowired
	private ICasterProperties casterProperties;
	private ComboBox<?> combo = null;
	private TabPane finNonFinTab;

	private List<Control> controlList;

	public HeatPool getHeatPool() {
		return heatPool;
	}

	public PlanPool getPlanPool() {
		return planPool;
	}

	public ICasterProperties getCasterProperties() {
		return casterProperties;
	}

	public void setCasterProperties(ICasterProperties casterProperties) {
		this.casterProperties = casterProperties;
	}

	public List<Control> getControlList() {
		return controlList;
	}

	public void setControlList(List<Control> controlList) {
		this.controlList = controlList;
	}
	
	public TabPane getTabPane(String id) {		
		controlList.forEach(control -> {
			if (control instanceof TabPane) {				
				if (control.getId().equalsIgnoreCase(id)) {
					finNonFinTab =  (TabPane) control;
				}
			}
		});	
		return finNonFinTab;
	}

	public ComboBox<?> getComboBox(String id) {		
		controlList.forEach(control -> {
			if (control instanceof ComboBox) {				
				if (control.getId().equalsIgnoreCase(id)) {
					combo =  (ComboBox<?>) control;
				}
			}
		});
		return combo;
		
	}

}
