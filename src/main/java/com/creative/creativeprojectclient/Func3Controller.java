package com.creative.creativeprojectclient;

import body.SendDataResBody;
import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import network.Packet;
import network.ProtocolType;
import network.protocolCode.RealEstateRecommendCode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.WARNING;

public class Func3Controller implements Initializable {
    @FXML
    AnchorPane panel;
    @FXML
    Button findBtn;
    @FXML
    ComboBox<String> sidoCombo;
    @FXML
    ComboBox<String> sigunguCombo;
    @FXML
    ComboBox<String> eupMyeonDongCombo;
    @FXML
    LineChart lineChart;
    @FXML
    BarChart barChart;
    @FXML
    Label resultDate; //거래 날짜
    @FXML
    Label resultAvgPrice; // 거래 평균 가격
    @FXML
    Label resultVolume; // 거래량

    private List<Sido> regionSelectList;
    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        initRegionSelectCombo();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void initRegionSelectCombo(){// 시도 콤보 박스 초기화
        regionSelectList = mainController.getRegionSelectList();// 지역 리스트 초기화

        sidoCombo.setPromptText("시/도");
        sigunguCombo.setPromptText("시/군/구");
        eupMyeonDongCombo.setPromptText("읍/면/동");

        List<String> sidoNameList = regionSelectList.stream()
                .map(sido -> sido.getRegionName())
                .collect(Collectors.toList()); // 시도 이름 리스트 반환

        ObservableList<String> sidos = FXCollections.observableArrayList(sidoNameList);
        sidoCombo.setItems(sidos); // 시도 콤보 박스 초기화

        setComboBoxPrompt(sidoCombo);
        setComboBoxPrompt(sigunguCombo);
        setComboBoxPrompt(eupMyeonDongCombo);
    }
    private void setComboBoxPrompt(ComboBox<String> comboBox) {
        comboBox.setButtonCell(new ListCell<>() { // Combo box 초기화 시 promptText 강제로 보여주기.
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(comboBox.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
    }

    public void sidoComboChange() {//시도 콤보박스 선택시
        int selectedSidoIndex = sidoCombo.getSelectionModel().getSelectedIndex();// 선택한 시도 콤보 인덱스
        List<Sigungu> sigunguList = regionSelectList.get(selectedSidoIndex).getSigunguList();// 선택한 시도에 있는 시군구 리스트 반환

        List<String> sigunguNameList = sigunguList.stream()
                .map(sigungu -> sigungu.getRegionName())
                .collect(Collectors.toList());// 선택한 시도에 있는 시군구 이름 리스트 반환

        ObservableList<String> sigungus = FXCollections.observableArrayList(sigunguNameList);

        sigunguCombo.setItems(sigungus); //시도에 맞는 시군구 콤보 박스 설정

        eupMyeonDongCombo.getSelectionModel().clearSelection(); // 읍면동 콤보 박스 초기화
        eupMyeonDongCombo.getItems().clear();


    }

    public void sigungoComboChange(){//시군구 콤보박스 선택시
        int sidoIndex = sidoCombo.getSelectionModel().getSelectedIndex(); //선택한 시도 콤보 인덱스
        int sigunguIndex = sigunguCombo.getSelectionModel().getSelectedIndex(); //선택한 시군고 콤보 인덱스

        if(sigunguIndex==-1)
            return;

        List<EupMyeonDong> eupMyeonDongList = regionSelectList.get(sidoIndex).getSigunguList().get(sigunguIndex).getEupMyeonDongList(); //선택한 시군구에 있는 읍면동 리스트 반환

        List<String> eupMyeonDongNameList = eupMyeonDongList.stream()
                .map(eupMyeonDong -> eupMyeonDong.getRegionName())
                .collect(Collectors.toList());// 선택한 시군구에 있는 시군구 이름 리스트 반환

        ObservableList<String> eupMyeonDongs = FXCollections.observableArrayList(eupMyeonDongNameList);

        eupMyeonDongCombo.setItems(eupMyeonDongs);

    }
    public void moveToFunc1Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func1Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void moveToFunc2Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func2.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func2Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void moveToFunc3Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func3.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func3Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void findApart() { // 찾기 버튼 클릭시
        Packet receivePacket = null;
        int sidoIndex = sidoCombo.getSelectionModel().getSelectedIndex();
        int sigunguIndex = sigunguCombo.getSelectionModel().getSelectedIndex();
        int eupMyeonDongIndex = eupMyeonDongCombo.getSelectionModel().getSelectedIndex();

        if (sidoIndex < 0 || sigunguIndex < 0 || eupMyeonDongIndex < 0) { // 선택되지 않은 콤보 박스가 있을 경우
            alert(sidoIndex, sigunguIndex, eupMyeonDongIndex);
            return;
        }
        Sigungu sigungu = regionSelectList.get(sidoIndex).getSigunguList().get(sigunguIndex);
        EupMyeonDong eupMyeonDong = sigungu.getEupMyeonDongList().get(eupMyeonDongIndex);

        ApartmentForSearch apartmentForSearch = new ApartmentForSearch(sigungu.getRegionalCode(), eupMyeonDong.getRegionName());
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_RECOMMEND.getType());
        packet.setProtocolCode(RealEstateRecommendCode.RECORD_INFO_REQ.getCode());

        packet.setBody(apartmentForSearch);

        mainController.writePacket(packet);

        receivePacket = mainController.readPacket();
        SendDataResBody sendDataResBody = (SendDataResBody) receivePacket.getBody();
        List<AverageAreaAmoumtApartmentData> averageAreaAmoumtApartmentData = sendDataResBody.getAverageAreaAmoumtApartmentList();

        lineChart.getData().clear();
        barChart.getData().clear();

        XYChart.Series<String, Double> seriesline = new XYChart.Series<String, Double>();
        XYChart.Series<String, Integer> seriesbar = new XYChart.Series<String, Integer>();

        seriesline.setName("평단가 차트");
        seriesbar.setName("거래량 차트");
        for (AverageAreaAmoumtApartmentData a : averageAreaAmoumtApartmentData) {
            seriesline.getData().add(new XYChart.Data<String, Double>((a.getDealYear() + "." + a.getDealMonth()), a.getAverageAreaAmoumt()));
            seriesbar.getData().add(new XYChart.Data<String, Integer>((a.getDealYear() + "." + a.getDealMonth()), a.getAverageCnt()));
        }
        lineChart.getData().add(seriesline);
        barChart.getData().add(seriesbar);

        for (int i = 0; i < lineChart.getData().size(); i++) {
            XYChart.Series<String,Double> s = (XYChart.Series<String, Double>) lineChart.getData().get(i);
            for (int j = 0; j < s.getData().size(); j++) {
                XYChart.Data<String, Double> data = s.getData().get(j);
                AverageAreaAmoumtApartmentData aptInfo = averageAreaAmoumtApartmentData.get(j);
                int cur = j;
                data.getNode().setOnMouseEntered(event -> {
                    data.getNode().getStyleClass().add("onHover");

                    resultDate.setText(data.getXValue()); // 날짜
                    resultAvgPrice.setText(String.format("%.0f만원", data.getYValue())); // 아파트 평균 가격
                    resultVolume.setText(String.valueOf(aptInfo.getAverageCnt())+"개"); // 아파트 거래량

                });

                //Removing class on exit
                data.getNode().setOnMouseExited(event -> {
                    data.getNode().getStyleClass().remove("onHover");

                    resultDate.setText(""); // 날짜
                    resultAvgPrice.setText(""); // 아파트 평균 가격
                    resultVolume.setText(""); // 아파트 거래량
                });
            }
        }

        lineChart.getXAxis().setTickLabelsVisible(false);
        lineChart.getXAxis().setTickMarkVisible(false);

        barChart.getXAxis().setTickLabelsVisible(false);
        barChart.getXAxis().setTickMarkVisible(false);
    }



    private void alert(int sidoIndex, int sigunguIndex, int eupMyeonDongIndex) {
        Alert alert = new Alert(WARNING);
        if(sidoIndex<0)
            alert.setHeaderText("시/도를 입력해주세요");
        else if(sigunguIndex<0)
            alert.setHeaderText("시/군/구를 입력해주세요");
        else
            alert.setHeaderText("읍/면/동을 입력해주세요");

        alert.showAndWait();
    }
}
