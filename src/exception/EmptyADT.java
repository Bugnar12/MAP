package exception;

public class EmptyADT extends Exception{

    public EmptyADT(String message) {
        super(message);
    }

    public EmptyADT() {
        super("The ADT is empty!");
    }
}
