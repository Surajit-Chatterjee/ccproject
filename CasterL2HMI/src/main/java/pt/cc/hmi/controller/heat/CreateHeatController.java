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
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModelFactory;
import pt.cc.hmi.viewmodel.heat.NewHeatViewModel;

@ComponentScan(basePackages = "pt.cc")
@Component("CreateHeatController")
public class CreateHeatController implements Initializable {
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
	@FXML
	private BorderPane createHeatDialog;
	@Autowired
	private ViewModelFactory viewModelFactory;
	@Autowired
	private ViewModelServiceProvider serviceProvider;
	
	private NewHeatViewModel newHeatViewModel;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(planNameListModel);
		controlList.add(gradeListModel);
		serviceProvider.setControlList(controlList);
		viewModelFactory.setViewModelClass(NewHeatViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);
		newHeatViewModel = (NewHeatViewModel) viewModelFactory.createViewModel(createHeatDialog);
		newHeatViewModel.bindHeatName(heatNameProperyModel);		
		newHeatViewModel.bindNetWeight(heatNameProperyModel);
		

		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						newHeatViewModel.createNewHeat();
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
						newHeatViewModel.close();
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
