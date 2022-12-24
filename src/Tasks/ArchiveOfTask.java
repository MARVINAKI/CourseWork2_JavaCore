package Tasks;

import java.util.ArrayList;

public class ArchiveOfTask {
    private static final ArrayList<Task> archiveOfTask = new ArrayList<>();

    protected static void addToArchive(Task o) {
        archiveOfTask.add(o);
    }

    protected static void print() {
        if (archiveOfTask.size() == 0) {
            System.out.println("Архив удаленных задач пуст");
        } else {
            System.out.println("Архив удаленных задач:");
            for (Task task : archiveOfTask) {
                System.out.println("[" + task + "]");
            }
        }
    }
}
