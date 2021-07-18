package com.cseiu.passnetorganizer.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class StudentId implements Serializable {
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentId)) return false;
        StudentId studentId = (StudentId) o;
        return getValue().equals(studentId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
