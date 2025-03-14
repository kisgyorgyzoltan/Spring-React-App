package edu.bbte.idde.kzim2149.dao.jpa;

import edu.bbte.idde.kzim2149.model.PcPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PcPartRepository extends JpaRepository<PcPart, Long>, JpaSpecificationExecutor<PcPart> {
    @Query("SELECT p FROM PcPart p WHERE p.name = ?1")
    Collection<PcPart> findByName(String name);
}
