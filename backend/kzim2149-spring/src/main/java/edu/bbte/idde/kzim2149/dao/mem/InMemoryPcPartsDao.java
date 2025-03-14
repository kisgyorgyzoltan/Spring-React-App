package edu.bbte.idde.kzim2149.dao.mem;

import edu.bbte.idde.kzim2149.dao.PcPartsDaoI;
import edu.bbte.idde.kzim2149.model.PcPart;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("dev")
@Slf4j
public class InMemoryPcPartsDao implements PcPartsDaoI {
    private final ConcurrentHashMap<Long, PcPart> dataBase;
    private final AtomicInteger id = new AtomicInteger(0);

    public InMemoryPcPartsDao() {
        dataBase = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void init() {
        log.info("InMemoryPcPartsDao created");
        insert(new PcPart("Intel i7-9700K", "Intel", "CPU", 8, 3));
        insert(new PcPart("AMD Ryzen 5 3600", "AMD", "CPU", 6, 3));
        insert(new PcPart("Nvidia RTX 2080 Ti", "Nvidia", "GPU", 11, 3));
        insert(new PcPart("Nvidia RTX 2070 Super", "Nvidia", "GPU", 8, 3));
    }

    @Override
    public PcPart insert(PcPart model) {
        model.setId((long) id.get());
        Long longId = (long) id.get();
        dataBase.put(longId, model);
        log.info("Added: " + model + " with id: " + id.get());
        id.incrementAndGet();
        return model;
    }

    @Override
    public void delete(Long id) {
        dataBase.remove(id);
        log.info("Removed: " + id);
    }

    @Override
    public PcPart update(Long id, PcPart updatedModel) {
        dataBase.put(id, updatedModel);
        log.info("Updated: " + updatedModel);
        return updatedModel;
    }

    @Override
    public Collection<PcPart> findAll() {
        Collection<PcPart> list = new ArrayList<>();
        for (PcPart model : dataBase.values()) {
            list.add(model);
        }
        log.info("Found all: " + list);
        return list;
    }

    @Override
    public PcPart findById(Long id) {
        if (dataBase.containsKey(id)) {
            log.info("Found: " + id);
            return dataBase.get(id);
        }
        log.info("Search failed. Not found: " + id);
        return null;
    }

    @Override
    public Collection<PcPart> findByName(String name) {
        Collection<PcPart> pcParts = new ArrayList<>();
        for (PcPart model : dataBase.values()) {
            if (model.getName().equals(name)) {
                log.info("Found: " + name);
                pcParts.add(model);
            }
        }
        return pcParts;
    }
}
