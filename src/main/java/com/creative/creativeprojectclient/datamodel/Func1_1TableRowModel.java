package com.creative.creativeprojectclient.datamodel;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Func1_1TableRowModel {
    private StringProperty regionName;
    private StringProperty fluctuationRate;//변동률
    private StringProperty avgPrice;
    private StringProperty population;
    private StringProperty priceCnt;

    public Func1_1TableRowModel(StringProperty regionName, StringProperty fluctuationRate, StringProperty avgPrice, StringProperty population, StringProperty priceCnt) {
        this.regionName = regionName;
        this.fluctuationRate = fluctuationRate;
        this.avgPrice = avgPrice;
        this.population = population;
        this.priceCnt = priceCnt;
    }

    public StringProperty regionNameProperty(){
        return regionName;
    }

    public StringProperty fluctuationRateProperty(){
        return fluctuationRate;
    }

    public StringProperty avgPriceProperty(){
        return avgPrice;
    }

    public StringProperty populationProperty(){
        return population;
    }

    public StringProperty priceCntProperty() {
        return priceCnt;
    }

}
