package edu.bbte.idde.kzim2149.service;

import edu.bbte.idde.kzim2149.model.Promo;

import java.util.Collection;

public interface PromoService {
Promo insert(Promo entity);

void delete(Long id);

Promo update(Long id, Promo updatedEntity);

Collection<Promo> findAll();

Promo findById(Long id);

Collection<Promo> findByName(String name);
}
