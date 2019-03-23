package com.capsule.repository;

import com.capsule.domain.Expert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Expert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

}
