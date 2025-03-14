package edu.bbte.idde.kzim2149.dto.incoming;

import edu.bbte.idde.kzim2149.model.PcPart;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

@Slf4j
@Data
public class CreatePromoDto {
    @NotNull
    @Length(max = 255, min = 1)
    private String name;

    @NotNull
    @Length(max = 255, min = 1)
    private String description;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer discount;

    private Integer price;

    @NotNull
    private PcPart pcPart;

    public CreatePromoDto(String name, String description, Integer discount, PcPart pcPart) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.price = pcPart.getPrice();
        log.info("New price: {}", this.price);
        this.pcPart = pcPart;
    }
}
