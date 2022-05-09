package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Population {

    private String regionalCode;
    private String regionName;
    private int population;
}
