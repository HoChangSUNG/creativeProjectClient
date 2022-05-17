package network.protocolCode;

public enum RealEstateInfoCode {
    // 1. 지역별 부동산 가격 제공
    SEND_DATA_REQ((byte) 0x00),  SEND_DATA_RES((byte) 0x01), SEND_GRAPH_DATA_REQ((byte) 0x02), SEND_GRAPH_DATA_RES((byte) 0x03),
    REGION_SELECTION_REQ((byte) 0x04),REGION_SELECTION_RES((byte) 0x05), GRAPH_REGION_SELECTION_FIRST_REQ((byte) 0x06), GRAPH_REGION_SELECTION_FIRST_RES((byte) 0x07),
    GRAPH_REGION_SELECTION_SECOND_REQ((byte) 0x08), GRAPH_REGION_SELECTION_SECOND_RES((byte) 0x09);
    /** 1. 지역별 부동산 가격 제공 ProtocolCode
     *
     * 1-1. 실거래가 기준 지역별 부동산의 평균 가격과 가격 등락폭, 인구수 제공
     * SEND_DATA_REQ : 클라이언트가 서버에게 요청
     * SEND_DATA_RES : 서버가 클라이언트에게 응답
     *
     * 1-2. 지역별 최근 몇달간의 가격 변동폭, 거래량 등을 그래프로 제공
     * SEND_GRAPH_DATA_REQ : 클라이언트가 서버에게 요청
     * SEND_GRAPH_DATA_RES : 서버가 클라이언트에게 응답
     *
     */
    private final byte code;

    public byte getCode() {
        return code;
    }

    RealEstateInfoCode(byte code) {
        this.code = code;
    }

    public static RealEstateInfoCode get(int code) {
        switch (code) {
            case 0x00:
                return SEND_DATA_REQ;
            case 0x01:
                return SEND_DATA_RES;
            case 0x02:
                return SEND_GRAPH_DATA_REQ;
            case 0x03:
                return SEND_GRAPH_DATA_RES;
            case 0x04:
                return REGION_SELECTION_REQ;
            case 0x05:
                return REGION_SELECTION_RES;
            case 0x06:
                return GRAPH_REGION_SELECTION_FIRST_REQ;
            case 0x07:
                return GRAPH_REGION_SELECTION_FIRST_RES;
            case 0x08:
                return GRAPH_REGION_SELECTION_SECOND_REQ;
            default:
                return GRAPH_REGION_SELECTION_SECOND_RES;

        }
    }
}
