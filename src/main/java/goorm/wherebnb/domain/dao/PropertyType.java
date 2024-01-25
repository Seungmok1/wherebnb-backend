package goorm.wherebnb.domain.dao;

public enum PropertyType {
    주택(1), 아파트(2), 별채(3), 호텔(4);
    private int value;

    PropertyType(int value) {
        this.value = value;
    }
}
