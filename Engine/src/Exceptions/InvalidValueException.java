package Exceptions;

public class InvalidValueException extends Exception {
    String invalidValues;
    String objectType;
    String errorReason;


    public InvalidValueException(String invalidValues, String objectType, String reason){
        this.invalidValues = invalidValues;
        this.objectType = objectType;
        this.errorReason = reason;
    }

    @Override
    public String getMessage() {
        return "Invalid" + objectType + "because of" + errorReason + "is:" + invalidValues;
    }
}
