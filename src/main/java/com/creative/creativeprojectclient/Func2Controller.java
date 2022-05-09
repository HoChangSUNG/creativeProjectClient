package com.creative.creativeprojectclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import network.Packet;
import network.ProtocolType;
import network.protocolCode.RealEstateCompareCode;

import java.net.URL;
import java.util.ResourceBundle;

public class Func2Controller implements Initializable {
    @FXML
    AnchorPane panel;
    @FXML
    Label result;
    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void moveToFunc1Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func1_1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func1_1Controller controller = fxmlLoader.getController();
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

    @FXML
    public void func1(){// 기능 2.1 REQ 한 후, RES 받는것 테스트
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_COMPARE.getType());
        packet.setProtocolCode(RealEstateCompareCode.REAL_ESTATE_COMPARE_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        String data = (String)sendPacket.getBody();

        System.out.println(data);
        result.setText(data);

    }


}
