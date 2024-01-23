package goorm.wherebnb.domain.dao;

import lombok.Getter;

@Getter
public enum Amenity {
    WIFI(1,"무선 인터넷"),
    KITCHEN(2, "주방"),
    WASHER(3,"세탁기"),
    DRYER(4,"건조기"),
    AIR_CONDITIONER(5,"에어컨"),
    HEATING_SYSTEM(6,"난방"),
    WORK_ONLY_SPACE(7,"업무 전용 공간"),
    TV(8,"TV"),
    HAIR_DRYER(9,"헤어 드라이어"),
    IRON(10,"다리미");

    private int amenityId;
    private String amenityName;

    Amenity(int amenityId, String amenityName) {
        this.amenityId = amenityId;
        this.amenityName = amenityName;
    }
}
