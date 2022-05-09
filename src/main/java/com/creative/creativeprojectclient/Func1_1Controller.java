package com.creative.creativeprojectclient;

import body.FluctuationRateWrapper;
import com.creative.creativeprojectclient.datamodel.Func1_1TableRowModel;
import domain.FluctuationRate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import network.Packet;
import network.ProtocolType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import network.protocolCode.RealEstateInfoCode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Func1_1Controller implements Initializable {

    @FXML
    AnchorPane panel;
    @FXML
    Label result;
    private MainController mainController;

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

    // 연립다세대
    @FXML
    private TableView<Func1_1TableRowModel> rowhouse;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> regionNameColumnRow;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> fluctuationRateColumnRow;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> avgPriceColumnRow;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> populationColumnRow;

    // 단독/다가구
    @FXML
    private TableView<Func1_1TableRowModel> detachedHouse;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> regionNameColumnDetach;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> fluctuationRateColumnDetach;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> avgPriceColumnDetach;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> populationColumnDetach;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        packet.setProtocolType(ProtocolType.REAL_ESTATE_INFO.getType());
        packet.setProtocolCode(RealEstateInfoCode.SEND_DATA_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        String data = (String)sendPacket.getBody();

        System.out.println(data);
        result.setText(data);
    }

    @FXML
    public void func2(){// 기능 1.2 REQ 한 후, RES 받는것 테스트
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_INFO.getType());
        packet.setProtocolCode(RealEstateInfoCode.SEND_GRAPH_DATA_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        String data = (String)sendPacket.getBody();

        System.out.println(data);
        result.setText(data);

    }

    public void showRegionalEstateAvgData(){//실거래가 기준 지역별 부동산의 평균 가격,등락폭,인구수 제공
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_INFO.getType());
        packet.setProtocolCode(RealEstateInfoCode.SEND_DATA_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        FluctuationRateWrapper data = (FluctuationRateWrapper)sendPacket.getBody();

        showApt(data.getApartmentFluctuationRate());
        showRowHouse(data.getRowhouseFluctuationRate());
        showDetached(data.getDetachedhouseFluctuationRate());

        System.out.println("apt"+data.getApartmentFluctuationRate().size());
        System.out.println("showRowHouse"+data.getRowhouseFluctuationRate().size());
        System.out.println("showRowHouse"+data.getDetachedhouseFluctuationRate().size());


        //패킷 받고 나눠라
    }
    public void showApt(List<FluctuationRate> data){ //아파트 표 출력
        regionNameColumnApt.setCellValueFactory(cellData->cellData.getValue().regionNameProperty());
        fluctuationRateColumnApt.setCellValueFactory(cellData->cellData.getValue().fluctuationRateProperty());
        avgPriceColumnApt.setCellValueFactory(cellData->cellData.getValue().avgPriceProperty());
        populationColumnApt.setCellValueFactory(cellData->cellData.getValue().populationProperty());


        ObservableList<Func1_1TableRowModel> myList = FXCollections.observableArrayList();

        for (int i=0;i<data.size();i++){
            SimpleStringProperty regionName = new SimpleStringProperty(data.get(i).getRegionName());
            SimpleStringProperty fluctuationRate = new SimpleStringProperty(String.format("%.2f",data.get(i).getFluctuationRateData()));
            SimpleStringProperty averagePrice = new SimpleStringProperty(String.valueOf(data.get(i).getAveragePrice()));
            SimpleStringProperty population = new SimpleStringProperty(String.valueOf(data.get(i).getPopulation()));
            myList.add((new Func1_1TableRowModel(regionName,fluctuationRate,averagePrice,population)));
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
//                                setTextFill(Color.BLUE);
                                getStyleClass().add("minus-fluctuation");
                                setText(fluctuationRate+"("+data.get(currentIndex).getFluctuationPrice()+")");
                            }
                            else{
                                getStyleClass().add("plus-fluctuation");
                                setText("+"+fluctuationRate+"("+data.get(currentIndex).getFluctuationPrice()+")");

                            }
                        }
                    }
                };
            }
        });

        apartment.setItems(myList);
    }

    public void showRowHouse(List<FluctuationRate> data){//연립 다세대
        regionNameColumnRow.setCellValueFactory(cellData->cellData.getValue().regionNameProperty());
        fluctuationRateColumnRow.setCellValueFactory(cellData->cellData.getValue().fluctuationRateProperty());
        avgPriceColumnRow.setCellValueFactory(cellData->cellData.getValue().avgPriceProperty());
        populationColumnRow.setCellValueFactory(cellData->cellData.getValue().populationProperty());

        ObservableList<Func1_1TableRowModel> myList = FXCollections.observableArrayList();

        for (int i=0;i<data.size();i++){
            SimpleStringProperty regionName = new SimpleStringProperty(data.get(i).getRegionName());
            SimpleStringProperty fluctuationRate = new SimpleStringProperty(String.format("%.2f",data.get(i).getFluctuationRateData()));
            SimpleStringProperty averagePrice = new SimpleStringProperty(String.valueOf(data.get(i).getAveragePrice()));
            SimpleStringProperty population = new SimpleStringProperty(String.valueOf(data.get(i).getPopulation()));
            myList.add((new Func1_1TableRowModel(regionName,fluctuationRate,averagePrice,population)));
        }


        fluctuationRateColumnRow.setCellFactory(new Callback<TableColumn<Func1_1TableRowModel, String>, TableCell<Func1_1TableRowModel, String>>() {
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
//                                setTextFill(Color.BLUE);
                                getStyleClass().add("minus-fluctuation");
                                setText(fluctuationRate+"("+data.get(currentIndex).getFluctuationPrice()+")");
                            }
                            else{
                                getStyleClass().add("plus-fluctuation");
                                setText(fluctuationRate+"("+data.get(currentIndex).getFluctuationPrice()+")");
                            }
                        }
                    }
                };
            }
        });

        apartment.setItems(myList);

    }

    public void showDetached(List<FluctuationRate> data){// 단독/다가구
        regionNameColumnDetach.setCellValueFactory(cellData->cellData.getValue().regionNameProperty());
        fluctuationRateColumnDetach.setCellValueFactory(cellData->cellData.getValue().fluctuationRateProperty());
        avgPriceColumnDetach.setCellValueFactory(cellData->cellData.getValue().avgPriceProperty());
        populationColumnDetach.setCellValueFactory(cellData->cellData.getValue().populationProperty());

        ObservableList<Func1_1TableRowModel> myList = FXCollections.observableArrayList();

        for (int i=0;i<data.size();i++){
            SimpleStringProperty regionName = new SimpleStringProperty(data.get(i).getRegionName());
            SimpleStringProperty fluctuationLate = new SimpleStringProperty(String.format("%.2f",data.get(i).getFluctuationRateData()));
            SimpleStringProperty averagePrice = new SimpleStringProperty(String.valueOf(data.get(i).getAveragePrice()));
            SimpleStringProperty population = new SimpleStringProperty(String.valueOf(data.get(i).getPopulation()));
            myList.add((new Func1_1TableRowModel(regionName,fluctuationLate,averagePrice,population)));
        }


        fluctuationRateColumnDetach.setCellFactory(new Callback<TableColumn<Func1_1TableRowModel, String>, TableCell<Func1_1TableRowModel, String>>() {
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
//                                setTextFill(Color.BLUE);
                                getStyleClass().add("minus-fluctuation");
                                setText(fluctuationRate+"("+data.get(currentIndex).getFluctuationPrice()+")");
                            }
                            else{
                                getStyleClass().add("plus-fluctuation");
                                setText(fluctuationRate+"("+data.get(currentIndex).getFluctuationPrice()+")");

                            }
                        }
                    }
                };
            }
        });

        apartment.setItems(myList);

    }
}
