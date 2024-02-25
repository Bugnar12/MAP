/*
package view;

import collections.*;
import controller.Controller;
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

public class View {
    public static void main(String[] args) throws Exception {

        //int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        MyStack<IStmt> stack1 = new MyStack<>();
        MyDictionary<String, Value> symbolTable1 = new MyDictionary<>();
        MyList<Value> output1 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        Heap<Integer, Value> heap1 = new Heap<>();
        MyIDictionary<String, Type> typeEnv1 = new MyDictionary<>();
        ex1.typeCheck(typeEnv1);
        PrgState programState1 = new PrgState(stack1, symbolTable1, output1, fileTable1, heap1, ex1);
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
        Heap<Integer, Value> heap2 = new Heap<>();
        MyIDictionary<String, Type> typeEnv2 = new MyDictionary<>();
        ex2.typeCheck(typeEnv2);
        PrgState programState2 = new PrgState(stack2, symbolTable2, output2, fileTable2, heap2, ex2);
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
        Heap<Integer, Value> heap3 = new Heap<>();
        MyIDictionary<String, Type> typeEnv3 = new MyDictionary<>();
        ex3.typeCheck(typeEnv3);
        PrgState programState3 = new PrgState(stack3, symbolTable3, output3, fileTable3, heap3, ex3);
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
        Heap<Integer, Value> heap4 = new Heap<>();
        MyIDictionary<String, Type> typeEnv4 = new MyDictionary<>();
        ex4.typeCheck(typeEnv4);
        PrgState programState4 = new PrgState(stack4, symbolTable4, output4, fileTable4, heap4, ex4);
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

        MyIDictionary<String, Type> typeEnv5 = new MyDictionary<>();
        programExample5.typeCheck(typeEnv5);
        PrgState currentProgramState5 = new PrgState(new MyStack<>(), new MyDictionary<String, Value>(),
                new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), programExample5);
        IRepository repository5 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log5.txt");
        repository5.add(currentProgramState5);
        Controller ctrl5 = new Controller(repository5);

        MyStack<IStmt> stack5 = new MyStack<>();
        MyDictionary<String, Value> symbolTable5 = new MyDictionary<>();
        MyList<Value> output5 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable5 = new MyDictionary<>();
        IHeap<Integer, Value> heap5 = new Heap<>();
        MyIDictionary<String, Type> typeEnv6 = new MyDictionary<>();
        ex6.typeCheck(typeEnv6);
        PrgState programState6 = new PrgState(stack5, symbolTable5, output5, fileTable5, heap5, ex6);
        IRepository repository6 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log6.txt");
        repository6.add(programState6);
        Controller ctrl6 = new Controller(repository6);

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

        MyIStack<IStmt> stack6 = new MyStack<>();
        MyDictionary<String, Value> symbolTable6 = new MyDictionary<>();
        MyList<Value> output6 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>();
        IHeap<Integer, Value> heap6 = new Heap<>();
        MyIDictionary<String, Type> typeEnv7 = new MyDictionary<>();
        ex7.typeCheck(typeEnv7);
        PrgState programState7 = new PrgState(stack6, symbolTable6, output6, fileTable6, heap6, ex7);
        IRepository repository7 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log7.txt");
        repository7.add(programState7);
        Controller ctrl7 = new Controller(repository7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new readHeapExp(new readHeapExp(new VarExp("a")))))))));

        MyIStack<IStmt> stack8 = new MyStack<>();
        MyDictionary<String, Value> symbolTable8 = new MyDictionary<>();
        MyList<Value> output8 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable8 = new MyDictionary<>();
        IHeap<Integer, Value> heap8 = new Heap<>();
        MyIDictionary<String, Type> typeEnv8 = new MyDictionary<>();
        ex8.typeCheck(typeEnv8);
        PrgState programState8 = new PrgState(stack8, symbolTable8, output8, fileTable8, heap8, ex8);
        IRepository repository8 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log8.txt");
        repository8.add(programState8);
        Controller ctrl8 = new Controller(repository8);

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

        MyIStack<IStmt> stack9 = new MyStack<>();
        MyDictionary<String, Value> symbolTable9 = new MyDictionary<>();
        MyList<Value> output9 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable9 = new MyDictionary<>();
        IHeap<Integer, Value> heap9 = new Heap<>();
        MyIDictionary<String, Type> typeEnv9 = new MyDictionary<>();
        ex9.typeCheck(typeEnv9);
        PrgState programState9 = new PrgState(stack9, symbolTable9, output9, fileTable9, heap9, ex9);
        IRepository repository9 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log9.txt");
        repository9.add(programState9);
        Controller ctrl9 = new Controller(repository9);

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

        MyIStack<IStmt> stack10 = new MyStack<>();
        MyDictionary<String, Value> symbolTable10 = new MyDictionary<>();
        MyList<Value> output10 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable10 = new MyDictionary<>();
        IHeap<Integer, Value> heap10 = new Heap<>();
        MyIDictionary<String, Type> typeEnv10 = new MyDictionary<>();
        ex10.typeCheck(typeEnv10);
        PrgState programState10 = new PrgState(stack10, symbolTable10, output10, fileTable10, heap10, ex10);
        IRepository repository10 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log10.txt");
        repository10.add(programState10);
        Controller ctrl10 = new Controller(repository10);

        IStmt declare_v_11 = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt allocate_v_11 = new NewStmt("v", new ValueExp(new IntValue(20)));
        IStmt print_v_11 = new PrintStmt(new readHeapExp(new VarExp("v")));
        IStmt write_heap_v_11 = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        IStmt print_v_11_2 = new PrintStmt(new ArithExp(new readHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1));

        IStmt ex11 = new CompStmt(declare_v_11, new CompStmt(allocate_v_11, new CompStmt
                (print_v_11, new CompStmt(write_heap_v_11, print_v_11_2))));

        MyIStack<IStmt> stack11 = new MyStack<>();
        MyDictionary<String, Value> symbolTable11 = new MyDictionary<>();
        MyList<Value> output11 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable11 = new MyDictionary<>();
        IHeap<Integer, Value> heap11 = new Heap<>();
        MyIDictionary<String, Type> typeEnv11 = new MyDictionary<>();
        ex11.typeCheck(typeEnv11);
        PrgState programState11 = new PrgState(stack11, symbolTable11, output11, fileTable11, heap11, ex11);
        IRepository repository11 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log11.txt");
        repository11.add(programState11);
        Controller ctrl11 = new Controller(repository11);

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

        MyIStack<IStmt> stack12 = new MyStack<>();
        MyDictionary<String, Value> symbolTable12 = new MyDictionary<>();
        MyList<Value> output12 = new MyList<>();
        MyDictionary<StringValue, BufferedReader> fileTable12 = new MyDictionary<>();
        IHeap<Integer, Value> heap12 = new Heap<>();
        MyIDictionary<String, Type> typeEnv12 = new MyDictionary<>();
        ex12.typeCheck(typeEnv12);
        PrgState programState12 = new PrgState(stack12, symbolTable12, output12, fileTable12, heap12, ex12);
        IRepository repository12 = new Repository("D:\\UBB\\Semester 3\\Advanced Programming Methods\\ToyLanguageInterpreter\\log12.txt");
        repository12.add(programState12);
        Controller ctrl12 = new Controller(repository12);


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
            menu.addCommand(new RunExampleCommand("7", "int v; v=4; (while (v>0) print(v);v=v-1);print(v)", ctrl7));
            menu.addCommand(new RunExampleCommand("8", "int v; Ref int a; new(a,20); new(v,30); print(rH(rH(a)))", ctrl8));
            menu.addCommand(new RunExampleCommand("9", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)", ctrl9));
            menu.addCommand(new RunExampleCommand("10", "Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))", ctrl10));
            menu.addCommand(new RunExampleCommand("11", "Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5)", ctrl11));
            menu.addCommand(new RunExampleCommand("12", "Ref int v;new(v,10);Ref int a; new(a,22);fork(wH(a,30);v=32;print(v);print(rH(a)));print(v);print(rH(a))", ctrl12));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        menu.show();
    }
}
*/
