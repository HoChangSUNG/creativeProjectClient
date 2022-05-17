package com.creative.creativeprojectclient;

import body.FluctuationRateWrapper;
import body.SelectRegionGraphData;
import com.creative.creativeprojectclient.datamodel.Func1_1TableRowModel;
import domain.FluctuationRate;
import domain.Sido;
import domain.Sigungu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import network.Packet;
import network.ProtocolType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import network.protocolCode.RealEstateInfoCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.WARNING;

public class Func1_1Controller implements Initializable {

    @FXML
    AnchorPane panel;
    @FXML
    Label result;
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

    private List<Sido> secondRegionSelectList;//두번째 그래프 지역 정보
    private List<String> firstRegionSelectList;//첫번째 그래프 지역 정보
    // 아파트
    @FXML
    private TableView<Func1_1TableRowModel> apartment;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> regionNameColumnApt;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> fluctuationRateColumnApt;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> avgPriceColumnApt;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> populationColumnApt;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> priceCntColumnApt;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        initSecondRegionSelectCombo(); // 두번째 그래프 지역 콤보 박스 초기화
        initFirstRegionSelectCombo();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstRegionSelectList = Arrays.asList("전국", "수도권", "지방권", "6대광역시", "5대광역시", "9개도", "8개도");
    }

    private void initFirstRegionSelectCombo(){// 첫번째 그래프 콤보 박스 초괴화
        regionCombo.setPromptText("지역");

        ObservableList<String> firstGraphRegion = FXCollections.observableArrayList(firstRegionSelectList);
        regionCombo.setItems(firstGraphRegion); //  콤보 박스 초기화

        setComboBoxPrompt(regionCombo);
    }

    private void initSecondRegionSelectCombo(){// 두번째 그래프 시도 콤보 박스 초기화
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

    public void findFirstRegion(){
        int regionComboIndex = regionCombo.getSelectionModel().getSelectedIndex();
        Packet receivePacket = null;

        if(regionComboIndex<0){
            alertMessage("지역을 선택해주세요");
            return;
        }
        System.out.println("선택된 지역 이름 : "+firstRegionSelectList.get(regionComboIndex));
        String regionName = firstRegionSelectList.get(regionComboIndex);

        Packet sendPacket = new Packet(ProtocolType.REAL_ESTATE_INFO.getType(), RealEstateInfoCode.GRAPH_REGION_SELECTION_FIRST_REQ.getCode(),regionName);
        mainController.writePacket(sendPacket); //패킷 송신
//        receivePacket = mainController.readPacket(); //패킷 수신
    }

    public void findSecondRegion(ActionEvent event) { // 두번째 찾기 버튼 클릭시
        int sidoIndex = sidoCombo.getSelectionModel().getSelectedIndex();
        int sigunguIndex = sigunguCombo.getSelectionModel().getSelectedIndex();
        Packet sendPacket = null;
        Packet receivePacket = null;

        if(sidoIndex<0){
            alertMessage("시/도를 선택해주세요");
            return;
        }

        Sido sido = secondRegionSelectList.get(sidoIndex);
        SelectRegionGraphData selectRegionGraphData=null;


        if(sigunguIndex <0){
            selectRegionGraphData = new SelectRegionGraphData(sido.getRegionName(), null);
        }
        else{
            Sigungu sigungu = secondRegionSelectList.get(sidoIndex).getSigunguList().get(sigunguIndex);
            if(sido.getRegionName().equals(sigungu.getRegionName())){ //세종특별자치시 인 경우에는 sido 이름만 넘겨줌.
                selectRegionGraphData = new SelectRegionGraphData(sido.getRegionName(), null);
            }
            else{
                selectRegionGraphData = new SelectRegionGraphData(sido.getRegionName(), sigungu.getRegionName());

            }
        }
        sendPacket = new Packet(ProtocolType.REAL_ESTATE_INFO.getType(), RealEstateInfoCode.GRAPH_REGION_SELECTION_SECOND_REQ.getCode(),selectRegionGraphData);


        mainController.writePacket(sendPacket); //패킷 송신
//        receivePacket = mainController.readPacket(); //패킷 수신

    }

    private void alertMessage(String message){
        Alert alert = new Alert(WARNING);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    public void moveToFunc1_1Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func1_1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func1_1Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);
            controller.showRegionalEstateAvgData();

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
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
    public void moveToFunc3_1Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func3_1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func3_1Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showRegionalEstateAvgData(){//실거래가 기준 지역별 부동산의 평균 가격,등락폭,인구수 제공
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_INFO.getType());
        packet.setProtocolCode(RealEstateInfoCode.SEND_DATA_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        FluctuationRateWrapper data = (FluctuationRateWrapper)sendPacket.getBody();

        showApt(data.getApartmentFluctuationRate());

    }

    public void showApt(List<FluctuationRate> data){ //아파트 표 출력

        regionNameColumnApt.setCellValueFactory(cellData->cellData.getValue().regionNameProperty());
        fluctuationRateColumnApt.setCellValueFactory(cellData->cellData.getValue().fluctuationRateProperty());
        avgPriceColumnApt.setCellValueFactory(cellData->cellData.getValue().avgPriceProperty());
        populationColumnApt.setCellValueFactory(cellData->cellData.getValue().populationProperty());
        priceCntColumnApt.setCellValueFactory(cellData->cellData.getValue().priceCntProperty());

        ObservableList<Func1_1TableRowModel> myList = FXCollections.observableArrayList();

        for (int i=0;i<data.size();i++){
            SimpleStringProperty regionName = new SimpleStringProperty(data.get(i).getRegionName());
            SimpleStringProperty fluctuationRate = new SimpleStringProperty(String.format("%.2f",data.get(i).getFluctuationRateData()));
            SimpleStringProperty averagePrice = new SimpleStringProperty(String.valueOf(data.get(i).getAveragePrice()));
            SimpleStringProperty population = new SimpleStringProperty(String.valueOf(data.get(i).getPopulation()));
            SimpleStringProperty priceCnt = new SimpleStringProperty(data.get(i).getCurrentPriceCnt() + "(" + data.get(i).getLastPriceCnt() + ")");
            myList.add((new Func1_1TableRowModel(regionName,fluctuationRate,averagePrice,population,priceCnt)));
        }


        fluctuationRateColumnApt.setCellFactory(new Callback<TableColumn<Func1_1TableRowModel, String>, TableCell<Func1_1TableRowModel, String>>() {
            @Override
            public TableCell<Func1_1TableRowModel, String> call(TableColumn<Func1_1TableRowModel, String> param) {
                return new TableCell<Func1_1TableRowModel,String>(){
                    @Override
                    protected void updateItem(String item,boolean empty){
                        if (!empty) {
                            int currentIndex = indexProperty()
                                    .getValue() < 0 ? 0
                                    : indexProperty().getValue();
                            String fluctuationRate = param
                                    .getTableView().getItems()
                                    .get(currentIndex).fluctuationRateProperty().getValue();

                            if (fluctuationRate.substring(0,1).equals("-")) {
                                setTextFill(Color.BLUE);
//                                getStyleClass().add("minus-fluctuation");
                                setText(fluctuationRate+"%("+data.get(currentIndex).getFluctuationPrice()+")");
                            }
                            else{
                                setTextFill(Color.RED);

//                                getStyleClass().add("plus-fluctuation");
                                setText("+"+fluctuationRate+"%("+data.get(currentIndex).getFluctuationPrice()+")");

                            }
                        }
                    }
                };
            }
        });

        apartment.setItems(myList);
    }


}
