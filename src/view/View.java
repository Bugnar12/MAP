package view;

import collections.*;
import controller.Controller;
import model.*;
import model.statement.*;
import model.value.*;
import model.expression.*;
import model.type.*;
import repository.IRepository;
import repository.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static void main(String[] args) {

        //int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        MyStack<IStmt> stack1 = new MyStack<>();
        MyDictionary<String, Value> symbolTable1 = new MyDictionary<>();
        MyList<Value> output1 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        PrgState programState1 = new PrgState(stack1, symbolTable1, output1, fileTable1, ex1);
        IRepository repository1 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log1.txt");
        repository1.add(programState1);
        Controller ctrl1 = new Controller(repository1);

        //ex2 : int a;int b; a=2+3*5;b=a+1;Print(b)
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

        MyStack<IStmt> stack2 = new MyStack<>();
        MyDictionary<String, Value> symbolTable2 = new MyDictionary<>();
        MyList<Value> output2 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        PrgState programState2 = new PrgState(stack2, symbolTable2, output2, fileTable2, ex2);
        IRepository repository2 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log2.txt");
        repository2.add(programState2);
        Controller ctrl2 = new Controller(repository2);


        //ex3 : bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        MyStack<IStmt> stack3 = new MyStack<>();
        MyDictionary<String, Value> symbolTable3 = new MyDictionary<>();
        MyList<Value> output3 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        PrgState programState3 = new PrgState(stack3, symbolTable3, output3, fileTable3, ex3);
        IRepository repository3 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log3.txt");
        repository3.add(programState3);
        Controller ctrl3 = new Controller(repository3);

        //My own example
        //ex4 : int a; int b; b = 8; a = b + 10/5; Print(a)

        IStmt ex4 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(8))),
                                new CompStmt(new AssignStmt("a", new ArithExp(new VarExp("b"), new ArithExp(new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)), 4), 1)), new PrintStmt(new VarExp("a"))
                                ))));

        MyStack<IStmt> stack4 = new MyStack<>();
        MyDictionary<String, Value> symbolTable4 = new MyDictionary<>();
        MyList<Value> output4 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        PrgState programState4 = new PrgState(stack4, symbolTable4, output4, fileTable4, ex4);
        IRepository repository4 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log4.txt");
        repository4.add(programState4);
        Controller ctrl4 = new Controller(repository4);

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


        PrgState currentProgramState5 = new PrgState(new MyStack<>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), programExample5);
        IRepository repository5 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log5.txt");
        repository5.add(currentProgramState5);
        Controller ctrl5 = new Controller(repository5);



        MyStack<IStmt> stack = new MyStack<>();
        MyDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyList<Value> output = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        PrgState programState6 = new PrgState(stack, symbolTable, output, fileTable, ex6);
        IRepository repository6 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log6.txt");
        repository6.add(programState6);
        Controller ctrl6 = new Controller(repository6);

        TextMenu menu = new TextMenu();
        try{
            menu.addCommand(new ExitCommand("0", "Exit"));
            menu.addCommand(new RunExampleCommand("1", "int v; v=2; Print(v)", ctrl1));
            menu.addCommand(new RunExampleCommand("2", "int a;int b; a=2+3*5;b=a+1;Print(b)", ctrl2));
            menu.addCommand(new RunExampleCommand("3", "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", ctrl3));
            menu.addCommand(new RunExampleCommand("4", "int a; int b; b = 8; a = b + 10/5; Print(a)", ctrl4));
            menu.addCommand(new RunExampleCommand("5", "int a; a = 25; int b; b = 30; IF (a > b) THEN print(a) ELSE print(b)", ctrl5));
            menu.addCommand(new RunExampleCommand("6", "string varf;\n" +
                                                                "      varf=\"test.in\";\n" +
                                                                "      openRFile(varf);\n" +
                                                                "      int varc;\n" +
                                                                "      readFile(varf,varc);print(varc);\n" +
                                                                "      readFile(varf,varc);print(varc)\n" +
                                                                "      closeRFile(varf)", ctrl6));
            }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        menu.show();
    }
}
