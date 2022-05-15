package com.creative.creativeprojectclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import network.Packet;
import network.ProtocolType;
import network.protocolCode.RealEstateRecommendCode;

import java.net.URL;
import java.util.ResourceBundle;

public class Func3_1Controller implements Initializable {
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
