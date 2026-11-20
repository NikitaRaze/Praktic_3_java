import java.time.LocalDate;

final class Task {
    final String title;
    final LocalDate due;

    Task(String title, LocalDate due) {
        this.title = title;
        this.due = due;
    }

    String toLine() {
        return (due == null ? "" : due.toString()) + '|' + title;
    }

    static Task fromLine(String line) {
        int split = line.indexOf('|');
        if (split < 0) throw new IllegalArgumentException("Нет разделителя");
        String date = line.substring(0, split);
        LocalDate due = date.isEmpty() ? null : LocalDate.parse(date);
        return new Task(line.substring(split + 1), due);
    }
}
