package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor

public class ApartmentInfo2 implements Serializable{
    private String regionalCode = "";
    private String regionName = "";
    private String apartmentName = "";
    private float area = 0;
}
