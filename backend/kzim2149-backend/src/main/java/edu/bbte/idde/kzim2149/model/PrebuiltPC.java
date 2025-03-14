package edu.bbte.idde.kzim2149.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.stream.Stream;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PrebuiltPC extends BaseEntity {
    private PCPart cpu;
    private PCPart gpu;
    private PCPart ram;
    private PCPart motherboard;
    private PCPart psu;
    private PCPart storage;
    private Integer price;

    public PrebuiltPC(PCPart cpu, PCPart gpu, PCPart ram, PCPart motherboard, PCPart psu, PCPart storage) {
        super();
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.motherboard = motherboard;
        this.psu = psu;
        this.storage = storage;
        this.price = Stream.of(cpu, gpu, ram, motherboard, psu, storage).mapToInt(PCPart::getPrice).sum();
    }

    public PrebuiltPC() {
        super();
    }
}
