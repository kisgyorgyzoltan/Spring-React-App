package edu.bbte.idde.kzim2149.dao;

import edu.bbte.idde.kzim2149.model.PCPart;

import java.util.Collection;

public interface PCPartsDaoI extends Dao<PCPart> {
    Collection<PCPart> findByName(String name);
}
