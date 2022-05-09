package com.creative.creativeprojectclient;

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

    @FXML
    private TableView<Func1_1TableRowModel> myTableView;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> regionNameColumn;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> fluctuationRateColumn;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> avgPriceColumn;
    @FXML
    private TableColumn<Func1_1TableRowModel, String> populationColumn;

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
        regionNameColumn.setCellValueFactory(cellData->cellData.getValue().regionNameProperty());
        fluctuationRateColumn.setCellValueFactory(cellData->cellData.getValue().fluctuationRateProperty());
        avgPriceColumn.setCellValueFactory(cellData->cellData.getValue().avgPriceProperty());
        populationColumn.setCellValueFactory(cellData->cellData.getValue().populationProperty());


        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_INFO.getType());
        packet.setProtocolCode(RealEstateInfoCode.SEND_DATA_REQ.getCode());
        mainController.writePacket(packet);

        Packet sendPacket = mainController.readPacket();
        List<FluctuationRate> data = (List<FluctuationRate>)sendPacket.getBody();

        ObservableList<Func1_1TableRowModel> myList = FXCollections.observableArrayList();

        for (int i=0;i<data.size();i++){
            System.out.println(data.get(i));
            SimpleStringProperty regionName = new SimpleStringProperty(data.get(i).getRegionName());
            SimpleStringProperty fluctuationLate = new SimpleStringProperty(String.format("%.2f",data.get(i).getFluctuationLateData()));
            SimpleStringProperty averagePrice = new SimpleStringProperty(String.valueOf(data.get(i).getPrice()));
            SimpleStringProperty population = new SimpleStringProperty(String.valueOf(data.get(i).getPopulation()));
            myList.add((new Func1_1TableRowModel(regionName,fluctuationLate,averagePrice,population)));
        }

//        ObservableList<Func1_1TableRowModel> myList = FXCollections.observableArrayList(
//                new Func1_1TableRowModel(new SimpleStringProperty("Jin Seong"), new SimpleStringProperty("1"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Jang Ho"), new SimpleStringProperty("-2"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Sung Bin"), new SimpleStringProperty("3"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Key Hwang"), new SimpleStringProperty("-4"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Seong Woo"), new SimpleStringProperty("5"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("I kyun"), new SimpleStringProperty("-6"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Jin Seong"), new SimpleStringProperty("7"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Jang Ho"), new SimpleStringProperty("-8"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Sung Bin"), new SimpleStringProperty("9"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Key Hwang"), new SimpleStringProperty("-10"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Seong Woo"), new SimpleStringProperty("11"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("I kyun"), new SimpleStringProperty("-12"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Jin Seong"), new SimpleStringProperty("13"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Jang Ho"), new SimpleStringProperty("-14"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Sung Bin"), new SimpleStringProperty("15"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Key Hwang"), new SimpleStringProperty("-16"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("Seong Woo"), new SimpleStringProperty("17"), new SimpleStringProperty("male"), new SimpleStringProperty("male")),
//                new Func1_1TableRowModel(new SimpleStringProperty("I kyun"), new SimpleStringProperty("-18"), new SimpleStringProperty("male"), new SimpleStringProperty("male"))
//        );



        fluctuationRateColumn.setCellFactory(new Callback<TableColumn<Func1_1TableRowModel, String>, TableCell<Func1_1TableRowModel, String>>() {
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
                                setText(fluctuationRate);
                            }
                            else{
                                getStyleClass().add("plus-fluctuation");
                                setText("+"+fluctuationRate);

                            }
                        }
                    }
                };
            }
        });

        myTableView.setItems(myList);

    }
}
