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
import network.protocolCode.RealEstateRecommendCode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.WARNING;

public class Func3_1Controller implements Initializable {
    @FXML
    AnchorPane panel;

    @FXML
    Label result;
    @FXML
    Button findBtn;
    @FXML
    ComboBox<String> sidoCombo;
    @FXML
    ComboBox<String> sigunguCombo;

    private List<Sido> regionSelectList;
    private MainController mainController;
    public void setMainController(MainController mainController) {

        this.mainController = mainController;
        initRegionSelectCombo(); // 지역 콤보 박스 초기화

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initRegionSelectCombo(){// 시도 콤보 박스 초기화
        regionSelectList = mainController.getRegionSelectList();// 지역 리스트 초기화

        sidoCombo.setPromptText("시/도");
        sigunguCombo.setPromptText("시/군/구");

        List<String> sidoNameList = regionSelectList.stream()
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
        List<Sigungu> sigunguList = regionSelectList.get(selectedSidoIndex).getSigunguList();// 선택한 시도에 있는 시군구 리스트 반환

        List<String> sigunguNameList = sigunguList.stream()
                .map(sigungu -> sigungu.getRegionName())
                .collect(Collectors.toList());// 선택한 시도에 있는 시군구 이름 리스트 반환

        ObservableList<String> sigungus = FXCollections.observableArrayList(sigunguNameList);

        sigunguCombo.setItems(sigungus); //시도에 맞는 시군구 콤보 박스 설정

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

    public void findButton(ActionEvent event) { // 찾기 버튼 클릭시
        int sidoIndex = sidoCombo.getSelectionModel().getSelectedIndex();
        int sigunguIndex = sigunguCombo.getSelectionModel().getSelectedIndex();

        if(sidoIndex<0 || sigunguIndex<0){ // 선택되지 않은 콤보 박스가 있을 경우
            alert(sidoIndex,sigunguIndex);
            return;
        }
        Sigungu sigungu = regionSelectList.get(sidoIndex).getSigunguList().get(sigunguIndex);

        System.out.println("선택된 시군구 지역 코드 : "+sigungu.getRegionalCode());
        System.out.println("선택된 시군구 지역 이름 : "+sigungu.getRegionName());

    }

    private void alert(int sidoIndex, int sigunguIndex) {
        Alert alert = new Alert(WARNING);
        if(sidoIndex<0)
            alert.setHeaderText("시/도를 입력해주세요");
        else
            alert.setHeaderText("시/군/구를 입력해주세요");

        alert.showAndWait();
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

    @FXML
    public void moveToFunc3_2Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func3_2.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func3_2Controller controller = fxmlLoader.getController();
            controller.setMainController(mainController);

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
