package pages;

import java.util.Arrays;

public enum Fields{
    FULL_NAME ("Full Name","userName"),
    EMAIL ("Email", "userEmail"),
    CURRENT_ADDRESS("Current Address","currentAddress"),
    PERMANENT_ADDRESS("Permanent Address","permanentAddress"),
    FULL_NAME1("name","name"),
    EMAIL1("email","email"),
    FIRST_NAME("firstName","firstName"),
    LAST_NAME("lastName","lastName"),
    GENDER("Gender","gender"),
    MOBILE_NUMBER("Mobile Number","userNumber"),
    DOB("Date of Birth","dateOfBirthInput"),
    HOBBIES("Hobbies","hobbiesWrapper"),
    ADDRESS("Address","currentAddress");


    private String field;
    private String locator;

    Fields(String field, String locator) {
        this.field = field;
        this.locator = locator;
    }

    public static Fields getLocatorByFieldName(String field){
        return Arrays.stream(Fields.values()).filter(el -> el.getField().equals(field)).findFirst().get();
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
