package Tasks;

import Tasks.Enum.TaskRepeatFunc;
import Tasks.MyException.EmptyStringException;

import java.time.LocalDate;
import java.util.Scanner;

public class Console {

    public static void openConsole() {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                scanner.useDelimiter("\n");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    scanner.useDelimiter("\n");
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            deleteTask(scanner);
                            break;
                        case 3:
                            getDateTasks(scanner);
                            break;
                        case 4:
                            ArchiveOfTask.print();
                            break;
                        case 5:
                            printAdditionalOperations(scanner);
                            break;
                        case 0:
                            break label;
                        default:
                            System.out.println("Выберите пункт меню из списка!");
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        String taskName = inputTaskName(scanner);
        String description = inputDescription(scanner);
        int typeOfTask = inputTypeOfTask(scanner);
        String str = checkRepeatFunc(scanner);
        LocalDate date = LocalDate.EPOCH;
        int taskRepeatFunc = 0;
        switch (str) {
            case "N", "n" -> date = setSingleDate(scanner);
            case "Y", "y" -> {
                taskRepeatFunc = setTaskRepeatFunc(scanner);
                if (taskRepeatFunc == 2) {
                    date = setWeeklyDate(scanner);
                    break;
                }
                if (taskRepeatFunc == 3) {
                    date = setMonthlyDate(scanner);
                    break;
                }
                if (taskRepeatFunc == 4) {
                    date = setYearlyDate(scanner);
                    break;
                }
                if (taskRepeatFunc < 1 || taskRepeatFunc > 4) {
                    System.out.println("Ошибка выбора! Попробуйте снова!");
                    openConsole();
                }
            }
            default -> {
                System.out.println("Ошибка выбора! Попробуйте снова!");
                openConsole();
            }
        }
        try {
            new Task(taskName, description, typeOfTask, taskRepeatFunc, date);
        } catch (EmptyStringException e) {
            System.out.println(e.getMessage());
            openConsole();
        }
    }

    private static void deleteTask(Scanner scanner) {
        System.out.print("Введите идентификационный номер задачи: ");
        int id = scanner.nextInt();
        ArchiveOfTask.addToArchive(Task.listOfTask.getListOfTask().get(id));
        Task.listOfTask.getListOfTask().remove(id);
    }

