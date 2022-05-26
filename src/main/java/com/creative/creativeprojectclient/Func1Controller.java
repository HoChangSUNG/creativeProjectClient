package com.creative.creativeprojectclient;

import body.SelectRegionGraphData;
import domain.ApartmentIndex;
import domain.Sido;
import domain.Sigungu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import network.Packet;
import network.ProtocolType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import network.protocolCode.RealEstateInfoCode;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.WARNING;

public class Func1Controller implements Initializable {

    @FXML
    AnchorPane panel;
    @FXML
    Label firstResultDate;
    @FXML
    Label firstResultIndices;
    @FXML
    Label firstResultFluctuationRate;

    @FXML
    Label secondResultDate;
    @FXML
    Label secondResultIndices;
    @FXML
    Label secondResultFluctuationRate;

    private MainController mainController;

    @FXML
    ComboBox<String> regionCombo; // 첫번째 그래프 콤보 박스
    @FXML
    ComboBox<String> sidoCombo; //두번째 그래프 콤보 박스 시도 선택
    @FXML
    ComboBox<String> sigunguCombo;//두번째 그래프 콤보 박스 시군구 선택
    @FXML
    Button findFirstGraphBtn; // 첫번째 그래프 찾기 버튼
    @FXML
    Button findSecondGraphBtn;// 두번째 그래프 찾기 버튼

    private List<String> firstRegionSelectList;//첫번째 그래프 지역 정보
    private List<Sido> secondRegionSelectList;//두번째 그래프 지역 정보

