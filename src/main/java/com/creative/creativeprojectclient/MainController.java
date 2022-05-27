package com.creative.creativeprojectclient;

import domain.Sido;
import network.Packet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import network.ProtocolType;
import network.protocolCode.RealEstateInfoCode;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private Socket socket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    @FXML
    private AnchorPane panel;

    private List<Sido> regionSelectList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //소켓 연결
        initSocket();

        //지역 선택 리스트 가져오기
        regionSelectList = readRegionSelectList();

        //첫번째 기능으로 이동
        moveToFunc1Controller();


    }

    public List<Sido> getRegionSelectList(){
        return regionSelectList;
    }
    public void moveToFunc1Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func1Controller controller = fxmlLoader.getController();
            controller.setMainController(this);

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initSocket(){
        InetAddress ip = null;
        int ServerPortNum = 5000;
        try {
            ip = InetAddress.getByName("192.168.226.44");
            //ip = InetAddress.getByName("localhost");
            this.socket= new Socket(ip, ServerPortNum);// 소켓 연결
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException("서버가 응답하지 않습니다.",e);
        }

    }
    public Packet readPacket(){
        Packet packet;
        try{
            packet = (Packet)is.readObject();
        }catch(Exception e){
            e.printStackTrace();

            throw new RuntimeException();
        }
        return packet;
    }

    public void writePacket(Packet packet){
        try{
            os.writeObject(packet);
            os.flush();
        }catch(Exception e){
            e.printStackTrace();

            throw new RuntimeException();
        }
    }

    private List<Sido> readRegionSelectList(){
        Packet packet = new Packet();
        packet.setProtocolType(ProtocolType.REAL_ESTATE_INFO.getType());
        packet.setProtocolCode(RealEstateInfoCode.REGION_SELECTION_REQ.getCode());
        writePacket(packet);
        Packet receivePacket = readPacket();

        return (java.util.List<Sido>) receivePacket.getBody();
    }

}