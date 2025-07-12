package pt.cc.hmi.controller.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pt.cc.hmi.config.BeanMapper;
import pt.cc.hmi.config.IViewLoader;
import pt.cc.hmi.config.ViewLoader;
import pt.cc.hmi.controller.overview.OverviewController;
import pt.cc.hmi.controller.plan.PlanViewController;

@ComponentScan(basePackages = "pt.cc")
@Component("MainViewController")
public class MainViewController implements Initializable {
	private IViewLoader viewLoader;
	@FXML
	private BorderPane mainViewLayout;
	@FXML
	private Button overviewButton;
	@FXML
	private Button planButton;
	@FXML
	private Button mainViewButton;
	@FXML
	private AnchorPane anchorPaneLayout;
	@Autowired
	private BeanMapper beanMapper;

	@Override
	public void initialize(URL url, ResourceBundle resource) {
		ApplicationContext context = (ApplicationContext) beanMapper.getBean(ApplicationContext.class.getSimpleName());
		viewLoader = context.getBean(ViewLoader.class);
		planButton.setOnAction(e->{
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						loadPlanView();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		
			
		});
		overviewButton.setOnAction(e->{
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						loadOverView();
						System.out.println("Status: Task Completed ");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		
			
		});
	}

	@FXML
	public void loadMainView() {
		// mainView.loadMainView(mainView.primaryStage);
	}

	//@FXML
	public void loadOverView() {
		FXMLLoader fxmlLoader = viewLoader.loadView(OverviewController.class);
		// fxmlLoader.setControllerFactory(L2HMI2.getSpringContext()::getBean);
		try {
			if (fxmlLoader != null) {
				BorderPane overView = fxmlLoader.load();
				mainViewLayout.setCenter(overView);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	//@FXML
	public void loadPlanView() {
		FXMLLoader fxmlLoader = viewLoader.loadView(PlanViewController.class);
		// fxmlLoader.setControllerFactory(L2HMI2.getSpringContext()::getBean);
		try {
			if (fxmlLoader != null) {
				BorderPane planView = fxmlLoader.load();
				mainViewLayout.setCenter(planView);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
