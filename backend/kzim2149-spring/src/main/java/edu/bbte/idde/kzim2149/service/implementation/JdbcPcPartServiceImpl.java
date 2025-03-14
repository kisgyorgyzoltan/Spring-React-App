package edu.bbte.idde.kzim2149.service.implementation;

import edu.bbte.idde.kzim2149.dao.PcPartsDaoI;
import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;
import edu.bbte.idde.kzim2149.service.PcPartService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Profile({"prod", "dev"})
@Slf4j
public class JdbcPcPartServiceImpl implements PcPartService {
    @Autowired
    PcPartsDaoI pcPartsDao;

    @PostConstruct
    public void init() {
        log.info("JdbcPcPartsServiceImpl created");
    }

    @Override
    public PcPart insert(PcPart entity) {
        pcPartsDao.insert(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        pcPartsDao.delete(id);
    }

    @Override
    public PcPart update(Long id, PcPart updatedEntity) {
        pcPartsDao.update(id, updatedEntity);
        return updatedEntity;
    }

    @Override
    public Collection<PcPart> findAll() {
        return pcPartsDao.findAll();
    }

    @Override
    public PcPart findById(Long id) {
        return pcPartsDao.findById(id);
    }

    @Override
    public Collection<PcPart> findByName(String name) {
        return pcPartsDao.findByName(name);
    }

    @Override
    public Collection<PrebuiltPc> findPrebuiltPcByPartId(Long id) {
        return new ArrayList<>();
    }

    @Override
    public Collection<PcPart> findBySpecification(String producer, String type, Integer maxPrice, Integer minPrice) {
        return null;
    }

}
