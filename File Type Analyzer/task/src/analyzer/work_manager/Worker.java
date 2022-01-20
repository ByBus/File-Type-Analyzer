package analyzer.work_manager;

import analyzer.FileType;
import analyzer.searcher.Searcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Worker implements Callable<Work.Result> {

    private final Searcher searcher;
    private final Work work;

    public Worker(Work work, Searcher searcher) {
        this.searcher = searcher;
        this.work = work;
    }

    @Override
    public Work.Result call() {
        byte[] fileBytes = readBytes(work.getFile());
        List<FileType> filesMatched = work.getFileTypes().stream()
                .filter(ft -> searcher.isFileTypeCorrect(fileBytes, ft.getPattern().getBytes()))
                .collect(Collectors.toList());
        FileType typeWithHighPriority = filesMatched.isEmpty() ? null : Collections.max(filesMatched);
        return work.getResult(typeWithHighPriority);
    }

    private static byte[] readBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
