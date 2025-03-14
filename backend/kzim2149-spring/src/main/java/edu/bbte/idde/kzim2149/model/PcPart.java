package edu.bbte.idde.kzim2149.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pcparts_jpa")
public class PcPart extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String producer;

    @Column(nullable = false, length = 255)
    private String type;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer weight;

    @OneToMany(mappedBy = "cpu")
    private List<PrebuiltPc> prebuiltPcs;

    @OneToMany(mappedBy = "gpu")
    private List<PrebuiltPc> prebuiltPcs1;

    @OneToMany(mappedBy = "ram")
    private List<PrebuiltPc> prebuiltPcs2;

    @OneToMany(mappedBy = "motherboard")
    private List<PrebuiltPc> prebuiltPcs3;

    @OneToMany(mappedBy = "psu")
    private List<PrebuiltPc> prebuiltPcs4;

    @OneToMany(mappedBy = "storage")
    private List<PrebuiltPc> prebuiltPcs5;

    public PcPart() {
        super();
    }

    private String checkNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    private Integer checkNull(Integer num) {
        if (num == null) {
            return 0;
        } else {
            return num;
        }
    }

    public PcPart(String name, String producer, String type, Integer price, Integer weight) {
        super();
        this.name = checkNull(name);
        this.producer = checkNull(producer);
        this.type = checkNull(type);
        this.price = checkNull(price);
        this.weight = checkNull(weight);
    }

    @Override
    public String toString() {
        return "PcPart{id=%d, name='%s', producer='%s', type='%s', price=%d, weight=%d}"
                .formatted(id, name, producer, type, price, weight);
    }
}
