package exception;

public class InvalidOperator extends Exception{
    public InvalidOperator(){super("Invalid operator!");}
    public InvalidOperator(String msg){super(msg);}
}
