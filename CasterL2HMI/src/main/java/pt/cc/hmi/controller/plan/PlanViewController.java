/**
 * 
 */
package pt.cc.hmi.controller.plan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import pt.cc.exceptions.PlanIsNullException;
import pt.cc.heat.Heat;
import pt.cc.hmi.config.ConfigUtil;
import pt.cc.hmi.config.IViewLoader;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.plan.DefaultPlan;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanEvent;
import pt.cc.plan.PlanPoolEventState;
import pt.cc.plan.PlanProduct;

/**
 * 
 */
@ComponentScan(basePackages = "pt.cc")
@Component("planController")
public class PlanViewController implements Initializable, ICenterController {

	@FXML
	private TableView<Plan> planTable;
	@FXML
	private TableView<Heat> heatTable;
	@Autowired
	private IViewLoader viewLoader;
	@FXML
	private Button newPlan;
	@FXML
	private Button deletePlan;
	@FXML
	private Button copyPlan;
	@FXML
	private Button lockPlan;
	@FXML
	private Button ADD_PRODUCT;
	@FXML
	private Button MODIFY_PRODUCT;

	// Plan Product Tab Pane
	@FXML
	private TabPane planProductTabPane;
	@FXML
	private BorderPane tabBorderPane;
	private Tab[] planProductTab;
	private TableView<PlanProduct>[] tableInsideTab;
	private Map<Integer, TableView<PlanProduct>> strandTableMap = new ConcurrentHashMap<Integer, TableView<PlanProduct>>();
	private ObjectProperty<Object> selectedProperty = new SimpleObjectProperty();

	@Autowired
	private ViewModelServiceProvider serviceProvider;
	private PlanProduct selectedPlanProduct = null;

	private ObservableList<Plan> plans = FXCollections.emptyObservableList();
	Alert confirmAlert = null;

