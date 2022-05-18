package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SelectApartRegion  implements Serializable {

    private String sigunguName;
    private String SigunguRegionCode;
    private String eupmyeondongName;

}
