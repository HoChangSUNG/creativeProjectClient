package network;


import java.io.Serializable;


public class Packet implements Serializable {

    private byte protocolType;
    private byte protocolCode;
    private Object body;

    public Packet() {
    }

    public Packet(byte protocolType, byte protocolCode, Object body) {
        this.protocolType = protocolType;
        this.protocolCode = protocolCode;
        this.body = body;
    }

    public byte getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(byte protocolType) {
        this.protocolType = protocolType;
    }

    public byte getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(byte protocolCode) {
        this.protocolCode = protocolCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
