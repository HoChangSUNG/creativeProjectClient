package com.creative.creativeprojectclient;


import com.creative.creativeprojectclient.datamodel.Func2TableRowModel;
import domain.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import network.Packet;
import network.ProtocolType;
import network.protocolCode.RealEstateCompareCode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.*;

public class Func2Controller implements Initializable {
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
    private TableView<Func2TableRowModel> apartment;
    @FXML
    private TableColumn<Func2TableRowModel, String> apartmentName;
    @FXML
    private TableColumn<Func2TableRowModel, String> area;

    private List<Sido> regionSelectList;
    @FXML
    Button test;
    private MainController mainController;
    private List<ApartmentInfo1> apartmentList;
    private String regionalCode;
    private String regionName;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        initRegionSelectCombo(); // 지역 콤보 박스 초기화
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void findApart(ActionEvent event) { // 찾기 버튼 클릭시
        int sidoIndex = sidoCombo.getSelectionModel().getSelectedIndex();
        int sigunguIndex = sigunguCombo.getSelectionModel().getSelectedIndex();
        int eupMyeonDongIndex = eupMyeonDongCombo.getSelectionModel().getSelectedIndex();

        if(sidoIndex<0 || sigunguIndex<0 || eupMyeonDongIndex<0){ // 선택되지 않은 콤보 박스가 있을 경우
            alert(sidoIndex,sigunguIndex,eupMyeonDongIndex);
            return;
        }
        Sigungu sigungu = regionSelectList.get(sidoIndex).getSigunguList().get(sigunguIndex);
        EupMyeonDong eupMyeonDong = sigungu.getEupMyeonDongList().get(eupMyeonDongIndex);

        SelectApartRegion selectApartRegion = new SelectApartRegion(sigungu.getRegionName(), sigungu.getRegionalCode(), eupMyeonDong.getRegionName());

        regionalCode = sigungu.getRegionalCode();
        regionName = eupMyeonDong.getRegionName();

        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_COMPARE.getType());
        packet.setProtocolCode(RealEstateCompareCode.REAL_ESTATE_APARTMENT_REQ.getCode());

        packet.setBody(selectApartRegion);

        mainController.writePacket(packet);
        showApartment();
    }

    public void showApartment(){
        Packet sendPacket = mainController.readPacket();
        apartmentList = (java.util.List<ApartmentInfo1>)sendPacket.getBody();

        apartmentName.setCellValueFactory(cellData->cellData.getValue().apartmentNameProperty());
        area.setCellValueFactory(cellData->cellData.getValue().areaProperty());

        ObservableList<Func2TableRowModel> myList = FXCollections.observableArrayList();

        for (int i=0;i<apartmentList.size();i++){
            SimpleStringProperty apartmentName = new SimpleStringProperty(apartmentList.get(i).getApartmentName());
            SimpleStringProperty area = new SimpleStringProperty(String.valueOf(Math.round(apartmentList.get(i).getArea() / 3.3058 * 10)/10.0));

            myList.add((new Func2TableRowModel(apartmentName,area)));
        }

        apartment.setItems(myList);
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

    public void selectTableView(javafx.scene.input.MouseEvent mouseEvent) {
        Func2TableRowModel func2TableRowModel = apartment.getSelectionModel().getSelectedItem();

        String apartmentName = func2TableRowModel.apartmentNameProperty().get();
        float area = 0;

        for(int i = 0; i < apartmentList.size(); i ++) {
            if(apartmentName.equals(apartmentList.get(i).getApartmentName())) {
                area = apartmentList.get(i).getArea();
                break;
            }
        }

        ApartmentInfo2 apartmentInfo = new ApartmentInfo2(regionalCode,regionName,apartmentName,area);

        System.out.println("아파트 명 :" + apartmentInfo.getApartmentName());
        System.out.println("면적 :" + apartmentInfo.getArea());

        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_COMPARE.getType());
        packet.setProtocolCode(RealEstateCompareCode.REAL_ESTATE_APARTMENT_INFO_REQ.getCode());

        packet.setBody(apartmentInfo);
        mainController.writePacket(packet);

        showApartmentInfoGraph();
    }

    private void showApartmentInfoGraph() {
        Packet sendPacket = null;
        sendPacket = mainController.readPacket();
        List<ApartmentInfo3> apartmentList = (java.util.List<ApartmentInfo3>)sendPacket.getBody();
        System.out.println("그래프 데이터 패킷 무사 도착");
        System.out.println("면적 : " + apartmentList.get(0).getArea());
    }


}
