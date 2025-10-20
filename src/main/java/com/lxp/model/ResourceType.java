package com.lxp.model;

public enum ResourceType {
    VOD("vod"),
    FILE("file"),
    URL("url");

    private final String dbValue;

    ResourceType(String dbValue) {
        this.dbValue = dbValue;
    }

    /** ✅ 사용자 입력 문자열을 Enum으로 변환(대소문자/공백 허용, 허용 외 값이면 예외) */
    public static ResourceType fromInput(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 리소스 타입은 필수입니다. (url|vod|file)" + " 이전으로 돌아갑니다.");
        }
        String v = raw.trim();
        for (ResourceType rt : values()) {
            if (rt.dbValue.equalsIgnoreCase(v) || rt.name().equalsIgnoreCase(v)) {
                return rt;
            }
        }
        throw new IllegalArgumentException(
                "[ERROR] 리소스 타입은 url, vod, file 중 하나여야 합니다. 입력값 = " + raw + " 이전으로 돌아갑니다."
        );
    }

    /** (기존) DB 문자열을 Enum으로 변환 */
    public static ResourceType fromDb(String value) {
        if (value == null) return null;
        for (ResourceType rt : values()) {
            if (rt.dbValue.equalsIgnoreCase(value) || rt.name().equalsIgnoreCase(value)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("[ERROR] 리소스 타입이 다릅니다. " + value + " 이전으로 돌아갑니다.");
    }

    /** 필요하면 DB 저장용 문자열 */
    public String getDbValue() { return dbValue; }

    @Override public String toString() { return name(); }

    /** (선택) VOD 전용 검증에 쓰기 좋게 */
    public boolean isVod() { return this == VOD; }
}