	@Override
	public void initialize(URL url, ResourceBundle resource) {
		refreshPlanTableView();
		createPlanProductTab();
		getPlanTable().getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Plan>() {
			@Override
			public void onChanged(Change<? extends Plan> change) {
				try {
					if (change.getList().size() > 0) {
						Plan plan = change.getList().get(0);
						System.out.println("Selected Plan " + plan);
						refreshHeatTableView(plan);
						refreshPlanProductTableView((DefaultPlan) plan);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		newPlan.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						newPlan();

						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		});
		copyPlan.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						copyPlan();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		});
		deletePlan.setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						deletePlan();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		});
		getADD_PRODUCT().setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						select(e.getSource());
						newProduct();

						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		});
		getMODIFY_PRODUCT().setOnAction(e -> {
			// Start a background task
			new Thread(() -> {
				try {
					// Update the UI with the result on the JavaFX thread
					Platform.runLater(() -> {
						select(e.getSource());
						modiFyProduct();
						System.out.println("Status: Task Completed");
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

			System.out.println("Status: Task Started");
		});
	}

	@SuppressWarnings("unchecked")
	private void createPlanProductTab() {
		int nrOfStrand = serviceProvider.getCasterProperties().getNrOfStrand();
		planProductTab = new Tab[nrOfStrand];
		tableInsideTab = new TableView[nrOfStrand];
		ObservableList<PlanProduct> planProductList = FXCollections.emptyObservableList();// getPlanProducts(createPlanProduct());
		for (int index = 0; index < nrOfStrand; index++) {
			tableInsideTab[index] = new TableView<PlanProduct>();

			TableColumn<PlanProduct, String> nameCol = new TableColumn<PlanProduct, String>("name");
			nameCol.setId("name");
			tableInsideTab[index].getColumns().add(nameCol);
			nameCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, String>("name"));

			TableColumn<PlanProduct, Double> aimLengthCol = new TableColumn<PlanProduct, Double>("aimLength");
			aimLengthCol.setId("aimLength");
			tableInsideTab[index].getColumns().add(aimLengthCol);
			aimLengthCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, Double>("aimLength"));

			TableColumn<PlanProduct, Double> minLengthCol = new TableColumn<PlanProduct, Double>("minLength");
			minLengthCol.setId("minLength");
			tableInsideTab[index].getColumns().add(minLengthCol);
			minLengthCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, Double>("minLength"));

			TableColumn<PlanProduct, Double> maxLengthCol = new TableColumn<PlanProduct, Double>("maxLength");
			maxLengthCol.setId("maxLength");
			tableInsideTab[index].getColumns().add(maxLengthCol);
			maxLengthCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, Double>("maxLength"));

			TableColumn<PlanProduct, Double> widthCol = new TableColumn<PlanProduct, Double>("width");
			widthCol.setId("width");
			tableInsideTab[index].getColumns().add(widthCol);
			widthCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, Double>("width"));

			TableColumn<PlanProduct, Double> thicknessCol = new TableColumn<PlanProduct, Double>("thickness");
			thicknessCol.setId("thickness");
			tableInsideTab[index].getColumns().add(thicknessCol);
			thicknessCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, Double>("thickness"));

			TableColumn<PlanProduct, Double> weightCol = new TableColumn<PlanProduct, Double>("weight");
			weightCol.setId("weight");
			tableInsideTab[index].getColumns().add(weightCol);
			weightCol.setCellValueFactory(new PropertyValueFactory<PlanProduct, Double>("weight"));

			tableInsideTab[index].setItems(planProductList);
			planProductTab[index] = new Tab("Product Srand " + (index + 1), tableInsideTab[index]);
			planProductTabPane.getTabs().add(planProductTab[index]);

			tableInsideTab[index].getSelectionModel().getSelectedItems()
					.addListener(new ListChangeListener<PlanProduct>() {
						@Override
						public void onChanged(Change<? extends PlanProduct> change) {
							try {
								if (change.getList().size() > 0) {
									PlanProduct product = change.getList().get(0);
									System.out.println("Selected PlanProduct " + product);
									setSelectedPlanProduct(product);
									Plan plan = getPlanTable().getSelectionModel().getSelectedItem();
									if (plan != null) {
										// DefaultPlan defPlan = (DefaultPlan) plan;
										// refreshPlanProductTableView(defPlan,product.getStrandNumber());
									}

								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					});
			// todo
			tableInsideTab[index].prefWidthProperty().bind(tabBorderPane.widthProperty());
			// mainPane.prefWidthProperty().bind(tabBorderPane.widthProperty());
			Integer strandNr = Integer.valueOf(index + 1);
			getStrandTableMap().put(strandNr, tableInsideTab[index]);
		}

	}

	// this is defined in fxml file on action

	// @FXML
	public void newPlan() {
		// here we will open the create plan dialog window
		try {
			// create new Stage to show the dialog

			FXMLLoader fxmlLoader = viewLoader.loadView(CreatePlanController.class);
			BorderPane createNewPlanView = fxmlLoader.load();
			ConfigUtil.invokeView(createNewPlanView, "Create Plan..", serviceProvider.getCasterProperties());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void copyPlan() {
		try {
			// create new Stage to show the dialog
			int index = getPlanTable().getSelectionModel().getSelectedIndex();
			if (index < 0) {

				ConfigUtil.invokeErrorView(serviceProvider.getCasterProperties(), "Plan Name is empty");
				return;
			}

			FXMLLoader fxmlLoader = viewLoader.loadView(CopyPlanController.class);
			BorderPane copyPlanView = fxmlLoader.load();
			ConfigUtil.invokeView(copyPlanView, "Copy Plan..", serviceProvider.getCasterProperties());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void newProduct() {
		try {
			// create new Stage to show the dialog

			int index = getPlanTable().getSelectionModel().getSelectedIndex();
			if (index < 0) {
				ConfigUtil.invokeErrorView(serviceProvider.getCasterProperties(), "Select Plan To Create Product");
				return;
			}
			FXMLLoader fxmlLoader = viewLoader.loadView(CreatePlanProductController.class);
			BorderPane createNewPlanProductView = fxmlLoader.load();
			ConfigUtil.invokeView(createNewPlanProductView, "Create Product..", serviceProvider.getCasterProperties());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void modiFyProduct() {
		try {
			// create new Stage to show the dialog
			FXMLLoader fxmlLoader = viewLoader.loadView(ModifyPlanProductController.class);
			BorderPane createNewPlanProductView = fxmlLoader.load();
			ConfigUtil.invokeView(createNewPlanProductView, "Modify Product..", serviceProvider.getCasterProperties());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private synchronized void deletePlan() {
		int indexTodelete = getPlanTable().getSelectionModel().getSelectedIndex();
		if (indexTodelete >= 0) {
			// we need to delete from plan pool, not from table view Plan plan =
			Plan plan = getPlanTable().getItems().get(indexTodelete);
			confirmAlert = new Alert(AlertType.CONFIRMATION, "Do you want to Delete Plan ? " + plan.getPlanName());
			// planTable.getItems().remove(indexTodelete);
			// deletConfirm(plan);

			DialogPane dialogPane = confirmAlert.getDialogPane();
			dialogPane.getStylesheets().add(new File(serviceProvider.getCasterProperties().getCssFileName()).getName());

			confirmAlert.initModality(Modality.APPLICATION_MODAL);
			confirmAlert.getDialogPane().setHeaderText("Confirm..");
			Optional<ButtonType> result = confirmAlert.showAndWait();

			if (result.isPresent()) {
				if (result.get() == ButtonType.OK) {
					// todo remove heat from planPool
					serviceProvider.getPlanPool().removePlan(plan);
					System.out.println("plan deleted ok");

					/*
					 * Stage stage = (Stage) DeleteHeatDialog.getScene().getWindow(); stage.close();
					 */
				} else if (result.get() == ButtonType.CANCEL) {
					System.out.println("plan not deleted cancel");
				}
			} else {
				// Alert was closed (e.g., by clicking the close button)

			}

			getPlanTable().getSelectionModel().clearSelection();
			refreshHeatTableView(plan);
		} else {
			confirmAlert = new Alert(AlertType.CONFIRMATION, "Please Select Plan..");
			DialogPane dialogPane = confirmAlert.getDialogPane();
			dialogPane.getStylesheets().add(new File(serviceProvider.getCasterProperties().getCssFileName()).getName());
			confirmAlert.initModality(Modality.APPLICATION_MODAL);
			confirmAlert.getDialogPane().setHeaderText("Confirm..");
			confirmAlert.showAndWait();
		}

	}

	@FXML
	public void lockPlan() {
		// TODO
	}

	@EventListener
	public void planPoolEventPerformed(PlanEvent event) {
		if (event.getState() == PlanPoolEventState.NEW_PLAN_ADDED
				|| event.getState() == PlanPoolEventState.PLAN_REMOVED) {
			refreshPlanTableView();
		}
	}

	@EventListener
	public void planProductPoolEventPerformed(PlanEvent event) {
		if (event.getState() == PlanPoolEventState.PLAN_CHANGED) {
			DefaultPlan updatedPlan = (DefaultPlan) event.getPlan();
			refreshPlanProductTableView(updatedPlan);
		}
	}

	private void refreshPlanProductTableView(DefaultPlan plan) {
		if (plan == null) {
			throw new PlanIsNullException();
		}

		List<PlanProduct> planProducts = plan.getPlannedProducts();
		planProducts.forEach(pp -> {
			int strandNr = pp.getStrandNumber();
			TableView<PlanProduct> tableView = getStrandTableMap().get(Integer.valueOf(strandNr));
			if (tableView != null) {
				List<TableColumn<PlanProduct, ?>> columns = tableView.getColumns();
				columns.forEach(col -> {
					String id = col.getId();
					col.setCellValueFactory(new PropertyValueFactory<>(id));

				});
			}
		});

		for (int strandNr = 1; strandNr <= serviceProvider.getCasterProperties().getNrOfStrand(); strandNr++) {
			TableView<PlanProduct> tableView = getStrandTableMap().get(Integer.valueOf(strandNr));
			tableView.setItems(getPlanProducts(planProducts, strandNr));
		}

	}

	private void refreshPlanTableView() {
		plans = getPlans(serviceProvider.getPlanPool().getAllPlan());
		StringBuilder planBuilder = new StringBuilder(getClass() + " Available plan in pool   \n");
		for (Plan plan : plans) {
			planBuilder.append(plan.getPlanName()).append("\n");
		}
		List<TableColumn<Plan, ?>> columns = getPlanTable().getColumns();
		columns.forEach(col -> {
			String id = col.getId();
			col.setCellValueFactory(new PropertyValueFactory<>(id));
		});
		getPlanTable().setItems(plans);
	}

	private void refreshHeatTableView(Plan selectedPlan) {
		ObservableList<Heat> heats = getHeats(
				serviceProvider.getHeatPool().findHeat(Integer.valueOf(selectedPlan.getPlanId())));
		List<TableColumn<Heat, ?>> columns = heatTable.getColumns();
		columns.forEach(col -> {
			String id = col.getId();
			col.setCellValueFactory(new PropertyValueFactory<>(id));
		});
		heatTable.setItems(heats);
	}

	private ObservableList<Plan> getPlans(List<Plan> plans) {
		return FXCollections.<Plan>observableArrayList(plans);
	}

	private ObservableList<Heat> getHeats(List<Heat> heats) {
		return FXCollections.<Heat>observableArrayList(heats);
	}

	private ObservableList<PlanProduct> getPlanProducts(List<PlanProduct> planProducts, int strandNr) {
		List<PlanProduct> productList = new ArrayList<>();
		for (PlanProduct pp : planProducts) {
			if (pp.getStrandNumber() == strandNr) {
				productList.add(pp);
			}
		}
		return FXCollections.<PlanProduct>observableArrayList(productList);
	}

	public TableView<Plan> getPlanTable() {
		return planTable;
	}

	public void setPlanTable(TableView<Plan> planTable) {
		this.planTable = planTable;
	}

	public Map<Integer, TableView<PlanProduct>> getStrandTableMap() {
		return strandTableMap;
	}

	@Override
	public ObjectProperty<Object> selectedProperty() {
		return selectedProperty;
	}

	public void select(Object object) {
		selectedProperty.set(object);
	}

	public Button getADD_PRODUCT() {
		return ADD_PRODUCT;
	}

	public Button getMODIFY_PRODUCT() {
		return MODIFY_PRODUCT;
	}

	public PlanProduct getSelectedPlanProduct() {
		return selectedPlanProduct;
	}

	private void setSelectedPlanProduct(PlanProduct selectedPlanProduct) {
		this.selectedPlanProduct = selectedPlanProduct;
	}

}
