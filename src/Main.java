import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        try (Scanner in = new Scanner(System.in, "UTF-8")) {
            run(in, tasks);
        }
    }

    private static void run(Scanner in, List<Task> tasks) {
        System.out.println("Менеджер задач");
        while (true) {
            System.out.println("\n1. Добавить задачу");
            System.out.println("0. Выход");
            System.out.print("> ");
            if (!in.hasNextLine()) return;
            switch (in.nextLine().trim()) {
                case "1":
                    addTask(in, tasks);
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
}