    private static void getDateTasks(Scanner scanner) {
        System.out.print("Введите дату (DD.MM.YYYY): ");
        String str = scanner.next().trim().replace(" ", "");
        LocalDate date = LocalDate.of(Integer.parseInt(str.substring(6)), Integer.parseInt(str.substring(3, 5)), Integer.parseInt(str.substring(0, 2)));
        for (Task task : Task.listOfTask.getListOfTask().values()) {
            if (task == null) {
                continue;
            }
            switch (task.getTaskRepeatFunc()) {
                case DAILY:
                    if (task.getTimeCreation().compareTo(date) <= 0) {
                        System.out.println(task.getTaskName());
                    }
                    break;
                case SINGLE:
                    if (date.equals(task.getDate())
                            && task.getTimeCreation().compareTo(date) <= 0) {
                        System.out.println(task.getTaskName());
                    }
                    break;
                case WEEKLY:
                    if (date.getDayOfWeek().equals(task.getDate().getDayOfWeek())
                            && task.getTimeCreation().compareTo(date) < 0) {
                        System.out.println(task.getTaskName());
                    }
                    break;
                case MONTHLY:
                    if (date.getDayOfMonth() == task.getDate().getDayOfMonth()
                            && task.getTimeCreation().compareTo(date) < 0) {
                        System.out.println(task.getTaskName());
                    }
                    break;
                case YEARLY:
                    if (date.getDayOfMonth() == task.getDate().getDayOfMonth()
                            && date.getMonth() == task.getDate().getMonth()
                            && task.getTimeCreation().getYear() <= date.getYear()
                            && task.getTimeCreation().compareTo(date) < 0) {
                        System.out.println(task.getTaskName());
                    }
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println(
                """
                        1. Добавить задачу
                        2. Удалить задачу
                        3. Получить задачу на указанный день
                        4. Печать архива удаленных задач
                        5. Доп.операции
                        0. Выход
                        """
        );
    }

    private static void printAdditionalOperations(Scanner scanner) {
        System.out.println("""
                1. Изменить название задачи
                2. Изменить описание задачи
                3. Распечатать задачи
                0. Вернуться в главное меню
                """);
        System.out.print("Выберите пункт меню: ");
        int num = scanner.nextInt();
        switch (num) {
            case 1:
                changeTaskName(scanner);
                break;
            case 2:
                changeTaskDescription(scanner);
                break;
            case 3:
                Task.print(scanner);
                break;
            case 0:
                openConsole();
            default: {
                System.out.println("Ошибка выбора! Попробуйте снова!");
                printAdditionalOperations(scanner);
            }
        }
    }


    private static LocalDate setSingleDate(Scanner scanner) {
        System.out.print("Когда выполнить задачу? (DD.MM.YYYY): ");
        String str = scanner.next().trim().replace(" ", "");
        scanner.useDelimiter("\n");
        return LocalDate.of(Integer.parseInt(str.substring(6)), Integer.parseInt(str.substring(3, 5)), Integer.parseInt(str.substring(0, 2)));
    }

    private static LocalDate setWeeklyDate(Scanner scanner) {
        System.out.print("""
                1 - Понедельник
                2 - Вторник
                3 - Среда
                4 - Четверг
                5 - Пятница
                6 - Суббота
                7 - Воскресенье
                Укажите день:\s""");
        int day = scanner.nextInt();
        return LocalDate.EPOCH.plusDays(LocalDate.EPOCH.getDayOfWeek().getValue() + day - 1);
    }

    private static LocalDate setMonthlyDate(Scanner scanner) {
        System.out.print("Введите число, когда повторять задачу: ");
        int day = scanner.nextInt();
        return LocalDate.of(1970, 1, day);
    }

    private static LocalDate setYearlyDate(Scanner scanner) {
        System.out.print("Введите дату (DD.MM): ");
        String str = scanner.next().trim().replace(" ", "");
        return LocalDate.of(1970, Integer.parseInt(str.substring(3)), Integer.parseInt(str.substring(0, 2)));
    }

    private static int setTaskRepeatFunc(Scanner scanner) {
        System.out.print("1 - " + TaskRepeatFunc.DAILY + "\n" +
                "2 - " + TaskRepeatFunc.WEEKLY + "\n" +
                "3 - " + TaskRepeatFunc.MONTHLY + "\n" +
                "4 - " + TaskRepeatFunc.YEARLY + "\n" +
                "Выберите один из вариантов: ");
        return scanner.nextInt();
    }

    private static String inputTaskName(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Введите название задачи: ");
        return scanner.next();
    }

    private static String inputDescription(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Введите описание задачи: ");
        return scanner.next();
    }

    private static int inputTypeOfTask(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Тип задачи (1 - личная, 2 - рабочая): ");
        return scanner.nextInt();
    }

    private static String checkRepeatFunc(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Задача повторяемая? (Y/N): ");
        return scanner.next();
    }

    private static void changeTaskName(Scanner scanner) {
        System.out.print("Введите ID задачи: ");
        int id = scanner.nextInt();
        System.out.print("Введите название задачи: ");
        String taskName = scanner.next().trim();

        try {
            Task.listOfTask.getListOfTask().get(id).setTaskName(taskName);
        } catch (EmptyStringException e) {
            System.out.println(e.getMessage());
            openConsole();
        }
    }

    private static void changeTaskDescription(Scanner scanner) {
        System.out.print("Введите ID задачи: ");
        int id = scanner.nextInt();
        System.out.print("Введите описание: ");
        String description = scanner.next().trim();
        try {
            Task.listOfTask.getListOfTask().get(id).setDescription(description);
        } catch (EmptyStringException e) {
            System.out.println(e.getMessage());
            openConsole();
        }
    }
}
