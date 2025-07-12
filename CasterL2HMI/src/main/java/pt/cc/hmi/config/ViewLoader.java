package pt.cc.hmi.config;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javafx.fxml.FXMLLoader;
import pt.cc.adapters.ICasterProperties;

@ComponentScan(basePackages = "pt.cc")
@Configuration
public class ViewLoader implements IViewLoader {

	@Autowired
	private BeanMapper beanMapper;
	@Autowired
	private ICasterProperties casterProperties;
	private FXMLLoader fxmlLoader;

	@Override
	public FXMLLoader loadView(Class<?> controller) {
		fxmlLoader = new FXMLLoader();
		ApplicationContext context = (ApplicationContext) beanMapper.getBean(ApplicationContext.class.getSimpleName());
		String fxml = controller.getSimpleName() + casterProperties.getFxmlSuffix();		
		URL fxmlUrl = controller.getResource(casterProperties.getControllerViewPath().concat(fxml));
		fxmlLoader.setLocation(fxmlUrl);
		fxmlLoader.setControllerFactory(context::getBean);
		return fxmlLoader;
	}

}
