package pt.cc.hmi.controller.heat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import pt.cc.hmi.viewmodel.heat.UpdateHeatViewModel;

@ComponentScan(basePackages = "pt.cc")
@Component("UpdateHeatController")
public class UpdateHatController implements Initializable {
	@FXML
	private BorderPane updateHeatDialog;
	@FXML
	private TextField heatNameProperyModel;
	@FXML
	private ComboBox<String> planNameListModel;
	@FXML
	private TextField netWeightPropertyModel;
	@FXML
	private ComboBox<String> gradeListModel;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@Autowired
	private ViewModelFactory viewModelFactory;	
	@Autowired
	private ViewModelServiceProvider serviceProvider;
	@Autowired
	private OverviewController overview;

	private UpdateHeatViewModel updateHeatViewModel;

	ObservableList<String> gradeList = FXCollections.observableArrayList("Q235B", "K1890", "J1234");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Heat selectedHeat = overview.getHeatOverview().getSelectionModel().getSelectedItem();
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(planNameListModel);
		controlList.add(gradeListModel);
		serviceProvider.setControlList(controlList);
		viewModelFactory.setViewModelClass(UpdateHeatViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);
		viewModelFactory.setObject(selectedHeat);
		updateHeatViewModel = (UpdateHeatViewModel) viewModelFactory.createViewModel(updateHeatDialog);

		updateHeatViewModel.bindHeatName(heatNameProperyModel);	
		updateHeatViewModel.bindNetWeight(netWeightPropertyModel);
	
		// todo use method to set this value

		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						updateHeatViewModel.updateHeat(selectedHeat);
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
						updateHeatViewModel.close();
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
