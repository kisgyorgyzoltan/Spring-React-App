package edu.bbte.idde.kzim2149.service;

import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;

import java.util.Collection;

public interface PcPartService {
    PcPart insert(PcPart entity);

    void delete(Long id);

    PcPart update(Long id, PcPart updatedEntity);

    Collection<PcPart> findAll();

    PcPart findById(Long id);

    Collection<PcPart> findByName(String name);

    Collection<PrebuiltPc> findPrebuiltPcByPartId(Long id);

    Collection<PcPart> findBySpecification(String producer, String type, Integer maxPrice, Integer minPrice);
}
