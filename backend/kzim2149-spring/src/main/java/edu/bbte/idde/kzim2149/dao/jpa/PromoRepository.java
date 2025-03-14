package edu.bbte.idde.kzim2149.dao.jpa;

import edu.bbte.idde.kzim2149.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query("SELECT p FROM Promo p WHERE p.name = ?1")
    Collection<Promo> findByName(String name);
}
