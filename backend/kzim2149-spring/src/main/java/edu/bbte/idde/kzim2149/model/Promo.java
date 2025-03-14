package edu.bbte.idde.kzim2149.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "promos")
public class Promo extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private Integer price;

    @OneToOne
    private PcPart pcPart;

    public Promo() {
        super();
    }

    @Override
    public String toString() {
        return "Promo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", discount=" + discount +
                ", price=" + price +
                ", pcPart=" + pcPart +
                '}';
    }
}
