package pt.cc.hmi.controller.plan;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import pt.cc.hmi.viewmodel.plan.ModifyPlanProductViewModel;

@ComponentScan(basePackages = "pt.cc")
@Component("modifyPlanProductController")
public class ModifyPlanProductController extends CreatePlanProductController {
	@FXML
	protected Button ok;
	@FXML
	protected Button cancel;

	private ModifyPlanProductViewModel planProductViewModel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(thickness);
		controlList.add(strandNr);
		serviceProvider.setControlList(controlList);
		viewModelFactory.setViewModelClass(ModifyPlanProductViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);

		viewModelFactory.setObject(planController.getSelectedPlanProduct());

		planProductViewModel = (ModifyPlanProductViewModel) viewModelFactory.createViewModel(createProductWindow);
		planProductViewModel.bindAimLength(aimLength);
		planProductViewModel.bindMinLength(minLength);
		planProductViewModel.bindMaxLength(maxLength);
		planProductViewModel.bindWidth(width);

		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						planProductViewModel.modify();
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
						planProductViewModel.close();
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
