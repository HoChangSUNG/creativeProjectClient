package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RowHouse {

    private int id;
    private int dealAmount;
    private int buildYear;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String regionName;
    private String rowhouseName;
    private float area;
    private int floor;
    private String jibun;
    private String regionalCode;
}
