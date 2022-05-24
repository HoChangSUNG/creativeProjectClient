package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AverageAreaAmoumtApartmentData implements Serializable {
    private String dealYear;
    private String dealMonth;
    private double averageAreaAmoumt;
    private int averageCnt;
}
