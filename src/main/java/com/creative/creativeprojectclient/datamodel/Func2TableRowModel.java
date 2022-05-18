package com.creative.creativeprojectclient.datamodel;


import javafx.beans.property.StringProperty;

public class Func2TableRowModel {
    private StringProperty apartmentName;
    private StringProperty area;


    public Func2TableRowModel(StringProperty apartmentName, StringProperty area) {
        this.apartmentName = apartmentName;
        this.area = area;

    }

    public StringProperty apartmentNameProperty(){
        return apartmentName;
    }

    public StringProperty areaProperty(){
        return area;
    }

}
