package edu.bbte.idde.kzim2149.dao.jdbc;

import edu.bbte.idde.kzim2149.dao.AbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.PCPartsDaoI;
import edu.bbte.idde.kzim2149.dao.PrebuiltPCDaoI;

public class JdbcAbstractDaoFactory extends AbstractDaoFactory {
    private static PCPartsDaoI pcPartsDao;
    private static PrebuiltPCDaoI prebuiltPCDao;

    @Override
    public synchronized PCPartsDaoI getPCPartsDao() {
        if (pcPartsDao == null) {
            pcPartsDao = new PCPartsDao();
        }
        return pcPartsDao;
    }

    @Override
    public synchronized PrebuiltPCDaoI getPrebuiltPCDao() {
        if (prebuiltPCDao == null) {
            prebuiltPCDao = new PrebuiltPCDao();
        }
        return prebuiltPCDao;
    }
}
