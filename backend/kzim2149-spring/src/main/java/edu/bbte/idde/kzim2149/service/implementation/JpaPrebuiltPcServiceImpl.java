package edu.bbte.idde.kzim2149.service.implementation;

import edu.bbte.idde.kzim2149.exception.BadRequestException;
import edu.bbte.idde.kzim2149.exception.NotFoundException;
import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;
import edu.bbte.idde.kzim2149.dao.jpa.PcPartRepository;
import edu.bbte.idde.kzim2149.dao.jpa.PrebuiltPcRepository;
import edu.bbte.idde.kzim2149.service.PrebuiltPcService;
import edu.bbte.idde.kzim2149.validator.PrebuiltPcValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile("jpa")
public class JpaPrebuiltPcServiceImpl implements PrebuiltPcService {

    @Autowired
    private PrebuiltPcRepository prebuiltPcRepository;

    @Autowired
    private PcPartRepository pcPartRepository;

    @Autowired
    private PrebuiltPcValidator prebuiltPcValidator;

    @Override
    public PrebuiltPc insert(PrebuiltPc entity) {
        return prebuiltPcRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        prebuiltPcRepository.deleteById(id);
    }

    @Override
    public PrebuiltPc update(Long id, PrebuiltPc updatedEntity) {
        PrebuiltPc prebuiltPc = findById(id);
        prebuiltPc.setCpu(updatedEntity.getCpu());
        prebuiltPc.setGpu(updatedEntity.getGpu());
        prebuiltPc.setMotherboard(updatedEntity.getMotherboard());
        prebuiltPc.setPsu(updatedEntity.getPsu());
        prebuiltPc.setRam(updatedEntity.getRam());
        prebuiltPc.setStorage(updatedEntity.getStorage());
        return prebuiltPcRepository.save(prebuiltPc);
    }

    @Override
    public Collection<PrebuiltPc> findAll() {
        return prebuiltPcRepository.findAll();
    }

    @Override
    public PrebuiltPc findById(Long id) {
        return prebuiltPcRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Prebuilt PC not found with id: " + id)
        );
    }

    @Override
    public PcPart getPartById(Long id, String partType) {
        PrebuiltPc prebuiltPc = findById(id);
        PcPart pcPart = null;
        if ("cpu".equals(partType)) {
            pcPart = prebuiltPc.getCpu();
        } else if ("gpu".equals(partType)) {
            pcPart = prebuiltPc.getGpu();
        } else if ("ram".equals(partType)) {
            pcPart = prebuiltPc.getRam();
        } else if ("motherboard".equals(partType)) {
            pcPart = prebuiltPc.getMotherboard();
        } else if ("psu".equals(partType)) {
            pcPart = prebuiltPc.getPsu();
        } else if ("storage".equals(partType)) {
            pcPart = prebuiltPc.getStorage();
        }
        if (pcPart == null) {
            throw new NotFoundException("Part not found");
        } else {
            return pcPart;
        }

    }

    @Override
    public void deletePcPartThroughPrebuiltPc(Long id, String partType) {
        PrebuiltPc prebuiltPc = findById(id);
        switch (partType) {
            case "cpu":
                prebuiltPc.setCpu(null);
                break;
            case "gpu":
                prebuiltPc.setGpu(null);
                break;
            case "ram":
                prebuiltPc.setRam(null);
                break;
            case "motherboard":
                prebuiltPc.setMotherboard(null);
                break;
            case "psu":
                prebuiltPc.setPsu(null);
                break;
            case "storage":
                prebuiltPc.setStorage(null);
                break;
            default:
                throw new NotFoundException("Type not found");
        }
        prebuiltPcRepository.save(prebuiltPc);
        pcPartRepository.deleteById(id);
    }

    @Override
    public PcPart updatePartById(Long id, String type, PcPart updatedPcPart) {
        checkUpdatedPcPart(type, updatedPcPart);
        PrebuiltPc prebuiltPc = findById(id);
        if (prebuiltPc == null) {
            throw new NotFoundException("Prebuilt PC not found");
        }
        PcPart currentPcPart = prebuiltPc.getPartByType(type);
        validateCurrentPcPart(type, updatedPcPart, currentPcPart);
        updatedPcPart.setId(currentPcPart.getId());
        prebuiltPc.setPartByType(updatedPcPart, type);
        prebuiltPcRepository.save(prebuiltPc);
        pcPartRepository.save(updatedPcPart);
        return updatedPcPart;
    }

    private void validateCurrentPcPart(String type, PcPart updatedPcPart, PcPart currentPcPart) {
        if (currentPcPart == null) {
            throw new NotFoundException("Part not found");
        }
        if (!currentPcPart.getType().equalsIgnoreCase(updatedPcPart.getType())
                || !currentPcPart.getType().equalsIgnoreCase(type)
        ) {
            throw new NotFoundException("Incorrect type");
        }
        if (!prebuiltPcValidator.typeExists(type)) {
            throw new NotFoundException("Type not found");
        }
    }

    private void checkUpdatedPcPart(String type, PcPart updatedPcPart) {
        if (!prebuiltPcValidator.typeExists(type)) {
            throw new BadRequestException("Type not found");
        }
        if (!prebuiltPcValidator.validatePart(updatedPcPart)) {
            throw new BadRequestException("Invalid part");
        }
    }

    @Override
    public PcPart insertPartById(Long id, String type, PcPart pcPart) {
        PrebuiltPc prebuiltPc = findById(id);
        if (prebuiltPc == null) {
            throw new NotFoundException("Prebuilt PC not found");
        }
        if (!prebuiltPcValidator.typeExists(type)) {
            throw new NotFoundException("Type not found");
        }
        if (!prebuiltPcValidator.validatePart(pcPart)) {
            throw new NotFoundException("Invalid part");
        }
        prebuiltPc.setPartByType(pcPart, type);
        pcPartRepository.save(pcPart);
        prebuiltPcRepository.save(prebuiltPc);
        return pcPart;
    }
}
