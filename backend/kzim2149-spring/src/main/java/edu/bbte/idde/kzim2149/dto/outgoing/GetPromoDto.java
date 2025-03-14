package edu.bbte.idde.kzim2149.dto.outgoing;

import lombok.Data;

@Data
public class GetPromoDto {
    private Long id;
    private String name;
    private String description;
    private Integer discount;
    private Integer price;
    private GetPcPartDto pcPart;
}
