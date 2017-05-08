package pl.gymkhana_gp.judge.presentation.views.printviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

public abstract class AbstractPlayerListController {
	
	protected final static int HEADER_HEIGHT = 30;
	
	private final int rowHeight;
	private final int rowsPerPage;
	
	private int currentPrintingIndex = 0;
	
	private ObservableList<PlayerViewData> playersToPrint = FXCollections.observableArrayList();
	
	public AbstractPlayerListController() {
		this(30, 23);
	}
	
	public AbstractPlayerListController(int rowHeight, int rowsPerPage) {
		this.rowHeight = rowHeight;
		this.rowsPerPage = rowsPerPage;
	}
	
	protected abstract TableView<PlayerViewData> getTableView();
	
	protected abstract Pane getRootPane();
	
	public boolean nextPage() {
		int startIndex = currentPrintingIndex;
		int endIndex = currentPrintingIndex + rowsPerPage;
		
		if(endIndex > playersToPrint.size()) {
			endIndex = playersToPrint.size();
		}
		
		System.out.println(startIndex + "; " + endIndex);
		if(endIndex <= currentPrintingIndex) {
			System.out.println("koniec");
			return false;
		}
		System.out.println("drukuje");
		
		ObservableList<PlayerViewData> sublistToPrint = FXCollections.observableArrayList(playersToPrint.subList(startIndex, endIndex));
		
		getTableView().setItems(sublistToPrint);
		getTableView().refresh();
		getTableView().autosize();
		
//		double size = rowHeight * (sublistToPrint.size() + 1.1)/* + HEADER_HEIGHT*/;
		double size = rowHeight * (sublistToPrint.size() + 0.1) + HEADER_HEIGHT;
		getRootPane().setMinHeight(size);
		getRootPane().setPrefHeight(size);
		getRootPane().setMaxHeight(size);
		
		currentPrintingIndex += rowsPerPage;
		return true;
	}

	protected int getCurrentPrintingIndex() {
		return currentPrintingIndex;
	}

	protected void setCurrentPrintingIndex(int currentPrintingIndex) {
		this.currentPrintingIndex = currentPrintingIndex;
	}

	protected ObservableList<PlayerViewData> getPlayersToPrint() {
		return playersToPrint;
	}

	protected void setPlayersToPrint(ObservableList<PlayerViewData> playersToPrint) {
		this.playersToPrint = playersToPrint;
		
		currentPrintingIndex = 0;
	}

	protected int getRowHeight() {
		return rowHeight;
	}

	protected int getRowsPerPage() {
		return rowsPerPage;
	}
	
}
