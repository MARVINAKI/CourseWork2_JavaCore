package Tasks.Enum;

public enum TypeOfTask {
    PERSONAL_TASK("Персональная задача"),
    WORK_TASK("Рабочая задача");

    private final String type;

    TypeOfTask(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
