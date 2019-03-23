package com.capsule.repository;

import com.capsule.domain.ExaminationReading;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExaminationReading entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExaminationReadingRepository extends JpaRepository<ExaminationReading, Long> {

}
