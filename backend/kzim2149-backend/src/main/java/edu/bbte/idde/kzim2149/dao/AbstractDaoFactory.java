package edu.bbte.idde.kzim2149.dao;

import edu.bbte.idde.kzim2149.dao.jdbc.JdbcAbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.mem.MemoryAbstractDaoFactory;

public abstract class AbstractDaoFactory {
    private static AbstractDaoFactory INSTANCE;
    public static final String IN_MEMORY = "in-memory";
    public static final String JDBC = "jdbc";

    public static synchronized AbstractDaoFactory getInstance() {
        if (INSTANCE == null) {
            String daoType;
            String profile = System.getenv("PROFILE");
            if (profile == null) {
                daoType = AbstractDaoFactory.JDBC;
            } else {
                daoType = "prod".equals(profile) ? AbstractDaoFactory.JDBC : AbstractDaoFactory.IN_MEMORY;
            }
            if (AbstractDaoFactory.IN_MEMORY.equals(daoType)) {
                INSTANCE = new MemoryAbstractDaoFactory();
            } else {
                INSTANCE = new JdbcAbstractDaoFactory();
            }
        }
        return INSTANCE;
    }

    public abstract PCPartsDaoI getPCPartsDao();

    public abstract PrebuiltPCDaoI getPrebuiltPCDao();
}
