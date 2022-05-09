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
    private float fluctuationRateData;
    private int population;
    private int fluctuationPrice;
    private int averagePrice;

    public FluctuationRate(String regionalCode, float fluctuationLateData, int fluctuationPrice, int averagePrice) {
        this.regionalCode = regionalCode;
        this.fluctuationRateData = fluctuationLateData;
        this.fluctuationPrice = fluctuationPrice;
        this.averagePrice = averagePrice;
    }

    @Override
    public int compareTo(FluctuationRate o) {
        if(this.fluctuationRateData >o.fluctuationRateData){
            return -1;
        }
        if(this.fluctuationRateData ==o.fluctuationRateData){
            if(fluctuationPrice >o.fluctuationPrice){
                return -1;
            }
            else if(fluctuationPrice ==o.fluctuationPrice){
                return 0;
            }
            else{
                return 1;
            }
        }
        return 1;
    }
}
