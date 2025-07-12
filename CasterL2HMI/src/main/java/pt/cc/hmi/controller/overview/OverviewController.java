package pt.cc.hmi.controller.overview;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import pt.cc.adapters.ICasterProperties;
import pt.cc.heat.Heat;
import pt.cc.heat.HeatPool;
import pt.cc.heat.HeatPoolEvent;
import pt.cc.heat.HeatPoolEventState;
import pt.cc.hmi.L2HMI;
import pt.cc.hmi.config.ConfigUtil;
import pt.cc.hmi.config.IViewLoader;
import pt.cc.hmi.controller.heat.CreateHeatController;
import pt.cc.hmi.controller.heat.DeleteHeatController;
import pt.cc.hmi.controller.heat.UpdateHatController;
import pt.cc.strand.StrandInfo;

@ComponentScan(basePackages = "pt.cc")
@Component("overview")
public class OverviewController implements Initializable {
	@Autowired
	private IViewLoader viewLoader;
	@Autowired
	private ICasterProperties casterProperties;
	private static BorderPane mainLayout;
	@FXML
	private MenuItem createHeatMenuItem;
	@FXML
	private MenuItem deleteHeatMenuItem;
	@FXML
	private MenuItem heatPlanAssignmentMenuItem;
	@FXML
	private MenuItem updateHeatMenuItem;

	@FXML
	private Button planButton;
	@FXML
	private Button overviewButton;

	@FXML
	private TableView<Heat> heatOverview;
	@FXML
	private TableColumn<Heat, String> planName;

	@FXML
	private BorderPane strandInfoBorderPane;
	@Autowired
	private HeatPool heatPool;
	// TableView of strandInfo
	@FXML
	private TableView<StrandInfo> strandInfoView;
	@FXML
	private TableColumn<StrandInfo, Integer> ColStrandNr;

	// private ObservableList<StrandInfo> strandInfoList =
	// FXCollections.emptyObservableList();

	private List<StrandInfo> strandList = new ArrayList<StrandInfo>();

	private ObservableList<Heat> heats = FXCollections.emptyObservableList();
	private int nrOfStrand = 1;

	private ObservableList<Heat> getHeats(List<Heat> heats) {
		return FXCollections.<Heat>observableArrayList(heats);
	}

