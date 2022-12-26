package Tasks;

import Tasks.Enum.TaskRepeatFunc;
import Tasks.Enum.TypeOfTask;
import Tasks.Methods.Methods;
import Tasks.MyException.EmptyStringException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class Task
        implements Methods {
    private String taskName;
    private String description;
    private TypeOfTask typeOfTask;
    private TaskRepeatFunc taskRepeatFunc;
    private LocalDate date;
    protected static final ListOfTask listOfTask = new ListOfTask();
    private static int count;
    private final int id;
    private final LocalDate timeCreation;

    public Task(String taskName,
                String description,
                int typeOfTask,
                int taskRepeatFunc,
                LocalDate date)
            throws EmptyStringException {
        setTaskName(taskName);
        setDescription(description);
        setTypeOfTask(typeOfTask);
        setTaskRepeatFunc(taskRepeatFunc);
        setDate(date);
        this.timeCreation = LocalDate.now();
        this.id = ++count;
        listOfTask.addToList(getId(), this);
    }

    protected static void print(Scanner scanner) {
        System.out.print("Введите пароль: ");
        String pass = scanner.next();
        if (pass.equals("111")) {
            if (listOfTask.getListOfTask().size() == 0) {
                System.out.println("Список задач пуст");
            } else {
                for (Task task : listOfTask.getListOfTask().values()) {
                    System.out.print(task);
                }
            }
        } else {
            System.out.println("Неверный пароль!");
        }

    }

    protected final String getTaskName() {
        return taskName;
    }

    private int getId() {
        return id;
    }

    protected final TaskRepeatFunc getTaskRepeatFunc() {
        return taskRepeatFunc;
    }

    protected final LocalDate getDate() {
        return date;
    }

    protected final LocalDate getTimeCreation() {
        return timeCreation;
    }

    private void setTypeOfTask(int typeOfTask) {
        switch (typeOfTask) {
            case 1 -> this.typeOfTask = TypeOfTask.PERSONAL_TASK;
            case 2 -> this.typeOfTask = TypeOfTask.WORK_TASK;
        }
    }

    private void setTaskRepeatFunc(int taskRepeatFunc) {
        switch (taskRepeatFunc) {
            case 0 -> this.taskRepeatFunc = TaskRepeatFunc.SINGLE;
            case 1 -> this.taskRepeatFunc = TaskRepeatFunc.DAILY;
            case 2 -> this.taskRepeatFunc = TaskRepeatFunc.WEEKLY;
            case 3 -> this.taskRepeatFunc = TaskRepeatFunc.MONTHLY;
            case 4 -> this.taskRepeatFunc = TaskRepeatFunc.YEARLY;
        }
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    protected final void setDescription(String description) throws EmptyStringException {
        if (description == null || description.trim().isEmpty()) {
            throw new EmptyStringException();
        }
        this.description = description.trim();
    }

    protected final void setTaskName(String taskName) throws EmptyStringException {
        if (taskName == null || taskName.trim().isEmpty()) {
            throw new EmptyStringException();
        }
        this.taskName = taskName.trim();
    }

    @Override
    public String toString() {
        return "ID_" + id + "[" +
                taskName + ", " +
                description + ", " +
                typeOfTask + ", " +
                timeCreation + ", " +
                taskRepeatFunc + ", " +
                (getTaskRepeatFunc() == TaskRepeatFunc.WEEKLY ? date.getDayOfWeek() : date) + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && typeOfTask == task.typeOfTask && taskRepeatFunc == task.taskRepeatFunc && Objects.equals(date, task.date) && Objects.equals(timeCreation, task.timeCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, typeOfTask, taskRepeatFunc, date, id, timeCreation);
    }
}
