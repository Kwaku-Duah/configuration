package com.cohort_management.Repository;

import com.cohort_management.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Long> {
    List<Cohort> findByCohortName(String cohortName);
}
