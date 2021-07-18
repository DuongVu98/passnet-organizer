package com.cseiu.passnetorganizer.domain.repository;

import com.cseiu.passnetorganizer.domain.aggregate.entity.Organization;
import com.cseiu.passnetorganizer.domain.aggregate.vo.OrgId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, OrgId> {

}
