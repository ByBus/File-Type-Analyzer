package analyzer;

public class FileType implements Comparable<FileType> {
    private final int priority;
    private final String pattern;
    private final String fileDescription;

    public FileType(int priority, String pattern, String fileDescription) {
        this.priority = priority;
        this.pattern = pattern;
        this.fileDescription = fileDescription;
    }

    @Override
    public int compareTo(FileType fileType) {
        return Integer.compare(priority, fileType.priority);
    }

    public String getPattern() {
        return pattern;
    }

    public String getDescription() {
        return fileDescription;
    }

    @Override
    public String toString() {
        return fileDescription + " " + pattern;
    }
}
