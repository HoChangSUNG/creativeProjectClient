package network.protocolCode;

public enum RealEstateCompareCode {
    REAL_ESTATE_APARTMENT_REQ((byte) 0x00),  REAL_ESTATE_APARTMENT_RES((byte) 0x01),
    REAL_ESTATE_APARTMENT_INFO_REQ((byte) 0x02),  REAL_ESTATE_APARTMENT_INFO_RES((byte) 0x03);

    /** 2. 부동산 지역 비교 ProtocolCode
     *
     * 2-1. 사용자가 선택한 각 지역들을 서로 비교
     * REAL_ESTATE_COMPARE_REQ : 클라이언트가 서버에게 요청
     * REAL_ESTATE_COMPARE_RES : 서버가 클라이언트에게 응답
     *
     */

    private final byte code;

    public byte getCode() {
        return code;
    }

    RealEstateCompareCode(byte code) {
        this.code = code;
    }

    public static RealEstateCompareCode get(int code) {
        switch (code) {
            case 0x00:
                return REAL_ESTATE_APARTMENT_REQ;
            case 0x01:
                return REAL_ESTATE_APARTMENT_RES;
            case 0x02:
                return REAL_ESTATE_APARTMENT_INFO_REQ;
            default:
                return REAL_ESTATE_APARTMENT_INFO_RES;
        }
    }

}
