package edu.bbte.idde.kzim2149.dao.mem;

import edu.bbte.idde.kzim2149.dao.AbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.PCPartsDaoI;
import edu.bbte.idde.kzim2149.dao.PrebuiltPCDaoI;
import edu.bbte.idde.kzim2149.model.PCPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPC;

public class MemoryAbstractDaoFactory extends AbstractDaoFactory {
    private static PCPartsDaoI pcPartsDao;
    private static PrebuiltPCDaoI prebuiltPCDao;

    @Override
    public synchronized PrebuiltPCDaoI getPrebuiltPCDao() {
        if (prebuiltPCDao == null) {
            prebuiltPCDao = new InMemoryPrebuiltPCDao();
            // Inserting a prebuilt pc
            PCPartsDaoI pcPartsDao = getPCPartsDao();
            // 1 cpu
            PCPart cpu = pcPartsDao.findById(1L);
            // 1 gpu
            PCPart gpu = pcPartsDao.findById(2L);
            // 1 ram
            PCPart ram = pcPartsDao.findById(3L);
            // 1 motherboard
            PCPart motherboard = pcPartsDao.findById(4L);
            // 1 psu
            PCPart psu = pcPartsDao.findById(5L);
            // 1 storage
            PCPart storage = pcPartsDao.findById(6L);
            prebuiltPCDao.insert(new PrebuiltPC(cpu, gpu, ram, motherboard, psu, storage));
        }
        return prebuiltPCDao;
    }

    @Override
    public synchronized PCPartsDaoI getPCPartsDao() {
        if (pcPartsDao == null) {
            pcPartsDao = new InMemoryPCPartsDao();
            pcPartsDao.insert(new PCPart("Intel Core i7-10700K", "Intel", "CPU", 300, 0));
            // 1 gpu
            pcPartsDao.insert(new PCPart("Nvidia GeForce RTX 3080", "Nvidia", "GPU", 700, 0));
            // 1 ram
            pcPartsDao.insert(new PCPart("Corsair Vengeance RGB Pro 32GB", "Corsair", "RAM", 200, 0));
            // 1 motherboard
            pcPartsDao.insert(new PCPart("Asus ROG Strix Z490-E Gaming", "Asus", "Motherboard", 300, 0));
            // 1 psu
            pcPartsDao.insert(new PCPart("Corsair RM850x", "Corsair", "PSU", 150, 0));
            // 1 storage
            pcPartsDao.insert(new PCPart("Samsung 970 Evo Plus 1TB", "Samsung", "Storage", 150, 0));
        }
        return pcPartsDao;
    }

}
