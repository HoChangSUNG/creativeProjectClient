package body;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SelectRegionGraphData implements Serializable {

    private String sidoName;
    private String sigunguName;

}
