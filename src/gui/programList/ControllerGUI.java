package gui.programList;

import collections.IHeap;
import collections.MyIDictionary;
import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.PrgState;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ControllerGUI {
    private Controller ctrl;
    @FXML
    private TextField prgStateNumber;
    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;
    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> programStateListView;
    @FXML
    private TableView<Pair<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Pair<String, Value>, String> symbolVariableColumn;
    @FXML
    private TableColumn<Pair<String, Value>, String> symbolValueColumn;
    @FXML
    private ListView<String> executionStackView;
    @FXML
    private Button runOneStepButton;

    public void setController(Controller ctrl) {
        this.ctrl = ctrl;
        this.populateAll();
    }
    @FXML
    public void initialize() {
        //set constraints on the programStates
        programStateListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        symbolVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        symbolValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
    }

    private PrgState getCurrentProgramState() {
        if (ctrl.getProgramStates().size() == 0)
            return null;
        else {
            int id = programStateListView.getSelectionModel().getSelectedIndex();
            if (id == -1)
                return ctrl.getProgramStates().get(0);
            else
                return ctrl.getProgramStates().get(id);
        }
    }

    private void populateNumberOfPrgStatesTextField() {
        prgStateNumber.setText(String.valueOf(ctrl.getProgramStates().size()));
    }

    private void populateHeapTableView() {
        PrgState currentProgramState = this.getCurrentProgramState();
        IHeap currentHeap = currentProgramState.getHeap();
        ArrayList<Pair<Integer, Value>> heapEntries = new ArrayList<>();
        for (Object key : currentHeap.getContent().keySet()) {
            heapEntries.add(new Pair<>((Integer) key, (Value) currentHeap.get(key)));
        }

        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));

    }

    private void populateOutputListView() {
        PrgState programState = this.getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<Value> outputValues = Objects.requireNonNull(programState).getOut().getList();
        for (int i = 0; i < outputValues.size(); i++)
            output.add(outputValues.get(i).toString());

        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTable() {
        PrgState programState = this.getCurrentProgramState();
        List<String> fileTable = new ArrayList<>();
        for (StringValue key : programState.getFileTable().getContent().keySet())
            fileTable.add(key.toString());

        fileTableListView.setItems(FXCollections.observableArrayList(fileTable));
    }

    private void populateProgramStateListView() {
        List<PrgState> programStates = ctrl.getProgramStates();
        List<Integer> prgStatesID = programStates.stream().map(PrgState::getId).collect(Collectors.toList());
        programStateListView.setItems(FXCollections.observableArrayList(prgStatesID));
        this.populateNumberOfPrgStatesTextField();
    }

    private void populateSymbolTableView() {
        PrgState programState = this.getCurrentProgramState();
        MyIDictionary<String, Value> symTblView = Objects.requireNonNull(programState).getSymbolTable();
        ArrayList<Pair<String, Value>> symTblEntries = new ArrayList<>();

        for (Map.Entry<String, Value> entry : symTblView.getContent().entrySet())
            symTblEntries.add(new Pair<>(entry.getKey(), entry.getValue()));

        symbolTableView.setItems(FXCollections.observableArrayList(symTblEntries));
    }

    private void populateExecutionStack() {
        PrgState programState = this.getCurrentProgramState();
        List<String> exeStackString = new ArrayList<>();
        if (programState != null)
            for (IStmt statement : programState.getExeStack().reverse_stack())
                exeStackString.add(statement.toString());

        executionStackView.setItems(FXCollections.observableList(exeStackString));
    }

    private void populateAll() {
        if (getCurrentProgramState() == null)
            return;
        populateHeapTableView();
        populateOutputListView();
        populateFileTable();
        populateProgramStateListView();
        populateSymbolTableView();
        populateExecutionStack();
    }

    @FXML
    private void runOneStep(MouseEvent mouseClick) {
        if (this.ctrl != null) {
            try {
                List<PrgState> programStates = Objects.requireNonNull(this.ctrl.getProgramStates());
                if (programStates.size() > 0) {
                    this.ctrl.oneStepGUI();
                    this.populateAll();
                    programStates = this.ctrl.removeCompletedProgram(this.ctrl.getProgramStates());
                    this.ctrl.setProgramStates(programStates);
                    populateProgramStateListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error occured!");
                    alert.setContentText("No program states left to execute!");
                    alert.showAndWait();
                }
            } catch (InterruptedException ie) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error occured!");
                alert.setContentText("Error has occured in the logic of the execution!");
                alert.setContentText(ie.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error occured!");
            alert.setContentText("No program selected! Please select one!");
            alert.showAndWait();
        }
    }
}
