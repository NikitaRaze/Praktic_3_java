import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path file = Paths.get("tasks.txt");
        try {
            List<Task> tasks = TaskFile.load(file);
            try (Scanner in = new Scanner(System.in, "UTF-8")) {
                run(in, tasks);
            }
            TaskFile.save(file, tasks);
            System.out.println("Список сохранён.");
        } catch (IOException e) {
            System.err.println("Ошибка файла задач: " + e.getMessage());
        }
    }

    private static void run(Scanner in, List<Task> tasks) {
        System.out.println("Менеджер задач");
        while (true) {
            System.out.println("\n1. Добавить задачу");
            System.out.println("2. Показать задачи");
            System.out.println("3. Удалить задачу");
            System.out.println("0. Выход");
            System.out.print("> ");
            if (!in.hasNextLine()) return;
            switch (in.nextLine().trim()) {
                case "1":
                    addTask(in, tasks);
                    break;
                case "2":
                    showTasks(tasks);
                    break;
                case "3":
                    removeTask(in, tasks);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Нет такой команды.");
            }
        }
    }

    private static void addTask(Scanner in, List<Task> tasks) {
        System.out.print("Название: ");
        if (!in.hasNextLine()) return;
        String title = in.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Введите название задачи.");
            return;
        }
        System.out.print("Срок (ГГГГ-ММ-ДД или Enter): ");
        if (!in.hasNextLine()) return;
        String date = in.nextLine().trim();
        try {
            LocalDate due = date.isEmpty() ? null : LocalDate.parse(date);
            tasks.add(new Task(title, due));
            System.out.println("Задача добавлена.");
        } catch (DateTimeParseException e) {
            System.out.println("Неверная дата. Задача не добавлена.");
        }
    }

    private static void showTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String date = task.due == null ? "без срока" : task.due.toString();
            System.out.printf("%d. %s [%s]%n", i + 1, task.title, date);
        }
    }

    private static void removeTask(Scanner in, List<Task> tasks) {
        showTasks(tasks);
        if (tasks.isEmpty()) return;
        System.out.print("Номер задачи: ");
        if (!in.hasNextLine()) return;
        try {
            int number = Integer.parseInt(in.nextLine().trim());
            tasks.remove(number - 1);
            System.out.println("Задача удалена.");
        } catch (NumberFormatException e) {
            System.out.println("Введите целый номер.");
        }
    }
}
