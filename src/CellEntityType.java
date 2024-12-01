public enum CellEntityType {
    PLAYER("P"),
    VOID("N"),
    ENEMY("E"),
    SANCTUARY("S"),
    PORTAL("F");

    private final String symbol;

    CellEntityType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
