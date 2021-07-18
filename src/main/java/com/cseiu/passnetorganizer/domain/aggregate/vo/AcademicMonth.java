package com.cseiu.passnetorganizer.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.stream.Stream;

@ToString
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum AcademicMonth {

    JANUARY("January", "Jan", 1),
    FEBRUARY("February", "Feb", 2),
    MARCH("March", "Mar", 3),
    APRIL("April", "Apr", 4),
    MAY("May", "May", 5),
    JUNE("June", "Jun", 6),
    JULY("July", "Jul", 7),
    AUGUST("August", "Aug", 8),
    SEPTEMBER("September", "Sep", 9),
    OCTOBER("October", "Oct", 10),
    NOVEMBER("November", "Nov", 11),
    DECEMBER("December", "Dec", 12);

    String name;
    String acronym;
    Integer index;

    public static AcademicMonth findMonth(int index) {
        return Stream.of(AcademicMonth.values())
           .filter(month -> month.index == index).findFirst()
           .orElseThrow(IllegalArgumentException::new);
    }
}
