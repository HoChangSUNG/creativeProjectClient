package body;

import domain.*;
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
    private List<ApartmentInfo3> apartmentInfoList;
    private List<FluctuationRate> fluctuationLateList;
    private List<AverageAreaAmoumtApartmentData> averageAreaAmoumtApartmentList;
    private List<ApartmentInfo1> apartmentList;
}
