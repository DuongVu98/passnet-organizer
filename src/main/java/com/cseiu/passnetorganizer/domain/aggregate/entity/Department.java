package com.cseiu.passnetorganizer.domain.aggregate.entity;

import com.cseiu.passnetorganizer.domain.aggregate.vo.DepartmentCode;
import com.cseiu.passnetorganizer.domain.aggregate.vo.DepartmentId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Name;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private DepartmentId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private Name name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "department_code"))
    private DepartmentCode code;

    @ToString.Exclude
    @JoinColumn(name = "org_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
