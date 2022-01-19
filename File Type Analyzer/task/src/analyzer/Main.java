package analyzer;

import analyzer.searcher.Searcher;
import analyzer.searcher.SearcherFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws IOException {
        String searchAlg = args[0];
        String filepath = args[1];
        String pattern = args[2];
        String fileDescription = args[3];

        byte[] content = readBytes(filepath);
        byte[] patternBytes = pattern.getBytes();

        SearcherFactory searcherFactory = new SearcherFactory();
        searcherFactory.setFileDescription(fileDescription);
        Searcher searcher = searcherFactory.newInstance(searchAlg);
        searcher.isFileTypeCorrect(content, patternBytes);
    }

    private static byte[] readBytes(String filePath) throws IOException {
        //copyTestFile(filePath);
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyTestFile(String fileName) throws IOException {
        File source = new File(fileName);
        File dest = new File("G:\\" + source.getName());
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
