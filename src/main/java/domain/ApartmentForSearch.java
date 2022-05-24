package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ApartmentForSearch implements Serializable {
    private String regionalCode;
    private String regionName;
}
