package analyzer.searcher;

public class SearcherFactory {

    public Searcher newInstance(String type) {
        switch (type) {
            case "--naive":
                return new NaiveSearcher();
            case "--KMP":
                return new KnuthMorrisPratt();
            default:
                return null;
        }
    }
}
