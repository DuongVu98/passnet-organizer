package com.cseiu.passnetorganizer.domain.repository;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Semester;
import com.cseiu.passnetorganizer.domain.aggregate.vo.SemesterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, SemesterId> {
}
