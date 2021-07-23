package com.cseiu.passnetorganizer.domain.aggregate.entity;

import com.cseiu.passnetorganizer.domain.aggregate.vo.Location;
import com.cseiu.passnetorganizer.domain.aggregate.vo.Name;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
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
@Table(name = "organization")
public class Organization extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private OrgId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private Name name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "location"))
    private Location location;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Department> departments = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Semester> semesters = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "teacherOrganization", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Student> nonStudents = new ArrayList<>();

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }

    public boolean containsDepartment(Department department) {
        return this.id.equals(department.getOrganization().getId());
    }
}
