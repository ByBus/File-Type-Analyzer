package analyzer.searcher;

public interface Searcher {
    boolean isFileTypeCorrect(byte[] content, byte[] pattern);
}
