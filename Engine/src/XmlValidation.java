import jaxb.JaxbClasses.SDMItem;
import jaxb.JaxbClasses.SDMSell;
import jaxb.JaxbClasses.SDMStore;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;

import java.util.*;

public class XmlValidation {
    private HashSet<SDMStore> duplicateStoresId;
    private HashSet<SDMStore> duplicateStoresName;
    private HashMap<SDMSell, SDMStore> invalidItemsPrices;
    private HashSet<SDMItem> duplicateItemNames;
    private HashSet<SDMItem> duplicateItemIds;
    private HashSet<SDMStore> invalidStoresLocations;
    private HashSet<SDMStore> invalidStoresPPK;

/*    public static void main(String[] args) {
        XmlValidation tt = new XmlValidation();
        List<String> test = new ArrayList<>();
        test.add("yuval");
        test.add("dani");
        test.add("tamir");
        test.add("yuval");
        HashSet<String> res = (HashSet<String>) tt.checkForDuplicate(test);
    }*/

    public void validateXmlFile(SuperDuperMarketDescriptor xmlInput){
        List<SDMStore> allStores = xmlInput.getSDMStores().getSDMStore();
        List<String> itemsNames = getItemsName(xmlInput.getSDMItems().getSDMItem());
        List<Integer> itemsIds = getItemsIds(xmlInput.getSDMItems().getSDMItem());
        duplicateItemNames = (HashSet<SDMItem>) checkForDuplicate(itemsNames);
        duplicateItemIds = (HashSet<SDMItem>) checkForDuplicate(itemsIds);
        checkStores(allStores);

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

/*    public Set<SDMItem> checkForDuplicateItemNameInXml(List<SDMItem> allItems){
        Set<SDMItem> duplicates = new HashSet<SDMItem>();
        Set<String> uniques = new HashSet<String>();
        for(SDMItem item : allItems){
            if(!uniques.add(item.getName())){
                duplicates.add(item);
            }
        }
        return duplicates;
    }

    public Set<SDMItem> checkForDuplicateItemIdInXml(List<SDMItem> allItems){
        Set<SDMItem> duplicates = new HashSet<SDMItem>();
        Set<Integer> uniques = new HashSet<Integer>();
        for(SDMItem item : allItems){
            if(!uniques.add(item.getId())){
                duplicates.add(item);
            }
        }
        return duplicates;
    }*/

}
