package edu.bbte.idde.kzim2149.dao.mem;

import edu.bbte.idde.kzim2149.dao.PCPartsDaoI;
import edu.bbte.idde.kzim2149.model.PCPart;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class InMemoryPCPartsDao implements PCPartsDaoI {
    private final ConcurrentHashMap<Long, PCPart> dataBase;
    private final AtomicInteger id = new AtomicInteger(0);

    public InMemoryPCPartsDao() {
        dataBase = new ConcurrentHashMap<>();
    }

    @Override
    public PCPart insert(PCPart model) {
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
    public PCPart update(Long id, PCPart updatedModel) {
        dataBase.put(id, updatedModel);
        log.info("Updated: " + updatedModel);
        return updatedModel;
    }

    @Override
    public Collection<PCPart> findAll() {
        Collection<PCPart> list = new ArrayList<>();
        for (PCPart model : dataBase.values()) {
            list.add(model);
        }
        log.info("Found all: " + list);
        return list;
    }

    @Override
    public PCPart findById(Long id) {
        if (dataBase.containsKey(id)) {
            log.info("Found: " + id);
            return dataBase.get(id);
        }
        log.info("Search failed. Not found: " + id);
        return null;
    }

    @Override
    public Collection<PCPart> findByName(String name) {
        Collection<PCPart> pcParts = new ArrayList<>();
        for (PCPart model : dataBase.values()) {
            if (model.getName().equals(name)) {
                log.info("Found: " + name);
                pcParts.add(model);
            }
        }
        return pcParts;
    }
}
