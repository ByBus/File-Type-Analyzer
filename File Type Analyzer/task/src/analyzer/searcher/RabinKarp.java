package analyzer.searcher;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp implements Searcher{

    private List<List<Integer>> searchWithRabinKarp(byte[] text, byte[] pattern) {
        List<List<Integer>> founds = new ArrayList<>();
        if (text.length < pattern.length) {
            return founds;
        }
        int PRIME = 101;
        int CONST = 256;
        int tLen = text.length;
        int pLen = pattern.length;
        int tHash = 0; // text substring Hash
        int pHash = 0; // pattern Hash
        int powerLastChar = 1;
        for (int i = 0; i < pLen - 1; i++) {
            powerLastChar = (powerLastChar * CONST) % PRIME;
        }
        //Calculate initial hashes
        for (int m = 0; m < pLen; m++) {
            pHash = (CONST * pHash + pattern[m]) % PRIME; //sum(c[0]*CONST^4..c[4]*CONST^0) - exponent grows RTL
            tHash = (CONST * tHash + text[m]) % PRIME;
        }
        for (int i = 0; i <= tLen - pLen; i++) {
            if (pHash == tHash) {
                boolean isMatch = true;
                for (int j = 0; j < pLen; j++) {
                    if (text[i + j] != pattern[j]) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    founds.add(List.of(i, i + pLen));
                    return founds;
                }
            }
            // rollHash
            if (i < tLen - pLen) {
                tHash = ((tHash - text[i] * powerLastChar) * CONST + text[i + pLen]) ;
                tHash = Math.floorMod(tHash, PRIME);
            }
        }
        return founds;
    }

    @Override
    public boolean isFileTypeCorrect(byte[] content, byte[] pattern) {
       return !searchWithRabinKarp(content, pattern).isEmpty();
    }
}
