package gui.programList;

import collections.*;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import model.PrgState;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProgramList {
    private ControllerGUI programExecutorController;
    public void setControllerView(ControllerGUI controllerGUI)
    {
        this.programExecutorController = controllerGUI;
    }
    @FXML
    private ListView<IStmt> programListViewIStmt;
    @FXML
    private ListView<String> programListViewString;
    private List<IStmt> programs;
    @FXML
    private Button displayButton;

    @FXML
    public void initialize() throws Exception
    {
        programListViewIStmt.setItems(getProgramsGUI());
        programListViewString.setItems(setProgramsString());
        programListViewIStmt.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(MouseEvent actionEvent) {
        IStmt selectedStatement = programListViewIStmt.getSelectionModel().getSelectedItem();

        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            try {
                selectedStatement.typeCheck(new MyDictionary<String, Type>());
                LatchTable latchTable = new LatchTable(new HashMap<>(), 0);
                PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), latchTable,selectedStatement);
                ArrayList<PrgState> l1 = new ArrayList<>();
                l1.add(prg1);
                IRepository repo1 = new Repository(l1,"log1.txt");
                Controller controller = new Controller(repo1);
                programExecutorController.setController(controller);
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<String> setProgramsString()
    {
        List<String> programs = new ArrayList<>();
        programs.add("1. int v; v = 2; print(v)");
        programs.add("2. int a; int b; a=2+3*5; b=a+1; print(b)");
        programs.add("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        programs.add("4. int a; int b; b = 8; a = b + 10/5; Print(a)");
        programs.add("5. string varf; varf=\"test.in.txt\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)");
        programs.add("6. int v; v=4; (while (v>0) print(v);v=v-1);print(v)");
        programs.add("7. Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))");
        programs.add("8. Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)");
        programs.add("9. Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)");
        programs.add("10. Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))");
        programs.add("11. Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5)");
        programs.add("12. int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))");
        programs.add("13.Ref int a; Ref int b; int v; new(a,0); new(b,0); wh(a,1); wh(b,2); v=(rh(a)<rh(b))?100:200 print(v);v= ((rh(b)-2)>rh(a))?100:200 ;print(v)");
        programs.add("14.Ref int v1; Ref int v2; Ref int v3; int cnt; new(v1,2); new(v2,3); new(v3,4); newLatch(cnt,rH(v2));\n" +
                "fork(wh(v1,rh(v1)*10);print(rh(v1));countDown(cnt);\n" +
                "fork(wh(v2,rh(v2)*10);print(rh(v2));countDown(cnt);\n" +
                "fork(wh(v3,rh(v3)*10);print(rh(v3));countDown(cnt)))); await(cnt); print(100); countDown(cnt); print(100)");

        return FXCollections.observableArrayList(programs);
    }
    @FXML
    private ObservableList<IStmt> getProgramsGUI() throws Exception {
        List<IStmt> programs = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        IStmt declare_a = new VarDeclStmt("a", new IntType());
        IStmt declare_b = new VarDeclStmt("b", new IntType());
        Exp multiply_a = new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3); //'.' -> CHAR
        Exp add_a = new ArithExp(new ValueExp(new IntValue(2)), multiply_a, 1);
        IStmt assign_a = new AssignStmt("a", add_a);
        Exp add_b = new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1);
        IStmt assign_b = new AssignStmt("b", add_b);
        IStmt print_b = new PrintStmt(new VarExp("b")); //"." -> STRING
        //Composing the statements and expressions
        IStmt ex2 = new CompStmt(declare_a, new CompStmt(declare_b, new CompStmt(assign_a, new CompStmt(assign_b, print_b))));

        //ex3 : bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        //My own example
        //ex4 : int a; int b; b = 8; a = b + 10/5; Print(a)

        IStmt ex4 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(8))),
                                new CompStmt(new AssignStmt("a", new ArithExp(new VarExp("b"), new ArithExp(new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)), 4), 1)), new PrintStmt(new VarExp("a"))
                                ))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in.txt"))),
                        new CompStmt(new OpenReadFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFile(new VarExp("varf"))))))))))));

        IStmt declare_a5 = new VarDeclStmt("a", new IntType());
        IStmt assign_a5 = new AssignStmt("a", new ValueExp(new IntValue(25)));
        IStmt declare_b5 = new VarDeclStmt("b", new IntType());
        IStmt assign_b5 = new AssignStmt("b", new ValueExp(new IntValue(30)));
        Exp relationalExpression5 = new RelationalExp(new VarExp("a"), new VarExp("b"), ">");
        IStmt print_a5 = new PrintStmt(new VarExp("a"));
        IStmt print_b5 = new PrintStmt(new VarExp("b"));
        IStmt if_statement5 = new IfStmt(relationalExpression5, print_a5, print_b5);

        IStmt programExample5 = new CompStmt(declare_a5, new CompStmt(assign_a5,
                new CompStmt(declare_b5, new CompStmt(assign_b5, if_statement5))));

        IStmt declare_v10 = new VarDeclStmt("v", new IntType());
        IStmt assign_v10_1 = new AssignStmt("v", new ValueExp(new IntValue(4)));
        Exp rel_expr10 = new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">");
        IStmt print_v10_1 = new PrintStmt(new VarExp("v"));
        Exp arithmetic_v10 = new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2);
        IStmt assign_v10_2 = new AssignStmt("v", arithmetic_v10);
        IStmt compoundStatement_v10 = new CompStmt(print_v10_1, assign_v10_2);
        IStmt whileStatement_v10 = new WhileStmt(rel_expr10, compoundStatement_v10);
        IStmt print_v10_2 = new PrintStmt(new VarExp("v"));

        IStmt ex7 = new CompStmt(declare_v10, new CompStmt(assign_v10_1, new CompStmt(whileStatement_v10, print_v10_2)));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new readHeapExp(new readHeapExp(new VarExp("a")))))))));

        IStmt declare_v9 = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt new_v9 = new NewStmt("v", new ValueExp(new IntValue(20)));
        IStmt declare_a9 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        IStmt new_a9 = new NewStmt("a", new VarExp("v"));
        IStmt print_v9 = new PrintStmt(new VarExp("v"));
        IStmt print_a9 = new PrintStmt(new VarExp("a"));

        IStmt ex9 = new CompStmt(declare_v9, new CompStmt(new_v9, new CompStmt
                (declare_a9, new CompStmt
                        (new_a9, new CompStmt
                                (print_v9, print_a9)))));

        IStmt declare_v10_1 = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt new_v10_1 = new NewStmt("v", new ValueExp(new IntValue(20)));
        IStmt declare_a10_1 = new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        IStmt new_a10_1 = new NewStmt("a", new VarExp("v"));
        IStmt allocate_v10_1 = new NewStmt("v", new ValueExp(new IntValue(30)));

        //here I modified with an extra NewStmt
        IStmt allocate_a10_2 = new NewStmt("a", new VarExp("v"));

        Exp read_a_1 = new readHeapExp(new VarExp("a"));
        Exp read_a_2 = new readHeapExp(read_a_1);
        IStmt print_a10 = new PrintStmt(read_a_2);

        IStmt ex10 = new CompStmt(declare_v10_1, new CompStmt(new_v10_1, new CompStmt
                (declare_a10_1, new CompStmt
                        (new_a10_1, new CompStmt(
                                allocate_v10_1, new CompStmt(allocate_a10_2, print_a10))))));

        IStmt declare_v_11 = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt allocate_v_11 = new NewStmt("v", new ValueExp(new IntValue(20)));
        IStmt print_v_11 = new PrintStmt(new readHeapExp(new VarExp("v")));
        IStmt write_heap_v_11 = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        IStmt print_v_11_2 = new PrintStmt(new ArithExp(new readHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1));

        IStmt ex11 = new CompStmt(declare_v_11, new CompStmt(allocate_v_11, new CompStmt
                (print_v_11, new CompStmt(write_heap_v_11, print_v_11_2))));

        IStmt declare_v12 = new VarDeclStmt("v", new IntType());
        IStmt declare_a12 = new VarDeclStmt("a", new RefType(new IntType()));
        IStmt assign_v12_1 = new AssignStmt("v", new ValueExp(new IntValue(10)));
        IStmt alloc_v12 = new NewStmt("a", new ValueExp(new IntValue(22)));
        IStmt write_a12 = new WriteHeapStmt("a", new ValueExp(new IntValue(30)));
        IStmt assign_v12_2 = new AssignStmt("v", new ValueExp(new IntValue(32)));
        IStmt print_v12_1 = new PrintStmt(new VarExp("v"));
        Exp read_v12 = new readHeapExp(new VarExp("a"));
        IStmt print_v12_2 = new PrintStmt(read_v12);
        IStmt fork_12 = new ForkStmt(new CompStmt(write_a12, new CompStmt(assign_v12_2, new CompStmt(print_v12_1, print_v12_2))));

        IStmt ex12 = new CompStmt(declare_v12, new CompStmt(declare_a12, new CompStmt(assign_v12_1, new CompStmt(alloc_v12, new CompStmt(fork_12, new CompStmt(print_v12_1, print_v12_2))))));

        IStmt ex13 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("b", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))),
                                        new CompStmt(new NewStmt("b", new ValueExp(new IntValue(0))),
                                                new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(1))),
                                                        new CompStmt(new WriteHeapStmt("b", new ValueExp(new IntValue(2))),
                                                                new CompStmt(new ConditionalAssignment("v", new RelationalExp( new readHeapExp(new VarExp("a")), new readHeapExp(new VarExp("b")), "<"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                new CompStmt(new ConditionalAssignment("v", new RelationalExp(new ArithExp(new readHeapExp(new VarExp("b")), new ValueExp(new IntValue(2)), 2), new readHeapExp(new VarExp("a")), ">"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                                        new PrintStmt(new VarExp("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex14 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                            new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                    new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                        new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                            new CompStmt(new NewLatch("cnt", new readHeapExp(new VarExp("v2"))),
                                                new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("v1", new ArithExp(new readHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), 3)),
                                                    new CompStmt(new PrintStmt(new readHeapExp(new VarExp("v1"))),
                                                        new CompStmt(new CountDown("cnt"),
                                                                new ForkStmt(new CompStmt(new WriteHeapStmt("v2", new ArithExp(new readHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), 3)),
                                                                    new CompStmt(new PrintStmt(new readHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(new CountDown("cnt"),
                                                                            new ForkStmt(new CompStmt(new WriteHeapStmt("v3", new ArithExp(new readHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)), 3)),
                                                                                new CompStmt(new PrintStmt(new readHeapExp(new VarExp("v3"))),
                                                                                                    new CountDown("cnt")
                                                                                                        )
                                                                                                    )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                                    ),
                                                            new CompStmt(new Await("cnt"),
                                                                            new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                    new CompStmt(new CountDown("cnt"),
                                                                                            new PrintStmt(new ValueExp(new IntValue(100)))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        programs.add(ex4);
        programs.add(ex6);
        programs.add(ex7);
        programs.add(ex8);
        programs.add(ex9);
        programs.add(ex10);
        programs.add(ex11);
        programs.add(ex12);
        programs.add(ex13);
        programs.add(ex14);

        Font font = new Font("Rockwell", 18);
        programListViewIStmt.setStyle("-fx-font-size: " + font.getSize() + "px;");

        Font font1 = new Font("Rockwell", 18);
        programListViewString.setStyle("-fx-font-size: " + font.getSize() + "px;");

        return FXCollections.observableArrayList(programs);
    }
}
