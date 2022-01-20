package analyzer;

import analyzer.searcher.Searcher;
import analyzer.searcher.SearcherFactory;
import analyzer.work_manager.ExecutionManager;
import analyzer.work_manager.Work;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String searchAlgorithm = "--KMP";
        String directoryToCheckFiles = args[0];
        String pattern = args[1];
        String fileDescription = args[2];

        List<File> files = listOfAllFiles(directoryToCheckFiles);
        List<Work> preparedTasks = createWorksFromFiles(files, pattern, fileDescription);

        SearcherFactory searcherFactory = new SearcherFactory();
        Searcher searcher = searcherFactory.newInstance(searchAlgorithm);

        ExecutionManager executionManager = new ExecutionManager(searcher);
        List<Work.Result> results = executionManager.makeWork(preparedTasks);
        results.forEach(System.out::println);
    }

    private static List<Work> createWorksFromFiles(List<File> files,
                                                   String pattern,
                                                   String fileDescription) {
        return files.stream()
                .map(file -> new Work(file, pattern, fileDescription))
                .collect(Collectors.toList());
    }

    public static List<File> listOfAllFiles(String rootPath){
        try {
            return Files.walk(Paths.get(rootPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
           return Collections.emptyList();
        }
    }
}
