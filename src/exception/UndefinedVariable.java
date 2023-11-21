package exception;

public class UndefinedVariable extends Exception {
    public UndefinedVariable(String message) {
        super(message);
    }

    public UndefinedVariable() {
        super("Undefined variable!");
    }
}
