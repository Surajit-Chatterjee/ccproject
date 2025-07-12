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
import javafx.scene.control.Control;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import pt.cc.heat.Heat;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModelFactory;
import pt.cc.hmi.viewmodel.heat.DeleteHeatViewModel;

@ComponentScan(basePackages = "pt.cc")
@Component("DeleteHeatController")
public class DeleteHeatController implements Initializable {
	
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@FXML
	private TableView<Heat> nonFinishedTable;
	@FXML
	private TabPane FinNonFinTabPane;	
	@FXML
	private BorderPane DeleteHeatDialog;
	@Autowired
	private ViewModelFactory viewModelFactory;
	@Autowired
	private ViewModelServiceProvider serviceProvider;
	private DeleteHeatViewModel deleteHeatViewModel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(FinNonFinTabPane);

		serviceProvider.setControlList(controlList);
		viewModelFactory.setViewModelClass(DeleteHeatViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);
		deleteHeatViewModel = (DeleteHeatViewModel) viewModelFactory.createViewModel(DeleteHeatDialog);

		
		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						deleteHeatViewModel.deleteHeat();
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
						deleteHeatViewModel.close();
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
