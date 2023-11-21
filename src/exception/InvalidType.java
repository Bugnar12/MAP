package exception;

public class InvalidType extends Exception{
    public InvalidType(){super("Invalid type!");}
    public InvalidType(String msg){super(msg);}
}
