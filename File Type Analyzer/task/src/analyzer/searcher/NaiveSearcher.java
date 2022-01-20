package analyzer.searcher;

import java.util.Arrays;
import java.util.stream.IntStream;

public class NaiveSearcher implements Searcher{
    @Override
    public boolean isFileTypeCorrect(byte[] content, byte[] pattern) {
            return IntStream.range(0, content.length - pattern.length + 1)
                    .mapToObj(i -> Arrays.copyOfRange(content, i, i + pattern.length))
                    .anyMatch(subContent -> Arrays.equals(subContent, pattern));
    }
}
