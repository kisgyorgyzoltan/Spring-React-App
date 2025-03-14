package edu.bbte.idde.kzim2149.dao;

import edu.bbte.idde.kzim2149.model.PrebuiltPC;

import java.util.Collection;

public interface PrebuiltPCDaoI extends Dao<PrebuiltPC> {
    Collection<PrebuiltPC> findByPrice(Integer price);
}
