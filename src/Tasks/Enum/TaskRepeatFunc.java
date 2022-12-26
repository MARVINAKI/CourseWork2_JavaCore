package Tasks.Enum;

public enum TaskRepeatFunc {
    SINGLE("Однократная"),
    DAILY("Ежедневная"),
    WEEKLY("Еженедельная"),
    MONTHLY("Ежемесячная"),
    YEARLY("Ежегодная");
    private final String taskRepeatFunc;

    TaskRepeatFunc(String taskRepeatFunc) {
        this.taskRepeatFunc = taskRepeatFunc;
    }

    @Override
    public String toString() {
        return taskRepeatFunc;
    }
}
