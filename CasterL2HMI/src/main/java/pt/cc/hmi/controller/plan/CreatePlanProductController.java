package pt.cc.hmi.controller.plan;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModelFactory;
import pt.cc.hmi.viewmodel.plan.CreatePlanProductViewModel;

@ComponentScan(basePackages = "pt.cc")
@Component("planProductController")
public class CreatePlanProductController implements Initializable {

	@FXML
	protected BorderPane createProductWindow;
	@FXML
	protected TextField aimLength;
	@FXML
	protected TextField minLength;
	@FXML
	protected TextField maxLength;
	@FXML
	protected TextField width;
	@FXML
	protected ComboBox<Double> thickness;
	@FXML
	protected ComboBox<Integer> strandNr;
	@FXML
	protected Button ok;
	@FXML
	protected Button cancel;

	@Autowired
	protected ViewModelFactory viewModelFactory;
	@Autowired
	protected ViewModelServiceProvider serviceProvider;
	@Autowired
	protected PlanViewController planController;
	private CreatePlanProductViewModel planProductViewModel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObjectProperty<Object> buttonObject = planController.selectedProperty();
		if (buttonObject.get() == planController.getADD_PRODUCT()) {
			System.out.println("Create Button Clicked");
		}

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(thickness);
		controlList.add(strandNr);
		serviceProvider.setControlList(controlList);
		viewModelFactory.setViewModelClass(CreatePlanProductViewModel.class);
		viewModelFactory.setServiceProvider(serviceProvider);
		viewModelFactory.setObject(planController.getPlanTable().getSelectionModel().getSelectedItem());
		planProductViewModel = (CreatePlanProductViewModel) viewModelFactory.createViewModel(createProductWindow);
		planProductViewModel.bindAimLength(aimLength);
		planProductViewModel.bindMinLength(minLength);
		planProductViewModel.bindMaxLength(maxLength);
		planProductViewModel.bindWidth(width);

		if (buttonObject.get() == planController.getMODIFY_PRODUCT()) {
			System.out.println("Modify Button Clicked");
			//setSelectedValue();

		}

		ok.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						planProductViewModel.createProduct();
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
