package pt.cc.hmi.viewmodel;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import pt.cc.heat.Heat;
import pt.cc.hmi.service.ViewModelServiceProvider;

public class ViewModel implements IViewModel {
	protected ObservableList<String> grades = FXCollections.observableArrayList("Q235B", "K1890", "J1234");
	protected ObservableList<Double> thicknessList = FXCollections.observableArrayList(Double.valueOf(200), Double.valueOf(230));
	protected StringProperty planNameProperty = new SimpleStringProperty();
	protected StringProperty newPlanNameProperty = new SimpleStringProperty();
	protected StringProperty heatNameProperty = new SimpleStringProperty();
	protected DoubleProperty netWeightProperty = new SimpleDoubleProperty();
	protected DoubleProperty aimLengthProperty = new SimpleDoubleProperty();
	protected DoubleProperty minLengthProperty = new SimpleDoubleProperty();
	protected DoubleProperty maxLengthProperty = new SimpleDoubleProperty();
	protected DoubleProperty widthProperty = new SimpleDoubleProperty();

	public Parent parentView;
	protected ViewModelServiceProvider serviceProvider;
	protected Object object;

	protected ObservableList<Heat> getHeats(List<Heat> heats) {
		return FXCollections.<Heat>observableArrayList(heats);
	}

	public ViewModel(Parent parentView) {
		this.parentView = parentView;
	}

	public ViewModel(Parent parentView, ViewModelServiceProvider serviceProvider) {
		this(parentView);
		this.serviceProvider = serviceProvider;
	}

	public ViewModel(Parent parentView, Object object, ViewModelServiceProvider serviceProvider) {
		this(parentView);
		this.object = object;
		this.serviceProvider = serviceProvider;
	}

	public void close() {
		Stage stage = (Stage) parentView.getScene().getWindow();
		stage.close();
	}

	public String getPlanName() {
		return planNameProperty.get();
	}

	public StringProperty planNameProperty() {
		return planNameProperty;
	}

	public String getNewPlanName() {
		return newPlanNameProperty.get();
	}

	public StringProperty newPlanNameProperty() {
		return newPlanNameProperty;
	}

	public String getHeatName() {
		return heatNameProperty.get();
	}

	public StringProperty heatNameProperty() {
		return heatNameProperty;
	}

	public double getAimLength() {
		return aimLengthProperty.get();
	}

	public DoubleProperty aimLengthProperty() {
		return aimLengthProperty;
	}

	public double getMinLength() {
		return minLengthProperty.get();
	}

	public DoubleProperty minLengthProperty() {
		return minLengthProperty;
	}

	public double getMaxLength() {
		return maxLengthProperty.get();
	}

	public DoubleProperty maxLengthProperty() {
		return maxLengthProperty;
	}

	public double getWidth() {
		return widthProperty.get();
	}

	public DoubleProperty widthProperty() {
		return widthProperty;
	}

	public void bindPlanName(TextField text) {
		text.textProperty().bindBidirectional(planNameProperty());
	}

	public void bindNewPlanName(TextField text) {
		text.textProperty().bindBidirectional(newPlanNameProperty());
	}

	public void bindHeatName(TextField text) {
		text.textProperty().bindBidirectional(heatNameProperty());
	}

	public double getNetWeight() {
		return netWeightProperty.get();
	}

	public DoubleProperty netWeightProperrty() {
		return netWeightProperty;
	}

	public void bindNetWeight(TextField text) {
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(text.textProperty(), netWeightProperty, (StringConverter<Number>) converter);
	}

	public void bindAimLength(TextField text) {
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(text.textProperty(), aimLengthProperty, (StringConverter<Number>) converter);
	}

	public void bindMinLength(TextField text) {
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(text.textProperty(), minLengthProperty, (StringConverter<Number>) converter);
	}

	public void bindMaxLength(TextField text) {
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(text.textProperty(), maxLengthProperty, (StringConverter<Number>) converter);
	}

	public void bindWidth(TextField text) {
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(text.textProperty(), widthProperty, (StringConverter<Number>) converter);
	}

	public void unbind() {

	}

	@Override
	public Parent getParent() {
		return parentView;
	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public ViewModelServiceProvider getServiceProvider() {
		return serviceProvider;
	}

}
