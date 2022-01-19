package analyzer.searcher;

public class SearcherFactory {
    String fileDescription;

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public Searcher newInstance(String type) {
        switch (type) {
            case "--naive":
                return new ExecutionTimeDecorator(new NaiveSearcher(), fileDescription);
            case "--KMP":
                return new ExecutionTimeDecorator(new KnuthMorrisPratt(), fileDescription);
            default:
                return null;
        }
    }
}
