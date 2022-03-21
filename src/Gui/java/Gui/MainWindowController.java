package Gui;

import Gui.model.ADT.IDictionary;
import Gui.model.value.StringValue;
import Gui.model.value.Value;
import Gui.controller.Controller;
import Gui.model.ADT.IStack;
import Gui.model.exceptions.MyException;
import Gui.model.PrgState;
import Gui.model.statement.IStmt;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {
    @FXML
    private ListView<String> exeStackVIew;
    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableNames;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableValues;
    @FXML
    private Label progStatesCount;
    @FXML
    private Button execButton;
    @FXML
    private TableView<Map.Entry<Integer,Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer,Value>,Integer> heapTableAddr;
    @FXML
    private TableColumn <Map.Entry<Integer, Value>, String> heapTableValues;
    @FXML
    private ListView<String> outputView;

    @FXML
    private TableView<Map.Entry<StringValue, BufferedReader>> fileTableView;
    @FXML
    private TableColumn<Map.Entry<StringValue, BufferedReader>, String> fileTableID;
    @FXML
    private TableColumn<Map.Entry<StringValue, BufferedReader>, String> fileTableFile;
    @FXML
    private ListView<Integer> progIdentifiersView;

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populateProgStatesCount();
        populateIdentifiersView();
        execButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = null;

        heapTableAddr.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValues.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        symTableNames.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + " "));
        symTableValues.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));

        fileTableID.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));
        fileTableFile.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));

        progIdentifiersView.setOnMouseClicked(mouseEvent -> changeProgramStateHandler(getSelectedProgramState()));


        execButton.setOnAction(actionEvent -> { oneStepHandler(); });
        execButton.setDisable(true);
    }

    private void changeProgramStateHandler(PrgState currentProgState) {
        if (currentProgState == null)
            return;
        try {
            populateProgStatesCount();
            populateIdentifiersView();
            populateHeapTableView(currentProgState);
            populateOutputView(currentProgState);
            populateFileTableView(currentProgState);
            populateExeStackView(currentProgState);
            populateSymTableView(currentProgState);
        } catch (MyException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
        }

    }

    public void oneStepHandler() {
        if (controller == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "No program selected!");
            error.show();
            execButton.setDisable(true);
            return;
        }
        PrgState programState = getSelectedProgramState();
        if (programState != null && !programState.isNotCompleted()) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!");
            error.show();
            return;
        }
        try {
            controller.executeOneStep();
            changeProgramStateHandler(programState);
            if (controller.getRepository().getPrgList().size() == 0) {
                execButton.setDisable(true);
                Alert msg = new Alert(Alert.AlertType.INFORMATION, "Execution finished!");
                msg.show();
            }
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
            execButton.setDisable(true);
        }

    }

    private void populateProgStatesCount() {
        progStatesCount.setText("No of Program States:" + controller.getRepository().getPrgList().size());
    }

    private void populateHeapTableView(PrgState givenProgramState) {
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getHeap().getContent().entrySet())));
        heapTableView.refresh();
    }

    private void populateOutputView(PrgState givenProgramState) throws MyException {
        outputView.setItems(FXCollections.observableArrayList(givenProgramState.getOutConsole().getContenbt()));
        outputView.refresh();
    }

    private void populateFileTableView(PrgState givenProgramState) {
//        fileTableView.setItems(FXCollections.observableArrayList());
        fileTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getFileTable().getContent().entrySet())));
        fileTableView.refresh();
    }

    private void populateIdentifiersView() {
        progIdentifiersView.setItems(FXCollections.observableArrayList(controller.getRepository().getPrgList().stream().map(PrgState::getStateID).collect(Collectors.toList())));
        progIdentifiersView.refresh();
    }

    private void populateExeStackView(PrgState givenProgramState) {
        IStack<IStmt> stack = givenProgramState.getStack();
        List<String> stackOutput = new ArrayList<>();
        for (IStmt stm : stack.getValues()) {
            stackOutput.add(stm.toString());
        }
        Collections.reverse(stackOutput);
        exeStackVIew.setItems(FXCollections.observableArrayList(stackOutput));
    }

    private void populateSymTableView(PrgState givenProgramState) {
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getSymTable().getContent().entrySet())));
        symbolTableView.refresh();
    }

    private PrgState getSelectedProgramState() {
        if(progIdentifiersView.getSelectionModel().getSelectedIndex()==-1)
            return null;
        int id = progIdentifiersView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramWithId(id);

    }
}