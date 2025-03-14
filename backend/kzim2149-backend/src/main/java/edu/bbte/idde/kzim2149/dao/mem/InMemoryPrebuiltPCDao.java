package edu.bbte.idde.kzim2149.dao.mem;

import edu.bbte.idde.kzim2149.dao.PrebuiltPCDaoI;
import edu.bbte.idde.kzim2149.model.PrebuiltPC;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class InMemoryPrebuiltPCDao implements PrebuiltPCDaoI {
    private final ConcurrentHashMap<Long, PrebuiltPC> dataBase;
    private final AtomicInteger id = new AtomicInteger(0);

    public InMemoryPrebuiltPCDao() {
        dataBase = new ConcurrentHashMap<>();
    }

    @Override
    public PrebuiltPC insert(PrebuiltPC entity) {
        entity.setId((long) id.incrementAndGet());
        Long longId = (long) id.get();
        dataBase.put(longId, entity);
        log.info("Added: " + entity + " with id: " + id.get());
        id.incrementAndGet();
        return entity;
    }

    @Override
    public void delete(Long id) {
        dataBase.remove(id);
        log.info("Removed: " + id);
    }

    @Override
    public PrebuiltPC update(Long id, PrebuiltPC updatedEntity) {
        dataBase.put(id, updatedEntity);
        log.info("Updated: " + updatedEntity);
        return updatedEntity;
    }

    @Override
    public Collection<PrebuiltPC> findAll() {
        Collection<PrebuiltPC> list = dataBase.values();
        log.info("Found all: " + list);
        return list;
    }

    @Override
    public PrebuiltPC findById(Long id) {
        if (dataBase.containsKey(id)) {
            log.info("Found: " + id);
            return dataBase.get(id);
        }
        return null;
    }

    @Override
    public Collection<PrebuiltPC> findByPrice(Integer price) {
        Collection<PrebuiltPC> prebuiltPCS = new ArrayList<>();
        for (PrebuiltPC prebuiltPC : dataBase.values()) {
            if (prebuiltPC.getPrice().equals(price)) {
                log.info("Found: " + prebuiltPC);
                prebuiltPCS.add(prebuiltPC);
            }
        }
        return prebuiltPCS;
    }
}
