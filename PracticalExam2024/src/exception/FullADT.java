package exception;

public class FullADT extends Exception{
    public FullADT(String message) {
        super(message);
    }

    public FullADT() {
        super("The ADT is full!");
    }

}
