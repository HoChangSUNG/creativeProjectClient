package domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor

public class ApartmentInfo3 implements Serializable{

    private int dealYear;
    private int dealMonth;
    private String apartmentName;
    private float area;
    private float averagePrice;
    private int tradingVolume;
}
