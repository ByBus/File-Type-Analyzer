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

//    @Override
//    public boolean isFileTypeCorrect(byte[] content, byte[] pattern) {
//        int bound = content.length - pattern.length;
//        for (int i = 0; i <= bound; i++) {
//            int matches = 0;
//            for (int j = 0; j < pattern.length; j++) {
//                if (Objects.equals(content[i + j], pattern[j])) {
//                    matches++;
//                } else {
//                    break;
//                }
//            }
//            if (matches == pattern.length) {
//                return true;
//            }
//        }
//        return false;
//    }
}
