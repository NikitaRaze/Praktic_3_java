import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

final class TaskFile {
    static List<Task> load(Path file) throws IOException {
        List<Task> tasks = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (NoSuchFileException e) {
            return tasks;
        }
        for (int i = 0; i < lines.size(); i++) {
            try {
                tasks.add(Task.fromLine(lines.get(i)));
            } catch (IllegalArgumentException | DateTimeParseException e) {
                throw new IOException("Ошибка в строке " + (i + 1) + " файла " + file, e);
            }
        }
        return tasks;
    }

    static void save(Path file, List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toLine());
        }
        Files.write(file, lines, StandardCharsets.UTF_8);
    }
}
