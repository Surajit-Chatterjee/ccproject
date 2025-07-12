/**
 * 
 */
package pt.cc.hmi.controller.plan;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModelFactory;
import pt.cc.hmi.viewmodel.plan.CopyPlanViewModel;
import pt.cc.plan.Plan;

/**
 * 
 */
@ComponentScan(basePackages = "pt.cc")
@Component("CopyPlanController")
public class CopyPlanController implements Initializable {

	@FXML
	private TextField planName;
	@FXML
	private TextField newPlanName;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@FXML
	private BorderPane copyPlanWindow;
	@Autowired
	private PlanViewController planController;
	@Autowired
	private ViewModelFactory viewModelFactory;
	@Autowired
	private ViewModelServiceProvider serviceProvider;

	private CopyPlanViewModel copyPlanViewModel;
	private Plan selectedPlan = null;

	@Override
	public void initialize(URL url, ResourceBundle resource) {
		selectedPlan = planController.getPlanTable().getSelectionModel().getSelectedItem();
		viewModelFactory.setViewModelClass(CopyPlanViewModel.class);
		viewModelFactory.setObject(selectedPlan);
		viewModelFactory.setServiceProvider(serviceProvider);
		copyPlanViewModel = (CopyPlanViewModel) viewModelFactory.createViewModel(copyPlanWindow);
		copyPlanViewModel.bindPlanName(planName);
		copyPlanViewModel.bindNewPlanName(newPlanName);
		
		planName.setText(selectedPlan.getPlanName());
		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						copyPlanViewModel.copyPlan();
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
						copyPlanViewModel.close();
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
