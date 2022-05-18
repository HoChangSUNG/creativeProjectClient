package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class ApartmentInfo1 implements Serializable {
    private String apartmentName;
    private float area;
}
