package edu.bbte.idde.kzim2149.dao;

import edu.bbte.idde.kzim2149.model.PcPart;

import java.util.Collection;

public interface PcPartsDaoI extends Dao<PcPart> {
    Collection<PcPart> findByName(String name);
}
