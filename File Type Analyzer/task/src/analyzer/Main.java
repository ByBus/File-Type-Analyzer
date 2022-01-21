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
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String searchAlgorithm = "--RBK";
        String directoryToCheckFiles = args[0];
        String fileOfPatterns = args[1];

        List<FileType> fileTypes = getFileTypesFromFile(fileOfPatterns);
        List<File> files = listOfAllFiles(directoryToCheckFiles);

        List<Work> preparedTasks = createWorksFromFiles(files, fileTypes);

        SearcherFactory searcherFactory = new SearcherFactory();
        Searcher searcher = searcherFactory.newInstance(searchAlgorithm);

        ExecutionManager executionManager = new ExecutionManager(searcher);
        List<Work.Result> results = executionManager.makeWork(preparedTasks);
        results.forEach(System.out::println);
    }

    private static List<Work> createWorksFromFiles(List<File> files, List<FileType> fileTypes) {
        return files.stream()
                .map(file -> new Work(file, fileTypes))
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

    private static List<FileType> getFileTypesFromFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.map(line -> line.replace("\"", "").split(";"))
                    .map(p -> new FileType(Integer.parseInt(p[0]), p[1], p[2]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
