package analyzer.work_manager;

import analyzer.FileType;

import java.io.File;
import java.util.List;

public class Work {

    private final File file;
    private final List<FileType> fileTypes;

    public Work(File file, List<FileType> fileTypes) {
        this.file = file;
        this.fileTypes = fileTypes;
    }


    public Result getResult(FileType fileType) {
        String unknownFileMessage = "Unknown file type";
        return new Result(file, fileType != null ? fileType.getDescription() : unknownFileMessage);
    }

    public File getFile() {
        return file;
    }

    public List<FileType> getFileTypes() {
        return fileTypes;
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
