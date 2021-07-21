package com.cseiu.passnetorganizer.domain.repository;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.aggregate.entity.Student;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.StudentCardId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.StudentId;
import com.cseiu.passnetorganizer.domain.aggregate.vo.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, StudentId> {
    @Query("select (count(s) > 0) from Student s where s.cardId = :cardId and s.department.organization = :organization")
    boolean existStudent(StudentCardId cardId, Organization organization);

    @Query("select s from Student s where s.userId = ?1 and s.department.organization.id = ?2")
    Optional<Student> findStudentByUidAndOrgId(UserId userId, OrgId organization);

    Optional<Student> findStudentByUserId(UserId userId);
}
