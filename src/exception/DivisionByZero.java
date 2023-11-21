package exception;

public class DivisionByZero extends Exception{
    public DivisionByZero() {super("Cannot divide by zero!");}
    public DivisionByZero(String msg) {super(msg);}
}
