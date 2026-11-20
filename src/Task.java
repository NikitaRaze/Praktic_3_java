final class Task {
    final String title;

    Task(String title) {
        this.title = title;
    }

    String toLine() {
        return '\t' + title;
    }

    static Task fromLine(String line) {
        return new Task(line.substring(line.indexOf('\t') + 1));
    }
}
