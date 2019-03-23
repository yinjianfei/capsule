package com.capsule.repository;

import com.capsule.domain.Capsule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Capsule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {

}
