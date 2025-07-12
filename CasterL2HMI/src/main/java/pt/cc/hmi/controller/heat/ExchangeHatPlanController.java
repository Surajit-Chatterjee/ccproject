package pt.cc.hmi.controller.heat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pt.cc.heat.Heat;
import pt.cc.hmi.controller.overview.OverviewController;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModelFactory;
import pt.cc.hmi.viewmodel.heat.ExchangeHatPlanViewModel;
import pt.cc.plan.Plan;

@ComponentScan(basePackages = "pt.cc")
@Component("ExchangeHatPlanController")
public class ExchangeHatPlanController implements Initializable {
	@FXML
	private TextField heatName;
	@FXML
	private ComboBox<Plan> planList;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@FXML
	private BorderPane exchangeHeatPlanWindow;

	@Autowired
	private ViewModelFactory viewModelFactory;
	@Autowired
	private ViewModelServiceProvider serviceProvider;
	@Autowired
	private OverviewController overview;
	private ExchangeHatPlanViewModel exchangeModel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//fillPlanList();		
		Heat selectedHeat = overview.getHeatOverview().getSelectionModel().getSelectedItem();
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(planList);
		serviceProvider.setControlList(controlList);
		viewModelFactory.setViewModelClass(ExchangeHatPlanViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);
		viewModelFactory.setObject(selectedHeat);
		exchangeModel = (ExchangeHatPlanViewModel) viewModelFactory.createViewModel(exchangeHeatPlanWindow);

		exchangeModel.bindHeatName(heatName);

		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						exchangeModel.exchange(selectedHeat);
						// System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			// System.out.println("Status: Task Started");
		});
		cancel.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						exchangeModel.close();
						// System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			// System.out.println("Status: Task Started");
		});

	}

}
