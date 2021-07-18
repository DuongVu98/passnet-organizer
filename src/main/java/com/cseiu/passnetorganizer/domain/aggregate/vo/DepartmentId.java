package com.cseiu.passnetorganizer.domain.aggregate.vo;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentId implements Serializable {
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentId)) return false;
        DepartmentId that = (DepartmentId) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
