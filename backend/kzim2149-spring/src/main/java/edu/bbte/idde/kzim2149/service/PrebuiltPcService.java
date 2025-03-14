package edu.bbte.idde.kzim2149.service;

import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;

import java.util.Collection;

public interface PrebuiltPcService {
    PrebuiltPc insert(PrebuiltPc entity);

    void delete(Long id);

    PrebuiltPc update(Long id, PrebuiltPc updatedEntity);

    Collection<PrebuiltPc> findAll();

    PrebuiltPc findById(Long id);

    PcPart getPartById(Long id, String partType);

    void deletePcPartThroughPrebuiltPc(Long id, String partType);


    PcPart updatePartById(Long id, String type, PcPart updatedPcPart);

    PcPart insertPartById(Long id, String type, PcPart pcPart);
}
