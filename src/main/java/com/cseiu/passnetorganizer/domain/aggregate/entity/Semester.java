package com.cseiu.passnetorganizer.domain.aggregate.entity;

import com.cseiu.passnetorganizer.domain.aggregate.vo.AcademicMonth;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Name;
import com.cseiu.passnetorganizer.domain.aggregate.vo.SemesterId;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "semester")
public class Semester extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private SemesterId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private Name name;

    @Column(name = "start_month")
    @Enumerated(EnumType.STRING)
    private AcademicMonth startMonth;

    @Column(name = "end_month")
    @Enumerated(EnumType.STRING)
    private AcademicMonth endMonth;

    @ToString.Exclude
    @JoinColumn(name = "org_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;
}
