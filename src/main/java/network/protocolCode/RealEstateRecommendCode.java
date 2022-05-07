package network.protocolCode;

public enum RealEstateRecommendCode {
    RECORD_INFO_REQ((byte) 0x00),  RECORD_INFO_RES((byte) 0x01), REGION_RECOMMEND_REQ((byte) 0x02), REGION_RECOMMEND_RES((byte) 0x03);

    /** 3. 부동산 추천 ProtocolCode
     *
     * 3-1. 사용자가 원하는 조건에 맞는 부동산 추천
     * RECORD_INFO_REQ : 클라이언트가 서버에게 요청
     * RECORD_INFO_RES : 서버가 클라이언트에게 응답
     *
     * 3-2. 사용자가 원하는 조건에 맞는 부동산 지역 추천
     * REGION_RECOMMEND_REQ : 클라이언트가 서버에게 요청
     * REGION_RECOMMEND_RES : 서버가 클라이언트에게 응답
     *
     */

    private final byte code;

    public byte getCode() {
        return code;
    }

    RealEstateRecommendCode(byte code) {
        this.code = code;
    }

    public static RealEstateRecommendCode get(int code) {
        switch (code) {
            case 0x00:
                return RECORD_INFO_REQ;
            case 0x01:
                return RECORD_INFO_RES;
            case 0x02:
                return REGION_RECOMMEND_REQ;
            default:
                return REGION_RECOMMEND_RES;

        }
    }
}
