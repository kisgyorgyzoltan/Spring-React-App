package edu.bbte.idde.kzim2149.dto.outgoing;

import lombok.Data;

@Data
public class GetPcPartDto {
    private Long id;
    private String name;

    private String producer;

    private String type;

    private Integer price;

    private Integer weight;
}
