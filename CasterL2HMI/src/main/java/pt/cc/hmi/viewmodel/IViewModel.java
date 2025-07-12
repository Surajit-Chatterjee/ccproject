package pt.cc.hmi.viewmodel;

import javafx.scene.Parent;
import pt.cc.hmi.service.ViewModelServiceProvider;

public interface IViewModel {

	public Parent getParent();

	public ViewModelServiceProvider getServiceProvider();

	public Object getObject();;

}
