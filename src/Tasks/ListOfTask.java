package Tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ListOfTask {
    private final Map<Integer, Task> listOfTask = new HashMap<>();

    protected void addToList(int id, Task o) {
        listOfTask.put(id, o);
    }

    protected final Map<Integer, Task> getListOfTask() {
        return listOfTask;
    }

    @Override
    public String toString() {
        return "Список задач{" + listOfTask.values() +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfTask that = (ListOfTask) o;
        return listOfTask.equals(that.listOfTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfTask);
    }
}