    @FXML
    private LineChart<String, Number> firstChart; //첫번째 그래프 지역 정보
    @FXML
    private LineChart<String, Number> secondChart; //첫번째 그래프 지역 정보

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        initSecondRegionSelectCombo(); // 두번째 그래프 지역 콤보 박스 초기화
        initFirstRegionSelectCombo();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstRegionSelectList = Arrays.asList("전국", "수도권", "지방권", "6대광역시", "5대광역시", "9개도", "8개도");
    }

    private void initFirstRegionSelectCombo() {// 첫번째 그래프 콤보 박스 초기화
        regionCombo.setPromptText("지역");

        ObservableList<String> firstGraphRegion = FXCollections.observableArrayList(firstRegionSelectList);
        regionCombo.setItems(firstGraphRegion); //  콤보 박스 초기화

        setComboBoxPrompt(regionCombo);
    }

    private void initSecondRegionSelectCombo() {// 두번째 그래프 시도 콤보 박스 초기화
        secondRegionSelectList = mainController.getRegionSelectList();// 지역 리스트 초기화

        sidoCombo.setPromptText("시/도");
        sigunguCombo.setPromptText("시/군/구");

        List<String> sidoNameList = secondRegionSelectList.stream()
                .map(sido -> sido.getRegionName())
                .collect(Collectors.toList()); // 시도 이름 리스트 반환

        ObservableList<String> sidos = FXCollections.observableArrayList(sidoNameList);
        sidoCombo.setItems(sidos); // 시도 콤보 박스 초기화

        setComboBoxPrompt(sidoCombo);
        setComboBoxPrompt(sigunguCombo);
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
        List<Sigungu> sigunguList = secondRegionSelectList.get(selectedSidoIndex).getSigunguList();// 선택한 시도에 있는 시군구 리스트 반환

        List<String> sigunguNameList = sigunguList.stream()
                .map(sigungu -> sigungu.getRegionName())
                .collect(Collectors.toList());// 선택한 시도에 있는 시군구 이름 리스트 반환

        ObservableList<String> sigungus = FXCollections.observableArrayList(sigunguNameList);

        sigunguCombo.setItems(sigungus); //시도에 맞는 시군구 콤보 박스 설정

    }

    public void findFirstRegion() {
        int regionComboIndex = regionCombo.getSelectionModel().getSelectedIndex();
        Packet receivePacket = null;

        if (regionComboIndex < 0) {
            alertMessage("지역을 선택해주세요");
            return;
        }
        System.out.println("선택된 지역 이름 : " + firstRegionSelectList.get(regionComboIndex));
        String regionName = firstRegionSelectList.get(regionComboIndex);

        Packet sendPacket = new Packet(ProtocolType.REAL_ESTATE_INFO.getType(), RealEstateInfoCode.GRAPH_REGION_SELECTION_FIRST_REQ.getCode(), regionName);

        mainController.writePacket(sendPacket); //패킷 송신
        receivePacket = mainController.readPacket(); //패킷 수신

        showRegionGraph(receivePacket,firstChart,firstResultDate,firstResultIndices,firstResultFluctuationRate);
    }

    private void showRegionGraph(Packet receivePacket, LineChart<String, Number> chart, Label resultDate, Label resultIndices, Label resultFluctuationRate) { //지역별 아파트 매매 그래프 정보 그래프에 출력

        chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();


        List<ApartmentIndex> apartmentIndices = (List<ApartmentIndex>) receivePacket.getBody();

        String selectedRegionName = apartmentIndices.get(0).getRegion();
        chart.setTitle(selectedRegionName);

        for (ApartmentIndex apartmentIndex : apartmentIndices) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월");
            dateFormat.format(apartmentIndex.getDate());
//            System.out.println(apartmentIndex.getRegion()+" "+dateFormat.format(apartmentIndex.getDate())+" "+apartmentIndex.getIndex()+" "+apartmentIndex.getFluctuation());
            series.getData().add(new XYChart.Data<String, Number>(dateFormat.format(apartmentIndex.getDate()), apartmentIndex.getIndex()));

        }
        series.setName(selectedRegionName + " 부동산 가격 지수");
        chart.getData().add(series);

        for (int i = 0; i < chart.getData().size(); i++) {
            XYChart.Series<String, Number> s = chart.getData().get(i);
            for (int j = 0; j < s.getData().size(); j++) {
                XYChart.Data<String, Number> data = s.getData().get(j);
                ApartmentIndex apartmentIndex = apartmentIndices.get(j);
                int cur = j;
                data.getNode().setOnMouseEntered(event -> {
                    data.getNode().getStyleClass().add("onHover");
                    resultDate.setText(data.getXValue()); // 날짜
                    resultIndices.setText(String.valueOf(data.getYValue())); // 아파트 매매지수
                    resultFluctuationRate.setText(apartmentIndex.getFluctuation() + "%"); //등락폭
                    if (apartmentIndex.getFluctuation() >= 0)
                        resultFluctuationRate.setTextFill(Color.RED);
                    else
                        resultFluctuationRate.setTextFill(Color.BLUE);
                });

                //Removing class on exit
                data.getNode().setOnMouseExited(event -> {
                    data.getNode().getStyleClass().remove("onHover");
                    resultDate.setText("");
                    resultIndices.setText("");
                    resultFluctuationRate.setText("");
                });
            }
        }

        chart.getXAxis().setTickLabelsVisible(false);
        chart.getXAxis().setTickMarkVisible(false);

    }

    public void findSecondRegion(ActionEvent event) { // 두번째 찾기 버튼 클릭시
        int sidoIndex = sidoCombo.getSelectionModel().getSelectedIndex();
        int sigunguIndex = sigunguCombo.getSelectionModel().getSelectedIndex();
        Packet sendPacket = null;
        Packet receivePacket = null;

        if (sidoIndex < 0) {
            alertMessage("시/도를 선택해주세요");
            return;
        }

        Sido sido = secondRegionSelectList.get(sidoIndex);
        SelectRegionGraphData selectRegionGraphData = null;


        if (sigunguIndex < 0) {
            selectRegionGraphData = new SelectRegionGraphData(sido.getRegionName(), null);
        } else {
            Sigungu sigungu = secondRegionSelectList.get(sidoIndex).getSigunguList().get(sigunguIndex);
            if (sido.getRegionName().equals(sigungu.getRegionName())) { //세종특별자치시 인 경우에는 sido 이름만 넘겨줌.
                selectRegionGraphData = new SelectRegionGraphData(sido.getRegionName(), null);
            } else {
                selectRegionGraphData = new SelectRegionGraphData(sido.getRegionName(), sigungu.getRegionName());

            }
        }
        sendPacket = new Packet(ProtocolType.REAL_ESTATE_INFO.getType(), RealEstateInfoCode.GRAPH_REGION_SELECTION_SECOND_REQ.getCode(), selectRegionGraphData);


        mainController.writePacket(sendPacket); //패킷 송신
        receivePacket = mainController.readPacket(); //패킷 수신
        showRegionGraph(receivePacket,secondChart,secondResultDate,secondResultIndices,secondResultFluctuationRate);
    }

    private void alertMessage(String message) {
        Alert alert = new Alert(WARNING);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    public void moveToFunc1Controller() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func1Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void moveToFunc2Controller() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func2.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func2Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void moveToFunc3Controller() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func3.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func3Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
