package pt.cc.hmi.viewmodel;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.heat.DeleteHeatViewModel;
import pt.cc.hmi.viewmodel.heat.ExchangeHatPlanViewModel;
import pt.cc.hmi.viewmodel.heat.NewHeatViewModel;
import pt.cc.hmi.viewmodel.heat.UpdateHeatViewModel;
import pt.cc.hmi.viewmodel.plan.CopyPlanViewModel;
import pt.cc.hmi.viewmodel.plan.CreatePlanProductViewModel;
import pt.cc.hmi.viewmodel.plan.ModifyPlanProductViewModel;
import pt.cc.hmi.viewmodel.plan.NewPlanViewModel;

@ComponentScan(basePackages = "pt.cc")
@Component("viewModelFactory")
public class ViewModelFactory {

	private ViewModelServiceProvider serviceProvider;

	private Class<?> clazz;
	private Object object;

	public ViewModelFactory() {
	}

	public void setViewModelClass(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ViewModel createViewModel(Parent parentView) {
		ViewModel viewModel = null;
		if (clazz == NewPlanViewModel.class) {
			viewModel = new NewPlanViewModel(parentView, getServiceProvider());
		} else if (clazz == NewHeatViewModel.class) {
			viewModel = new NewHeatViewModel(parentView, getServiceProvider());
		} else if (clazz == UpdateHeatViewModel.class) {
			viewModel = new UpdateHeatViewModel(parentView, object, getServiceProvider());
		} else if (clazz == DeleteHeatViewModel.class) {
			viewModel = new DeleteHeatViewModel(parentView, getServiceProvider());
		} else if (clazz == ExchangeHatPlanViewModel.class) {
			viewModel = new ExchangeHatPlanViewModel(parentView, object, getServiceProvider());
		} else if (clazz == CopyPlanViewModel.class) {
			viewModel = new CopyPlanViewModel(parentView, object, getServiceProvider());
		} else if (clazz == CreatePlanProductViewModel.class) {
			viewModel = new CreatePlanProductViewModel(parentView,object,getServiceProvider());
		} else if (clazz == ModifyPlanProductViewModel.class) {
			viewModel = new ModifyPlanProductViewModel(parentView,object,getServiceProvider());
		}
		
		return viewModel;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ViewModelServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ViewModelServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

}
