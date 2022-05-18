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
public class Sigungu implements Serializable {

    private String regionalCode;
    private String regionName;
    private int eupMyeonDongIndex = 0;
    private List<EupMyeonDong> eupMyeonDongList;

    public Sigungu(String regionalCode, String regionName) {
        this.regionalCode = regionalCode;
        this.regionName = regionName;
    }
}
