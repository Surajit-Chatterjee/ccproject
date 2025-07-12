package pt.cc.hmi.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.cc.adapters.ICasterProperties;
import pt.cc.hmi.L2HMI;

public class ConfigUtil {

	public static void invokeView(Parent rootPane, String title, ICasterProperties casterProperties) {
		if (rootPane instanceof BorderPane) {
			BorderPane dialog = (BorderPane) rootPane;
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(L2HMI.mainStage);
			stage.setResizable(false);
			Scene scene = new Scene(dialog);
			scene.getStylesheets().add(casterProperties.getCssFileName());
			stage.setScene(scene);
			stage.showAndWait();
		}
	}
	public static void invokeErrorView(ICasterProperties casterProperties,String msg) {
		Alert errorAlert = new Alert(AlertType.ERROR, "");
		DialogPane dialogPane = errorAlert.getDialogPane();
		dialogPane.getStylesheets().add(casterProperties.getCssFileName());
		errorAlert.initModality(Modality.APPLICATION_MODAL);
		errorAlert.getDialogPane().setContentText(msg);
		errorAlert.getDialogPane().setHeaderText("Error..");
		errorAlert.showAndWait();
	}
}
