package edu.bbte.idde.kzim2149.dto.outgoing;

import lombok.Data;

@Data
public class GetPrebuiltPCDto {
    private Long id;

    private GetPcPartDto cpu;

    private GetPcPartDto gpu;

    private GetPcPartDto ram;

    private GetPcPartDto motherboard;

    private GetPcPartDto psu;

    private GetPcPartDto storage;

    private Integer price;
}
