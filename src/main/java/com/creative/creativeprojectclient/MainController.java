package com.creative.creativeprojectclient;

import network.Packet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
public class MainController implements Initializable {

    private Socket socket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    @FXML
    private AnchorPane panel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("=======MainController 실행됨=======");

        //소켓 연결
        initSocket();
        
        //첫번째 기능으로 이동

        moveToFunc1_1Controller();

    }

    public void moveToFunc1_1Controller(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("func1_1.fxml"));
            AnchorPane std = fxmlLoader.load();
            Func1_1Controller controller = fxmlLoader.getController();
            controller.setMainController(this);
            controller.showRegionalEstateAvgData();//1.1기능 실행

            panel.getChildren().setAll(std);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initSocket(){
        InetAddress ip = null;
        int ServerPortNum = 5000;
        try {
            ip = InetAddress.getByName("localhost");
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

}