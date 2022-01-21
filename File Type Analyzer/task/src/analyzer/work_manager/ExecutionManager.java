package analyzer.work_manager;

import analyzer.searcher.Searcher;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ExecutionManager {
    private final Searcher searcher;
    private final int poolSize = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executor = Executors.newFixedThreadPool(poolSize);

    public ExecutionManager(Searcher searcher) {
        this.searcher = searcher;
    }

    public List<Work.Result> makeWork(List<Work> tasks) {
        List<Worker> workers = attachWorkToWorkers(tasks);
        try {
            List<Future<Work.Result>> futures = executor.invokeAll(workers);
            return futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (InterruptedException e) {
            return Collections.emptyList();
        } finally {
            executor.shutdown();
        }
    }

    private List<Worker> attachWorkToWorkers(List<Work> preparedTasks) {
        return preparedTasks.stream()
                .map(work -> new Worker(work, searcher))
                .collect(Collectors.toList());
    }
}
