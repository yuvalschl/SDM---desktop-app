package Exceptions;

import jaxb.JaxbClasses.SDMItem;
import jaxb.JaxbClasses.SDMStore;

import java.util.HashSet;
import java.util.Set;

public class DuplicateValueException extends Exception {
    private final String duplicateItemNames;
    private final String duplicateItemIds;
    private final String duplicateStoresId;
    private final String duplicateStoresName;

    public DuplicateValueException(String duplicateItemNames, String duplicateItemIds, String duplicateStoresId, String duplicateStoresName){
        this.duplicateItemIds = duplicateItemIds;
        this.duplicateItemNames = duplicateItemNames;
        this.duplicateStoresId = duplicateStoresId;
        this.duplicateStoresName = duplicateStoresName;
    }

    @Override
    public String getMessage() {
        String message = "";
        if(!duplicateStoresName.isEmpty()){
            message = message +"duplicated store names are:" + duplicateStoresName + System.lineSeparator();
        }
        if(!duplicateStoresId.isEmpty()){
            message = message +"duplicated store Ids are:" + duplicateStoresId + System.lineSeparator();
        }
        if(!duplicateItemNames.isEmpty()){
            message = message +"duplicated item name are:" + duplicateStoresName + System.lineSeparator();
        }
        if(!duplicateItemIds.isEmpty()){
            message = message +"duplicated Item ids are:" + duplicateItemIds + System.lineSeparator();
        }

        return message;
    }
}
