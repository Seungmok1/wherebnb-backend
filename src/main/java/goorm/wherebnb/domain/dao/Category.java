package goorm.wherebnb.domain.dao;

import lombok.Getter;

@Getter
public enum Category {
    hot(1),
    bestView(2),
    nearBeach(3),
    countryside(4),
    landmark(5),
    tropical(6),
    goodDesign(7),
    hanok(8),
    coolPool(9),
    room(10),
    campsite(11),
    enterWithSki(12),
    golf(13),
    farm(14),
    extraordinary(15),
    wine(16),
    mansion(17),
    grandPiano(18),
    dessert(19),
    nationPark(20),
    container(21),
    extraSmall(22),
    treehouse(23),
    surfing(24),
    cabin(25),
    nearLake(26);
    private int value;

    Category(int value) {
        this.value = value;
    }
}
