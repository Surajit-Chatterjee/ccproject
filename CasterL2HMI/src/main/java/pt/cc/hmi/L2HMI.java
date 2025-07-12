package pt.cc.hmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.cc.adapters.ICasterProperties;
import pt.cc.hmi.config.BeanMapper;
import pt.cc.hmi.config.IViewLoader;
import pt.cc.hmi.controller.main.MainViewController;

@SpringBootApplication
@ComponentScan(basePackages = "pt.cc")
public class L2HMI extends Application {

	public static Stage mainStage;
	private BeanMapper beanMapper;
	private IViewLoader viewLoader;
	private ICasterProperties casterProperties;
	public static ApplicationContext context = SpringApplication.run(L2HMI.class);

	public static void main(final String[] args) {
		launch();
	}

	static {

	}

	/*
	 * public static ApplicationContext getContext() { return context; }
	 */

	@Override
	public void init() throws Exception {
		beanMapper = context.getBean(BeanMapper.class);
		viewLoader = context.getBean(IViewLoader.class);
		casterProperties = context.getBean(ICasterProperties.class);

		// beanMapper.setBeanMap(ViewLoader.class.getSimpleName(), viewLoader);
		// beanMapper.setBeanMap(ICasterProperties.class.getSimpleName(),
		// casterProperties);

		beanMapper.setBeanMap(ApplicationContext.class.getSimpleName(), context);
	}

	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		// ApplicationContext context = (ApplicationContext)
		// beanMapper.getBean(ApplicationContext.class.getSimpleName());
		FXMLLoader fxmlLoader = viewLoader.loadView(MainViewController.class);
		if (fxmlLoader != null) {
			// fxmlLoader.setControllerFactory(context::getBean); check if error
			Scene mainScene = new Scene(fxmlLoader.load());
			mainScene.getStylesheets().add(casterProperties.getCssFileName());
			stage.setScene(mainScene);
			stage.show();
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		System.out.println("Shutting down");
	}

}
