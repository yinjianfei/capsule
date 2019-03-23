package com.capsule.repository;

import com.capsule.domain.Examination;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Examination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

}
