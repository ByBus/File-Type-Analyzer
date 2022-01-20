package analyzer.work_manager;

import java.io.File;

public class Work {

    private final File file;
    private final String pattern;
    private final String correctFileMessage;


    public Work(File file, String pattern, String correctFileMessage) {
        this.file = file;
        this.pattern = pattern;
        this.correctFileMessage = correctFileMessage;
    }

    public Result getResult(boolean isCorrect) {
        String unknownFileMessage = "Unknown file type";
        return new Result(file, isCorrect ? correctFileMessage : unknownFileMessage);
    }

    public File getFile() {
        return file;
    }

    public String getPattern() {
        return pattern;
    }

    public static class Result {
        private final String fileName;
        private final String message;

        public Result(File file, String message) {
            this.fileName = file.getName();
            this.message = message;
        }

        @Override
        public String toString() {
            return fileName + ": " + message;
        }
    }
}
