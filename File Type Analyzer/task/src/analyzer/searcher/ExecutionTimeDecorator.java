package analyzer.searcher;

public class ExecutionTimeDecorator implements Searcher{

    private final Searcher searcher;
    private String fileTypeDescription;

    public ExecutionTimeDecorator(Searcher searcher, String fileTypeDescription) {
        this.searcher = searcher;
        this.fileTypeDescription = fileTypeDescription;
    }

    @Override
    public boolean isFileTypeCorrect(byte[] content, byte[] pattern) {
        long startTime = System.currentTimeMillis();

        boolean result = searcher.isFileTypeCorrect(content, pattern);
        System.out.println(!result ? "Unknown file type" : fileTypeDescription);

        long elapsedMillis = System.currentTimeMillis() - startTime;
        double seconds = elapsedMillis / 1000.0;
        System.out.printf("It took %.3f seconds%n", seconds);
        return result;
    }

}
