package analyzer.work_manager;

import analyzer.searcher.Searcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

public class Worker implements Callable<Work.Result> {

    private final Searcher searcher;
    private final Work work;

    public Worker(Work work, Searcher searcher) {
        this.searcher = searcher;
        this.work = work;
    }

    @Override
    public Work.Result call() {
        boolean result = searcher.isFileTypeCorrect(readBytes(work.getFile()), work.getPattern().getBytes());
        return work.getResult(result);
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
