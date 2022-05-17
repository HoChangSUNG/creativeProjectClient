package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sido implements Serializable {

    private String regionalCode;
    private String regionName;
    private List<Sigungu> sigunguList;

    public Sido(String regionalCode, String regionName) {
        this.regionalCode = regionalCode;
        this.regionName = regionName;
    }
}
