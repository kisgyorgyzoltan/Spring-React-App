package edu.bbte.idde.kzim2149.service.implementation;

import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;
import edu.bbte.idde.kzim2149.dao.jpa.PcPartRepository;
import edu.bbte.idde.kzim2149.service.PcPartService;
import edu.bbte.idde.kzim2149.specification.PcPartSpecification;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@Slf4j
@Profile("jpa")
public class JpaPcPartServiceImpl implements PcPartService {
    @Autowired
    private PcPartRepository pcPartsRepository;

    @PostConstruct
    public void init() {
        log.info("JpaPcPartsServiceImpl created");
    }

    @Override
    public PcPart insert(PcPart entity) {
        pcPartsRepository.save(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        pcPartsRepository.deleteById(id);
    }

    @Override
    public PcPart update(Long id, PcPart updatedEntity) {
        PcPart pcPart;
        try {
            pcPart = pcPartsRepository.findById(id).orElseThrow();
            pcPart.setName(updatedEntity.getName());
            pcPart.setPrice(updatedEntity.getPrice());
            pcPart.setProducer(updatedEntity.getProducer());
            pcPart.setType(updatedEntity.getType());
            pcPart.setWeight(updatedEntity.getWeight());
            pcPartsRepository.save(pcPart);
            return pcPart;
        } catch (NoSuchElementException e) {
            log.error("PCPart not found with id: " + id);
            return null;
        }
    }

    @Override
    public Collection<PcPart> findAll() {
        return pcPartsRepository.findAll();
    }

    @Override
    public PcPart findById(Long id) {
        PcPart pcPart = null;
        try {
            pcPart = pcPartsRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("PCPart not found with id: " + id);
        }
        return pcPart;
    }

    @Override
    public Collection<PcPart> findByName(String name) {
        return pcPartsRepository.findByName(name);
    }

    @Override
    public Collection<PrebuiltPc> findPrebuiltPcByPartId(Long id) {
        try {
            PcPart pcPart = pcPartsRepository.findById(id).orElseThrow();
            return new ArrayList<>(pcPart.getPrebuiltPcs());
        } catch (NoSuchElementException e) {
            log.error("PCPart not found with id: " + id);
            return null;
        }
    }

    @Override
    public Collection<PcPart> findBySpecification(String producer, String type, Integer maxPrice, Integer minPrice) {
        Specification<PcPart> specification = PcPartSpecification.filterByThreeCriteria(producer, type, maxPrice, minPrice);
        return pcPartsRepository.findAll(specification);
    }
}
