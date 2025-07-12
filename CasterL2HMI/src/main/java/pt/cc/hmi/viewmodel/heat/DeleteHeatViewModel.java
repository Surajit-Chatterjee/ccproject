/**
 * 
 */
package pt.cc.hmi.viewmodel.heat;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import pt.cc.heat.Heat;
import pt.cc.hmi.service.ViewModelServiceProvider;
import pt.cc.hmi.viewmodel.ViewModel;

/**
 * 
 */
public class DeleteHeatViewModel extends ViewModel {
	private ViewModelServiceProvider serviceProvider;
	private AtomicReference<TableView<Heat>> atomTableView = new AtomicReference<TableView<Heat>>();
	private TabPane finNonFinTab = null;
	private TableView<Heat> heatTableView = null;

	/**
	 * @param parentView
	 * @param serviceProvider
	 */
	public DeleteHeatViewModel(Parent parentView, ViewModelServiceProvider serviceProvider) {
		super(parentView, serviceProvider);
		this.serviceProvider = serviceProvider;		
		finNonFinTab = (TabPane) serviceProvider.getTabPane("FinNonFinTabPane");
		finNonFinTab.getSelectionModel().selectFirst();
		Tab nonFinTab = finNonFinTab.getSelectionModel().getSelectedItem();
		loadInitialSelectedTable(nonFinTab);
		setSelectTabEvent(finNonFinTab);

	}

	public void deleteHeat() {
		if (atomTableView.get() != null) {
			ObservableList<Heat> heats = getHeats(atomTableView.get().getSelectionModel().getSelectedItems());
			if (!heats.isEmpty()) {
				boolean confirmed = isConfirmed(heats);
				if (confirmed) {
					heats.forEach(heat -> {
						serviceProvider.getHeatPool().deleteHeat(heat);
					});
					refreshHeatTableView(atomTableView.get());
				}
			}
		}

	}

	private boolean isConfirmed(ObservableList<Heat> heats) {
		Alert confirmAlert = null;
		StringBuilder heatNameBuilder = new StringBuilder();
		heats.forEach(heat -> {
			heatNameBuilder.append(heat.getHeatName());
			heatNameBuilder.append(",");
		});
		confirmAlert = new Alert(AlertType.CONFIRMATION, "Do you want to Delete Heat ? " + heatNameBuilder.toString());
		DialogPane dialogPane = confirmAlert.getDialogPane();
		dialogPane.getStylesheets().add(serviceProvider.getCasterProperties().getCssFileName());
		confirmAlert.initModality(Modality.APPLICATION_MODAL);
		confirmAlert.getDialogPane().setHeaderText("Confirm..");
		Optional<ButtonType> result = confirmAlert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == ButtonType.OK) {
				return true;
			} else if (result.get() == ButtonType.CANCEL) {
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void loadInitialSelectedTable(Tab tab) {
		Node tableView = tab.getContent();
		TableView<Heat> nonFinTable = (TableView<Heat>) tableView.lookup("#nonFinishedTable");
		nonFinTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		refreshHeatTableView(nonFinTable);
		atomTableView.set(nonFinTable);
	}

	@SuppressWarnings("unchecked")
	private void setSelectTabEvent(TabPane finNonFinTab) {

		finNonFinTab.getTabs().forEach(tab -> {
			tab.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event tabSelectionEvent) {
					if (tab.isSelected()) {
						if ("nonFinTab".equalsIgnoreCase(tab.getId())) {
							Node tableView = tab.getContent();
							heatTableView = (TableView<Heat>) tableView.lookup("#nonFinishedTable");
							heatTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
							System.out.println("tab selected " + tab.getId());
							atomTableView.set(heatTableView);
						}
						if ("finTab".equalsIgnoreCase(tab.getId())) {
							Node tableView = tab.getContent();
							heatTableView = (TableView<Heat>) tableView.lookup("#finishedTable");
							heatTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
							System.out.println("tab selected " + tab.getId());
						}
						refreshHeatTableView(heatTableView);
						atomTableView.set(heatTableView);
					}

				}
			});
		});
	}

	private void refreshHeatTableView(TableView<Heat> table) {
		ObservableList<Heat> heats = getHeats(serviceProvider.getHeatPool().getHeats());
		List<TableColumn<Heat, ?>> columns = table.getColumns();
		columns.forEach(col -> {
			String id = col.getId();
			col.setCellValueFactory(new PropertyValueFactory<>(id));
		});
		table.setItems(heats);
	}


}
