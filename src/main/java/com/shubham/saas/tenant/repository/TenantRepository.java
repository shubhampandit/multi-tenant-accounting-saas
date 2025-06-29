package com.shubham.saas.tenant.repository;

import com.shubham.saas.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    boolean existsBySchemaName(String schemaName);
}
