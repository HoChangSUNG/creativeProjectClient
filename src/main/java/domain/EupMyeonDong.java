package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EupMyeonDong implements Serializable {

    private String regionCode;
    private String regionName;
}
