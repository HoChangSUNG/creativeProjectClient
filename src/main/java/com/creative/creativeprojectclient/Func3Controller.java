package com.creative.creativeprojectclient;

import dto.AdminTestDTO;
import dto.StudentTestDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import network.Packet;
import network.ProtocolType;
import network.protocolCode.RealEstateInfoCode;
import network.protocolCode.RealEstateRecommendCode;

import java.net.URL;
import java.util.ResourceBundle;

public class Func3Controller implements Initializable {
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
    public void func1(){// 기능 1.1 REQ 한 후, RES 받는것 테스트
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_RECOMMEND.getType());
        packet.setProtocolCode(RealEstateRecommendCode.RECORD_INFO_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        String data = (String)sendPacket.getBody();

        System.out.println(data);
        result.setText(data);

    }

    @FXML
    public void func2(){// 기능 1.2 REQ 한 후, RES 받는것 테스트
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_RECOMMEND.getType());
        packet.setProtocolCode(RealEstateRecommendCode.REGION_RECOMMEND_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        String data = (String)sendPacket.getBody();

        System.out.println(data);
        result.setText(data);

    }

}
