package com.example.javaschedulerapp.model;
import java.time.LocalDate;


public class ItemData implements Comparable<ItemData> {

    private String itemName;

    public ItemData(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public int compareTo(ItemData o) {
        return 0;
    }


//    public int compareTo(ItemData otherTask) {
//        String subTask = taskSchedule.substring(14);
//        String subOtherTask = otherTask.getTaskSchedule().substring(14);
//        LocalDate date = LocalDate.parse(subTask);
//        LocalDate otherDate = LocalDate.parse(subOtherTask);
//        return date.compareTo(otherDate);
//    }
}
