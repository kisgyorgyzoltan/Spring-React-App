package edu.bbte.idde.kzim2149.dto.incoming;

import edu.bbte.idde.kzim2149.model.PcPart;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePrebuiltPcDto {

    private PcPart cpu;

    private PcPart gpu;

    private PcPart ram;

    private PcPart motherboard;

    private PcPart psu;
    
    private PcPart storage;
    @Positive
    private Integer price;

    public CreatePrebuiltPcDto(PcPart cpu, PcPart gpu, PcPart ram, PcPart motherboard, PcPart psu, PcPart storage) {
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.motherboard = motherboard;
        this.psu = psu;
        this.storage = storage;
        this.price = cpu.getPrice()
                + gpu.getPrice()
                + ram.getPrice()
                + motherboard.getPrice()
                + psu.getPrice()
                + storage.getPrice();
    }
}
