package edu.bbte.idde.kzim2149.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PCPart extends BaseEntity {
    private String name;
    private String producer;
    private String type;
    private Integer price;
    private Integer weight;

    public PCPart() {
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

    public PCPart(String name, String producer, String type, Integer price, Integer weight) {
        super();
        this.name = checkNull(name);
        this.producer = checkNull(producer);
        this.type = checkNull(type);
        this.price = checkNull(price);
        this.weight = checkNull(weight);
    }
}
