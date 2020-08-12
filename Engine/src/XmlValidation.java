import Exceptions.*;
import jaxb.JaxbClasses.*;

import java.util.*;
import java.util.stream.Collectors;

public class XmlValidation {
    private HashSet<SDMStore> duplicateStoresId;
    private HashSet<SDMStore> duplicateStoresName;
    private HashSet<SDMItem> duplicateItemNames;
    private HashSet<SDMItem> duplicateItemIds;
    private HashMap<SDMSell, SDMStore> invalidItemsPrices;
    private HashSet<SDMStore> invalidStoresLocations;
    private HashSet<SDMStore> invalidStoresPPK;

    public XmlValidation(SuperDuperMarketDescriptor xmlInput) {
        invalidStoresLocations = new HashSet<>();
        invalidStoresPPK = new HashSet<>();
        List<SDMStore> allStores = xmlInput.getSDMStores().getSDMStore();
        List<String> itemsNames = getItemsName(xmlInput.getSDMItems().getSDMItem());
        List<Integer> itemsIds = getItemsIds(xmlInput.getSDMItems().getSDMItem());
        duplicateItemNames = (HashSet<SDMItem>) checkForDuplicate(itemsNames);
        duplicateItemIds = (HashSet<SDMItem>) checkForDuplicate(itemsIds);
        checkStores(allStores);

    }

    public boolean validateXml() throws DuplicateValueException, InvalidValueException {
        boolean isValid = true;
        if(checkDuplicatesInXmlFile()){
            throw new DuplicateValueException(setToString(duplicateItemNames),setToString(duplicateItemIds),setToString(duplicateStoresId),setToString(duplicateStoresName));
            isValid = false;
        }
        if(!invalidItemsPrices.isEmpty()){
            throw new InvalidValueException(mapToString(invalidItemsPrices), "Item", "price");
            isValid = false;
        }
        if(!invalidStoresLocations.isEmpty()){
            throw new InvalidValueException(setToString(invalidStoresLocations), "store", "location");
            isValid = false;
        }
        
    }

    private String mapToString (Map<?,?> map){
        return map.entrySet()
                .stream().map(key -> key.getKey() + ":" + key.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }

    private String setToString(Set<?> set){
        return set.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    private boolean checkDuplicatesInXmlFile(){
        return duplicateStoresId.isEmpty() && duplicateStoresName.isEmpty() && duplicateItemNames.isEmpty() && duplicateItemIds.isEmpty();
    }

    private void checkStores(List<SDMStore> allStores){
        List<Integer> storesId = getStoresId(allStores);
        List<String> storesNames = getStoresName(allStores);

        for(SDMStore store : allStores){
            checkLocation(store);
            checkPPK(store);
        }

        duplicateStoresId = (HashSet<SDMStore>) checkForDuplicate(storesId);
        duplicateStoresName = (HashSet<SDMStore>) checkForDuplicate(storesNames);
        invalidItemsPrices = (HashMap<SDMSell, SDMStore>) validateItemsPrice(allStores);
    }

    public Set<? extends Object> checkForDuplicate(List<? extends Object> checkDup){
        Set<Object> duplicates = new HashSet<>();
        Set<Object> uniques = new HashSet<>();
        for(Object obj : checkDup){
            if(!uniques.add(obj)){
                duplicates.add(obj);
            }
        }

        return duplicates;
    }

    public Map<SDMSell, SDMStore> validateItemsPrice(List<SDMStore> allStores){
        Map<SDMSell, SDMStore> invalidItems = new HashMap<>();

        for(SDMStore store : allStores){
            for(SDMSell item : store.getSDMPrices().getSDMSell()){
                if(item.getPrice() < 0) {
                    invalidItems.put(item, store);
                }
            }
        }

        return invalidItems;
    }

    private void checkLocation(SDMStore store){
        int x = store.getLocation().getX();
        int y = store.getLocation().getY();
        if(x < 1 || x > 50 | y < 0 || y > 50){
            invalidStoresLocations.add(store);
        }
    }

    private void checkPPK(SDMStore store){
        if(store.getDeliveryPpk() < 0){
            invalidStoresPPK.add(store);
        }
    }

    private List<Integer> getStoresId(List<SDMStore> allStores){
        List<Integer> allIds = new ArrayList<>();
        for(SDMStore store : allStores){
            allIds.add(store.getId());
        }

        return allIds;
    }

    private List<String> getStoresName(List<SDMStore> allStores){
        List<String> allNames = new ArrayList<>();
        for(SDMStore store : allStores){
            allNames.add(store.getName());
        }

        return allNames;
    }

    private List<String> getItemsName(List<SDMItem> allItems){
        List<String> allNames = new ArrayList<>();
        for(SDMItem item : allItems){
            allNames.add(item.getName());
        }

        return allNames;
    }

    private List<Integer> getItemsIds(List<SDMItem> allItems){
        List<Integer> allNames = new ArrayList<>();
        for(SDMItem item : allItems){
            allNames.add(item.getId());
        }

        return allNames;
    }

}
