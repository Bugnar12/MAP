package exception;

public class ExistingVariable extends Exception{
    public ExistingVariable(String message) {
        super(message);
    }

    public ExistingVariable() {
        super("Variable already exists!");
    }
}
