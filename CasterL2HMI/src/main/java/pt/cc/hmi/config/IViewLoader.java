package pt.cc.hmi.config;

import javafx.fxml.FXMLLoader;

public interface IViewLoader {

	
	public FXMLLoader loadView(Class<?> controller);
}
