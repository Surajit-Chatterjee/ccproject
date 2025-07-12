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
import pt.cc.hmi.viewmodel.plan.NewPlanViewModel;

/**
 * 
 */

@ComponentScan(basePackages = "pt.cc")
@Component("CreatePlanController")
public class CreatePlanController implements Initializable {

	@FXML
	private TextField planName;
	@FXML
	private BorderPane creatPlanWindow;
	@FXML
	private Button ok;

	@Autowired
	private ViewModelFactory viewModelFactory;
	@Autowired
	private ViewModelServiceProvider serviceProvider;

	private NewPlanViewModel newPlanViewModel;

	@Override
	public void initialize(URL url, ResourceBundle resource) {
		viewModelFactory.setViewModelClass(NewPlanViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);
		newPlanViewModel = (NewPlanViewModel) viewModelFactory.createViewModel(creatPlanWindow);
		newPlanViewModel.bindPlanName(planName);

		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						newPlanViewModel.createPlan();
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
