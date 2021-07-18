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
public class Name implements Serializable {
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name = (Name) o;
        return getValue().equals(name.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
