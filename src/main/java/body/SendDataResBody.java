package body;

import domain.Apartment;
import domain.ApartmentInfo1;
import domain.AverageData;
import domain.FluctuationRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendDataResBody implements Serializable {
    private List<AverageData> averageDataList;
    private  List<FluctuationRate> fluctuationLateList;
}