	private ObservableList<StrandInfo> getStrands(List<StrandInfo> strands) {
		return FXCollections.<StrandInfo>observableArrayList(strands);
	}

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		try {
			refreshHeatTableView();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			nrOfStrand = casterProperties.getNrOfStrand();
			double rowHeight = 34.0;// each row height
			System.out.println("table height " + strandInfoView.getHeight());
			for (int index = 0; index < nrOfStrand; index++) {
				StrandInfo strandInfo = new StrandInfo(index + 1, index + 1);
				// strandInfoView.getItems().add(index, strandInfo);
				// strandInfoList.add(strandInfo);
				strandList.add(strandInfo);

			}

			System.out.println("roeHeight " + rowHeight * nrOfStrand);
			strandInfoBorderPane.setPrefHeight(rowHeight * nrOfStrand);
			ColStrandNr.setCellValueFactory(new PropertyValueFactory<StrandInfo, Integer>("strandNr"));
			strandInfoView.setItems(getStrands(strandList));

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Add Event Handler to menuItems
		createHeatMenuItem.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						loadCreateHeatDialog();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();
			System.out.println("Status: Task Started");

		});
		deleteHeatMenuItem.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						loadDeleteHeatDialog();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();
			System.out.println("Status: Task Started");

		});
		updateHeatMenuItem.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						loadUpdateHeatDialog();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();
			System.out.println("Status: Task Started");

		});
		heatPlanAssignmentMenuItem.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						loadExchangeHeatPlanDialog();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();
			System.out.println("Status: Task Started");

		});
	}

	@FXML
	public void openPlanView(ActionEvent e) {
		// logger.logInfo("Plan Button Clicked");
		FXMLLoader fxmlLoader = new FXMLLoader(L2HMI.class.getResource("plan/planview.fxml"));
		try {
			BorderPane planView = fxmlLoader.load();
			mainLayout.setCenter(planView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@EventListener
	public void heatPoolEventPerformed(HeatPoolEvent event) {
		if (event.getState() == HeatPoolEventState.HEAT_ADDED || event.getState() == HeatPoolEventState.HEAT_CHANGED
				|| event.getState() == HeatPoolEventState.HEAT_REMOVED) {
			refreshHeatTableView();
		}
	}

	private void refreshHeatTableView() {
		heats = getHeats(heatPool.getHeats());
		StringBuilder heatBuilder = new StringBuilder("Available Heat in pool   \n");
		for (Heat heat : heats) {
			heatBuilder.append(heat.getHeatName()).append(" -- ").append(heat.getPlanName()).append("\n");
		}
		List<TableColumn<Heat, ?>> columns = heatOverview.getColumns();
		columns.forEach(col -> {
			String id = col.getId();
			col.setCellValueFactory(new PropertyValueFactory<>(id));

		});

		heatOverview.setItems(heats);

		planName.setCellFactory((tableColumn) -> {
			TableCell<Heat, String> tableCell = new TableCell<>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (!empty) {
						int currentIndex = indexProperty().getValue().intValue() < 0 ? 0
								: indexProperty().getValue().intValue();

						String name = tableColumn.getCellData(currentIndex);
						// logger.logInfo("Plan Name " + name);
						setText(name);

						if (name == null) {
							setBackground(Background.fill(Color.RED));
						}

					}
				}
			};

			return tableCell;
		});

	}

	// @FXML
	public void loadCreateHeatDialog() {
		try {
			// create new Stage to show the dialog
			FXMLLoader fxmlLoader = viewLoader.loadView(CreateHeatController.class);
			BorderPane createNewHeatView = fxmlLoader.load();
			ConfigUtil.invokeView(createNewHeatView, "Create Heat..", casterProperties);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// @FXML
	public void loadDeleteHeatDialog() {

		try {
			// create new Stage to show the dialog

			FXMLLoader fxmlLoader = viewLoader.loadView(DeleteHeatController.class);
			BorderPane deleteView = fxmlLoader.load();
			ConfigUtil.invokeView(deleteView, "Delete Heat..", casterProperties);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// @FXML
	public void loadUpdateHeatDialog() {
		int indexTodelete = heatOverview.getSelectionModel().getSelectedIndex();
		if (indexTodelete < 0) {
			ConfigUtil.invokeErrorView(casterProperties, "Select Heat To Update");
			return;
		}
		try {
			// update new Stage to show the dialog
			FXMLLoader fxmlLoader = viewLoader.loadView(UpdateHatController.class);
			BorderPane updateHeatView = fxmlLoader.load();
			ConfigUtil.invokeView(updateHeatView, "Update Heat..", casterProperties);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// @FXML
	public void loadExchangeHeatPlanDialog() {

		if (heatOverview.getSelectionModel().getSelectedItem() == null) {
			validate();
			return;
		}
		try {
			// update new Stage to show the dialog
			FXMLLoader fxmlLoader = viewLoader.loadView(UpdateHatController.class);
			BorderPane exchangeHeatPlanView = fxmlLoader.load();
			ConfigUtil.invokeView(exchangeHeatPlanView, "Exchange Heat - Plan..", casterProperties);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void validate() {
		Alert errorAlert = new Alert(AlertType.ERROR, "");
		DialogPane dialogPane = errorAlert.getDialogPane();
		dialogPane.getStylesheets().add(new File(casterProperties.getCssFileName()).getName());

		errorAlert.initModality(Modality.APPLICATION_MODAL);
		errorAlert.getDialogPane().setContentText("Please Select Heat..");
		errorAlert.getDialogPane().setHeaderText("Error..");
		errorAlert.showAndWait();
	}

	private HeatPool getHeatPool() {
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			if (registry != null) {
				// logger.logInfo("Successfully Got The registry");
				HeatPool heatPool = (HeatPool) registry.lookup("heatPool");
				if (heatPool != null) {
					// logger.logInfo("Successfully Got HeatPool " + heatPool.getHeats());
					return heatPool;
				} else {
					// logger.logInfo("Could nore get HeatPool from Registry");
				}

			} else {
				// logger.logInfo("Could not getRegistry");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TableView<Heat> getHeatOverview() {
		return heatOverview;
	}

	public void setHeatOverview(TableView<Heat> heatOverview) {
		this.heatOverview = heatOverview;
	}

}
