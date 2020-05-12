package com.example.androidapp.APIAccess;

import java.util.ArrayList;
import java.util.List;

public class ItemsResponse {
    private boolean resultsTruncated;
    private List<Item> items;
    private String version;
    private String status;


    public boolean getResultsTruncated() {
        return resultsTruncated;
    }



    public List<Item> getItems() {
        return items;
    }

    public List<String> getItemsLabels() {
        List<String> itemLabels = new ArrayList<>();
        for(int i = 0; i < items.size();i++){
            items.get(i).getLabel();
            itemLabels.add(items.get(i).getLabel());
        }
        return itemLabels;
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }

    public void setResultsTruncated(boolean resultsTruncated) {
        this.resultsTruncated = resultsTruncated;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
