package exception;

public class InvalidRepositroy extends Exception{
    public InvalidRepositroy(String message) {
        super(message);
    }

    public InvalidRepositroy(){
        super("Invalid repository!");
    }
}
