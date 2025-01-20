package pages;

public enum Fields{
    FULL_NAME ("Full Name","userName"),
    EMAIL ("Email", "email"),
    CURRENT_ADDRESS("Current Address","currentAddress");
//    PERMANENT_ADDRESS("Permanent Address","permanentAddress");

    private String field;
    private String locator;

    Fields(String field, String locator) {
        this.field = field;
        this.locator = locator;
    }

    public String getLocator(){
        return this.locator;
    }

    public String getField(){
        return this.field;
    }

    @Override
    public String toString() {
        return field;
    }
}
