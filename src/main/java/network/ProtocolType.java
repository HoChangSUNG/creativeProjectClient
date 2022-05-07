package network;

public enum ProtocolType {

    REAL_ESTATE_INFO((byte) 0x00),  REAL_ESTATE_COMPARE((byte) 0x01), REAL_ESTATE_RECOMMEND((byte) 0x02);
    /**
     * REAL_ESTATE_INFO : 1.지역별 부동산 가격 제공
     * REAL_ESTATE_COMPARE : 2.부동산 지역 비교
     * REAL_ESTATE_RECOMMEND : 3.부동산 추천
     */
    private final byte type;

    public byte getType() {
        return type;
    }

    ProtocolType(byte type) {
        this.type = type;
    }

    public static ProtocolType get(int type) {
        switch (type) {
            case 0x00:
                return REAL_ESTATE_INFO;
            case 0x01:
                return REAL_ESTATE_COMPARE;
            default:
                return REAL_ESTATE_RECOMMEND;

        }
    }
}
