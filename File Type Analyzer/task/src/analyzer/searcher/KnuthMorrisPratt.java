package analyzer.searcher;

import java.util.ArrayList;
import java.util.List;

public class KnuthMorrisPratt implements Searcher{

    private int[] prefixFunction(byte[] pattern) {
        int[] prefix = new int[pattern.length];
        for (int i = 1; i < pattern.length; i++) {
            int previousLongestBorderLen = prefix[i - 1]; // p[9-1] = p[8] = 4 Same as index of next char after border
            byte suffixLastByte = pattern[previousLongestBorderLen];
            byte postfixLastByte = pattern[i];
            if (suffixLastByte == postfixLastByte) {
                prefix[i] = prefix[i - 1] + 1;
            } else {
                int indexFromLength = Math.max(0, previousLongestBorderLen - 1); // 4 - 1 = 3
                int maxLength = prefix[indexFromLength]; // p[3] = 1 Same as next index after that length
                prefix[i] = pattern[maxLength] == pattern[i] ? prefix[indexFromLength] + 1 : 0;
            }
        }
        return prefix;
    }

    private List<List<Integer>> findWithKnuthMorrisPratt(byte[] content, byte[] pattern) {
        int[] prefix = prefixFunction(pattern);
        List<List<Integer>> founds = new ArrayList<>();
        int shift = 0;
        int border = 0;
        for (int i = 0; i <= content.length - pattern.length; i += Math.max(1, shift)) {
            int matches = border;
            int indexOfLastChar = 0;
            for (int j = border; j < pattern.length; j++) {
                indexOfLastChar = i + j;
                if (pattern[j] == content[indexOfLastChar]) {
                    matches++;
                } else {
                    border = prefix[Math.max(0, matches - 1)];
                    shift = matches - border;
                    break;
                }
            }
            if (matches == pattern.length) {
                founds.add(List.of(indexOfLastChar - (pattern.length - 1), indexOfLastChar));
                return founds;
            }
        }
        return founds;
    }

    @Override
    public boolean isFileTypeCorrect(byte[] content, byte[] pattern) {
        return !findWithKnuthMorrisPratt(content, pattern).isEmpty();
    }
}
