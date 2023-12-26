package com.example.test123;

import Controller.Controller;
import Domain.*;
import Domain.Exp.ArithExp;
import Domain.Exp.ValueExp;
import Domain.Exp.VarExp;
import Domain.Exp.rH;
import Domain.Statement.*;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.*;
import Repo.Repository;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HelloApplication extends Application {
    private static final String[] PROGRAMS = {
            "0 : exit",
            "1 : (int v;(v=2;print(v)))",
            "2 : (int a;int b;(a=2+3*5;b=a+1;print(b)))",
            "3 : (bool a;int v;(a=true;if(a)then(v=2)else(v=3);print(v)))",
            "4 : (int v;(int v;v=20;Ref int a;new(a,v);new(v,30);print(rH(rH(a)))))",
            "5 : (int v;Ref int a;(v=10;new(a,22);fork(wH(a,30);int b;v=32;print(v);print(rH(a)));print(v);print(rH(a))))",
    };
    static IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));
    static IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                            new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                            new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"),
                                    new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
    static IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
    //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))) add this statement
    //a should first be initialized as ref type to an int value
    static IStmt ex4 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                    new CompStmt(new New("a", new VarExp("v")),
                            new CompStmt(new New("v", new ValueExp(new IntValue(30))),
                                    new PrintStmt(new rH(new rH(new VarExp("a")))))))));
    // int v; Ref int a; v=10;new(a,22);
    // fork(wH(a,30);v=32;print(v);print(rH(a)));
    // print(v);print(rH(a))
    static IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                    new CompStmt(new New("a", new ValueExp(new IntValue(22))),
                            new CompStmt(new forkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                    new CompStmt(new VarDeclStmt("b",new IntType()),new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                    new PrintStmt(new rH(new VarExp("a")))))))),
                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                            new PrintStmt(new rH(new VarExp("a")))))))));
    static TextMenu menu = new TextMenu();
    PrgState prg1 = new PrgState(ex1, "prg1.txt");
    Repository repo1 = new Repository(prg1);
    Controller ctrl1 = new Controller(repo1);
    PrgState prg2 = new PrgState(ex2, "prg2.txt");
    Repository repo2 = new Repository(prg2);
    Controller ctrl2 = new Controller(repo2);
    PrgState prg3 = new PrgState(ex3, "prg3.txt");
    Repository repo3 = new Repository(prg3);
    Controller ctrl3 = new Controller(repo3);
    PrgState prg4 = new PrgState(ex4, "prg4.txt");
    Repository repo4 = new Repository(prg4);
    Controller ctrl4 = new Controller(repo4);
    PrgState prg5 = new PrgState(ex5, "prg5.txt");
    Repository repo5 = new Repository(prg5);
    Controller ctrl5 = new Controller(repo5);

    private void populateSymTable(Controller controller,int selectedPrgStateID, TableView<Map.Entry<String, Value>> symTableTableView, ListView<String> exeStackListView) {
        PrgState selectedPrgState = null;
        for (PrgState prgState : controller.getPrgList()) {
            if (prgState.getID() == selectedPrgStateID) {
                selectedPrgState = prgState;
                break;
            }
        }

        if (selectedPrgState != null) {
            ObservableList<Map.Entry<String, Value>> symTableData = FXCollections.observableArrayList();
            MyIDictionary<String, Value> symTable = selectedPrgState.getSymTable();
            for (Map.Entry<String, Value> entry : symTable.getContent().entrySet()) {
                symTableData.add(new AbstractMap.SimpleEntry<>(entry));
            }

            TableColumn<Map.Entry<String, Value>, String> variableCol = new TableColumn<>("Variable");
            variableCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));

            TableColumn<Map.Entry<String, Value>, String> valueCol = new TableColumn<>("Value");
            valueCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().toString()));

            symTableTableView.getColumns().setAll(variableCol, valueCol);
            symTableTableView.setItems(symTableData);

            exeStackListView.getItems().clear();

            exeStackListView.getItems().add(selectedPrgState.getExeStack().toString());
        }
    }


    private void executeProgram(Controller controller, String selectedProgram,
                                ListView<String> outListView, ListView<String> fileTableListView,
                                ListView<String> prgStateListView, TableView<Map.Entry<String, Value>> symTableTableView,
                                ListView<String> exeStackListView, TableView<Map.Entry<Integer, Value>> heapTableView,
                                TextField numPrgStatesTextField){
        try {
            controller.oneStep1();
            ArrayList<PrgState> prglist = controller.getPrgList();
            numPrgStatesTextField.setText(String.valueOf(prglist.size()));
            numPrgStatesTextField.requestFocus();
            try {
                PrgState prg = prglist.get(0);
                heapTableView.getItems().clear();
                heapTableView.getColumns().clear();
                outListView.getItems().clear();
                fileTableListView.getItems().clear();
                prgStateListView.getItems().clear();
                outListView.getItems().add(prg.getOut().toString());
                fileTableListView.getItems().add(prg.getFileTable().toString());
                for (PrgState prgState : prglist) {
                    prgStateListView.getItems().add(String.valueOf(prgState.getID()));
                }


                ObservableList<Map.Entry<Integer, Value>> addressColumn = FXCollections.observableArrayList();
                ObservableList<Map.Entry<Integer, Value>> valueColumn2 = FXCollections.observableArrayList();
                MyIHeap<Integer, Value> heap = prg.getHeap();
                for (Map.Entry<Integer, Value> entry : heap.getContent().entrySet()) {
                    addressColumn.add(new AbstractMap.SimpleEntry<>(entry));
                    valueColumn2.add(new AbstractMap.SimpleEntry<>(entry));
                    System.out.println("verify"+entry);
                }
                TableColumn<Map.Entry<Integer, Value>, String> addressCol = new TableColumn<>("Address");
                addressCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().toString()));
                TableColumn<Map.Entry<Integer, Value>, String> valueCol2 = new TableColumn<>("Value");
                valueCol2.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().toString()));
                heapTableView.getColumns().addAll(addressCol, valueCol2);
                heapTableView.setItems(addressColumn);
            } catch (Exception ex) {
                // Handle exception
            }
        } catch (MyException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // First window
        primaryStage.setTitle("Select a Program");

        ListView<String> programListView = new ListView<>(FXCollections.observableArrayList(PROGRAMS));
        Button executeButton = new Button("Execute Selected Program");

        executeButton.setOnAction(e -> {
            String selectedProgram = programListView.getSelectionModel().getSelectedItem();
            if (selectedProgram != null) {
                // Execute the selected program here
                // Replace this comment with the logic to execute the selected program
                System.out.println("Executing: " + selectedProgram);
                menu.execute_commmand(selectedProgram.split(" ")[0]);
            }
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(programListView, executeButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Second window

        VBox layout2 = new VBox(20);

        Scene scene2 = new Scene(layout2, 300, 200);

        Stage secondStage = new Stage();
        secondStage.setTitle("Second Window");
        secondStage.setScene(scene2);
        TextField numPrgStatesTextField = new TextField(); // (a)

        TableView<Map.Entry<Integer, Value>> heapTableView = new TableView<>(); // (b)


        ListView<String> outListView = new ListView<>(); // (c)
        ListView<String> fileTableListView = new ListView<>(); // (d)
        ListView<String> prgStateListView = new ListView<>(); // (e)

        TableView<Map.Entry<String, Value>> symTableTableView = new TableView<>(); // (f)


        ListView<String> exeStackListView = new ListView<>(); // (g)

        Button onestepButton = new Button("Execute the Selected Program one step");
        onestepButton.setOnAction(e -> {
            String selectedProgram = programListView.getSelectionModel().getSelectedItem();
            if (selectedProgram != null) {
                // Execute the selected program here
                // Replace this comment with the logic to execute the selected program
                System.out.println("Executing: " + selectedProgram);
                if (selectedProgram.split(" ")[0].equals("1")) {
                   executeProgram(ctrl1, selectedProgram, outListView, fileTableListView, prgStateListView, symTableTableView, exeStackListView, heapTableView, numPrgStatesTextField);
                    prgStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            int selectedPrgStateID = Integer.parseInt(newValue);
                            populateSymTable(ctrl1,selectedPrgStateID, symTableTableView, exeStackListView);
                        }
                    });
                } else if (selectedProgram.split(" ")[0].equals("2")) {
                    executeProgram(ctrl2, selectedProgram, outListView, fileTableListView, prgStateListView, symTableTableView, exeStackListView, heapTableView, numPrgStatesTextField);
                    prgStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            int selectedPrgStateID = Integer.parseInt(newValue);
                            populateSymTable(ctrl2,selectedPrgStateID, symTableTableView, exeStackListView);
                        }
                    });
                } else if (selectedProgram.split(" ")[0].equals("3")) {
                    executeProgram(ctrl3, selectedProgram, outListView, fileTableListView, prgStateListView, symTableTableView, exeStackListView, heapTableView, numPrgStatesTextField);
                    prgStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            int selectedPrgStateID = Integer.parseInt(newValue);
                            populateSymTable(ctrl3,selectedPrgStateID, symTableTableView, exeStackListView);
                        }
                    });
                } else if (selectedProgram.split(" ")[0].equals("4")) {
                    executeProgram(ctrl4, selectedProgram, outListView, fileTableListView, prgStateListView, symTableTableView, exeStackListView, heapTableView, numPrgStatesTextField);
                    prgStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            int selectedPrgStateID = Integer.parseInt(newValue);
                            populateSymTable(ctrl4,selectedPrgStateID, symTableTableView, exeStackListView);
                        }
                    });
                } else if (selectedProgram.split(" ")[0].equals("5")) {
                    executeProgram(ctrl5, selectedProgram, outListView, fileTableListView, prgStateListView, symTableTableView, exeStackListView, heapTableView, numPrgStatesTextField);
                    prgStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            int selectedPrgStateID = Integer.parseInt(newValue);
                            populateSymTable(ctrl5, selectedPrgStateID, symTableTableView, exeStackListView);
                        }
                    });
                }
            }
        });

        layout2.getChildren().addAll(numPrgStatesTextField, heapTableView, outListView, fileTableListView, prgStateListView, symTableTableView, exeStackListView, onestepButton);
        primaryStage.show();
        secondStage.show();
    }

    public static void main(String[] args) {
        try {
            ex1.typecheck(new MyDictionary<String, Type>());
            ex2.typecheck(new MyDictionary<String, Type>());
            ex3.typecheck(new MyDictionary<String, Type>());
            ex4.typecheck(new MyDictionary<String, Type>());
            ex5.typecheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), new Controller(new Repository(new PrgState(ex1, "prg1.txt")))));
        menu.addCommand(new RunExample("2", ex2.toString(), new Controller(new Repository(new PrgState(ex2, "prg2.txt")))));
        menu.addCommand(new RunExample("3", ex3.toString(), new Controller(new Repository(new PrgState(ex3, "prg3.txt")))));
        menu.addCommand(new RunExample("4", ex4.toString(), new Controller(new Repository(new PrgState(ex4, "prg4.txt")))));
        menu.addCommand(new RunExample("5", ex5.toString(), new Controller(new Repository(new PrgState(ex5, "prg5.txt")))));
        launch(args);
    }
}

// modification to be done at f and g
