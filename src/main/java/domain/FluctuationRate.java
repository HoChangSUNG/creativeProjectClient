package domain;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FluctuationRate implements Serializable,Comparable<FluctuationRate> {
    private String regionName;
    private String regionalCode;
    private float fluctuationLateData;
    private int population;
    private int price;

    public FluctuationRate(String regionalCode, float fluctuationLateData, int price) {
        this.regionalCode = regionalCode;
        this.fluctuationLateData = fluctuationLateData;
        this.price = price;
    }

    @Override
    public int compareTo(FluctuationRate o) {
        if(this.fluctuationLateData>o.fluctuationLateData){
            return -1;
        }
        if(this.fluctuationLateData==o.fluctuationLateData){
            if(price>o.price){
                return -1;
            }
            else if(price==o.price){
                return 0;
            }
            else{
                return 1;
            }
        }
        return 1;
    }
}
