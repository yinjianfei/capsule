package com.capsule.repository;

import com.capsule.domain.CapsuleImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CapsuleImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapsuleImageRepository extends JpaRepository<CapsuleImage, Long> {

}
