package edu.bbte.idde.kzim2149.mapper;

import edu.bbte.idde.kzim2149.dto.incoming.CreatePromoDto;
import edu.bbte.idde.kzim2149.dto.outgoing.GetPromoDto;
import edu.bbte.idde.kzim2149.model.Promo;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class PromoMapper {
    public abstract GetPromoDto modelToGetDto(Promo model);

    @IterableMapping(elementTargetType = GetPromoDto.class)
    public abstract Collection<GetPromoDto> modelsToGetDtos(Collection<Promo> models);

    public abstract Promo createDtoToModel(CreatePromoDto dto);

    @IterableMapping(elementTargetType = Promo.class)
    public abstract Collection<Promo> createDtosToModels(Collection<CreatePromoDto> dtos);
}
