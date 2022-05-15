package com.creative.creativeprojectclient;

import domain.EupMyeonDong;
import domain.Sido;
import domain.Sigungu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private List<Sido> regionSelectList;
    @FXML
    Button test;
    private MainController mainController;
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

    @FXML
    public void func1(){// 기능 2.1 REQ 한 후, RES 받는것 테스트
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_COMPARE.getType());
        packet.setProtocolCode(RealEstateCompareCode.REAL_ESTATE_COMPARE_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        String data = (String)sendPacket.getBody();

        System.out.println(data);

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

        System.out.println("선택된 시군구 지역 코드 : "+sigungu.getRegionalCode());
        System.out.println("선택된 시군구 지역 이름 : "+sigungu.getRegionName());
        System.out.println("선택된 읍면동 이름 :" +eupMyeonDong.getRegionName());

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
