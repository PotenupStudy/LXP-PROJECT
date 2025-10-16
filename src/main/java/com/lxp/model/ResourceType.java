package com.lxp.model;

public enum ResourceType {
    VOD("vod"),
    FILE("file"),
    URL("url");

    private final String dbValue; // (내가 만든 변수) DB에 저장된 문자열 값 ex) "vod"

    // ✅ Enum 생성자
    // 각 상수(VOD, FILE, URL)가 만들어질 때 호출됨
    ResourceType(String dbValue) {
        this.dbValue = dbValue;
    }

    // (내가 만든 메서드) — DB에서 가져온 문자열을 Enum 객체로 변환
    public static ResourceType fromDb(String value) {
        if (value == null) return null;

        // ✅ [values()] — 자바가 Enum에 자동으로 만들어주는 정적 메서드
        // ResourceType.values() → [VOD, FILE, URL] 이런 배열을 리턴함
        for (ResourceType rt : values()) {

            // ✅ [rt] — 반복문 안에서 하나씩 꺼낸 Enum 객체를 담는 통 (예: VOD → FILE → URL)
            // ✅ [rt.dbValue] — 위에서 내가 만든 필드
            // ✅ [value] — fromDb 메서드의 매개변수 (DB 문자열 (vod, file, url))
            // ✅ equalsIgnoreCase() — 대소문자 구분 없이 비교
            if (rt.dbValue.equalsIgnoreCase(value) || rt.name().equalsIgnoreCase(value)) {
                    // ✅ [name()] — 자바가 Enum에 자동으로 만들어주는 메서드
                    // ResourceType.VOD.name() → "VOD" 문자열 리턴

                return rt; // 일치하면 Enum 상수(VOD, FILE, URL 중 하나) 리턴
            }
        }

        // 하나도 안 맞으면 예외 던짐
        throw new IllegalArgumentException("리소스 타입이 다릅니다. " + value);
    }

    public String getDbValue() {
        return dbValue;
    }

    // ✅ [name()] — Enum 상수 이름(VOD, FILE, URL)을 문자열로 반환
    @Override
    public String toString() {
        return name();
    }

}
