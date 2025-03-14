package edu.bbte.idde.kzim2149.service.implementation;

import edu.bbte.idde.kzim2149.dao.jpa.PcPartRepository;
import edu.bbte.idde.kzim2149.dao.jpa.PromoRepository;
import edu.bbte.idde.kzim2149.exception.NotFoundException;
import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.model.Promo;
import edu.bbte.idde.kzim2149.service.PromoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@Profile("jpa")
public class JpaPromoServiceImpl implements PromoService {
    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private PcPartRepository pcPartRepository;

    @Override
    public Promo insert(Promo entity) {
        Double newPrice = Double.valueOf(entity.getPcPart().getPrice());
        newPrice = newPrice - (newPrice * (entity.getDiscount() / 100.0));
        entity.setPrice((int) Math.ceil(newPrice));
        PcPart pcPart = pcPartRepository.findById(entity.getPcPart().getId()).orElse(null);
        if (pcPart == null) {
            log.error("PcPart not found with id: " + entity.getPcPart().getId());
            throw new NotFoundException("PcPart not found with id: " + entity.getPcPart().getId());
        }
        promoRepository.save(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        promoRepository.deleteById(id);
    }

    @Override
    public Promo update(Long id, Promo updatedEntity) {
        Promo promo;
        try {
            promo = promoRepository.findById(id).orElseThrow();
            promo.setName(updatedEntity.getName());
            promo.setDiscount(updatedEntity.getDiscount());
            promo.setDescription(updatedEntity.getDescription());
            promo.setPcPart(updatedEntity.getPcPart());
            promo.setPrice(updatedEntity.getPrice());
            promoRepository.save(promo);
            return promo;
        } catch (Exception e) {
            log.error("Promo not found with id: " + id);
            return null;
        }
    }

    @Override
    public Collection<Promo> findAll() {
        return promoRepository.findAll();
    }

    @Override
    public Promo findById(Long id) {
        Promo promo;
        try {
            promo = promoRepository.findById(id).orElseThrow();
            return promo;
        } catch (Exception e) {
            log.error("Promo not found with id: " + id);
            return null;
        }
    }

    @Override
    public Collection<Promo> findByName(String name) {
        return promoRepository.findByName(name);
    }
}
