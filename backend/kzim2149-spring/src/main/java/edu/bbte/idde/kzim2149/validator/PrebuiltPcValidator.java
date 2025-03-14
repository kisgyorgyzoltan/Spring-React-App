package edu.bbte.idde.kzim2149.validator;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePrebuiltPcDto;
import edu.bbte.idde.kzim2149.exception.BadRequestException;
import edu.bbte.idde.kzim2149.model.PcPart;
import edu.bbte.idde.kzim2149.dao.jpa.PcPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Profile("jpa")
@Component
public class PrebuiltPcValidator {
    @Autowired
    private PcPartRepository pcPartRepository;

    public static final List<String> PART_TYPES = List.of(
            new String[]{"CPU", "GPU", "RAM", "MOTHERBOARD", "PSU", "STORAGE"}
    );

    public boolean validatePart(PcPart part) {
        return part != null
                && part.getName() != null
                && part.getProducer() != null
                && part.getType() != null
                && part.getPrice() != null
                && part.getWeight() != null
                && part.getName().length() >= 3
                && part.getName().length() <= 255
                && part.getProducer().length() >= 3
                && part.getProducer().length() <= 255
                && part.getType().length() >= 3
                && part.getType().length() <= 255
                && part.getPrice() >= 0
                && part.getWeight() >= 0;
    }

    public void validate(CreatePrebuiltPcDto dto) {
        if (validateParts(dto)) {
            throw new BadRequestException("One or more of the parts are invalid");
        }

        if (existsParts(dto)) {
            throw new BadRequestException("One or more of the parts does not exist");
        }
    }

    private boolean existsParts(CreatePrebuiltPcDto dto) {
        return !(pcPartRepository.existsById(dto.getCpu().getId())
                && pcPartRepository.existsById(dto.getGpu().getId())
                && pcPartRepository.existsById(dto.getRam().getId())
                && pcPartRepository.existsById(dto.getMotherboard().getId())
                && pcPartRepository.existsById(dto.getPsu().getId())
                && pcPartRepository.existsById(dto.getStorage().getId()));
    }

    private boolean validateParts(CreatePrebuiltPcDto dto) {
        return !validatePart(dto.getCpu())
                || !validatePart(dto.getGpu())
                || !validatePart(dto.getRam())
                || !validatePart(dto.getMotherboard())
                || !validatePart(dto.getPsu())
                || !validatePart(dto.getStorage());
    }

    public boolean typeExists(String type) {
        return PART_TYPES.contains(type.toUpperCase(Locale.ROOT));
    }
}
