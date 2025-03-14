package edu.bbte.idde.kzim2149.mapper;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePrebuiltPcDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPrebuiltPCDto;
import edu.bbte.idde.kzim2149.model.PrebuiltPc;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class PrebuiltPcMapper {
    public abstract GetPrebuiltPCDto modelToGetDto(PrebuiltPc model);

    @IterableMapping(elementTargetType = GetPrebuiltPCDto.class)
    public abstract Collection<GetPrebuiltPCDto> modelsToGetDtos(Collection<PrebuiltPc> models);

    @Mapping(target = "id", ignore = true)
    public abstract PrebuiltPc createDtoToModel(CreatePrebuiltPcDto dto);

    @IterableMapping(elementTargetType = PrebuiltPc.class)
    public abstract Collection<PrebuiltPc> createDtosToModels(Collection<GetPrebuiltPCDto> dtos);
}
