package edu.bbte.idde.kzim2149.mapper;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePcPartDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPcPartDto;
import edu.bbte.idde.kzim2149.model.PcPart;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class PcPartMapper {
    public abstract GetPcPartDto modelToGetDto(PcPart model);

    @IterableMapping(elementTargetType = GetPcPartDto.class)
    public abstract Collection<GetPcPartDto> modelsToGetDtos(Collection<PcPart> models);

    public abstract PcPart createDtoToModel(CreatePcPartDto dto);

    @IterableMapping(elementTargetType = PcPart.class)
    public abstract Collection<PcPart> createDtosToModels(Collection<CreatePcPartDto> dtos);
}
