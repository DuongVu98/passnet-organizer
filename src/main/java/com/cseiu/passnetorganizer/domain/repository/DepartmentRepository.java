package com.cseiu.passnetorganizer.domain.repository;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Department;
import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.aggregate.vo.DepartmentCode;
import com.cseiu.passnetorganizer.domain.aggregate.vo.DepartmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, DepartmentId> {

    @Query("select (count(d) > 0) from Department d where d.code = :code and d.organization = :organization")
    boolean existsDepartmentByCode(DepartmentCode code, Organization organization);
}
