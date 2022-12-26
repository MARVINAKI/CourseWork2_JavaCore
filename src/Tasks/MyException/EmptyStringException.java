package Tasks.MyException;

public class EmptyStringException extends Exception {
    public EmptyStringException() {
        super("Все поля должны быть заполнены!");
    }
}
