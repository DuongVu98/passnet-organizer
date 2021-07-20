package com.cseiu.passnetorganizer.domain.aggregate.entity;


import com.cseiu.passnetorganizer.domain.aggregate.vo.ProfileId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.StudentCardId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.StudentId;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private StudentId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "card_id"))
    private StudentCardId cardId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "profile_id"))
    private ProfileId profileId;

    @ToString.Exclude
    @JoinColumn(name = "department_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;
}
