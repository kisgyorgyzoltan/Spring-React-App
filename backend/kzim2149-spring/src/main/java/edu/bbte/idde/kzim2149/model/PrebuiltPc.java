package edu.bbte.idde.kzim2149.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Locale;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "prebuiltpcs_jpa")
public class PrebuiltPc extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "cpu_id", referencedColumnName = "id")
    private PcPart cpu;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "gpu_id", referencedColumnName = "id")
    private PcPart gpu;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ram_id", referencedColumnName = "id")
    private PcPart ram;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "motherboard_id", referencedColumnName = "id")
    private PcPart motherboard;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "psu_id", referencedColumnName = "id")
    private PcPart psu;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private PcPart storage;

    private Integer price;

    public PcPart getPartByType(String type) {
        switch (type.toUpperCase(Locale.getDefault())) {
            case "CPU":
                return getCpu();
            case "GPU":
                return getGpu();
            case "RAM":
                return getRam();
            case "MOTHERBOARD":
                return getMotherboard();
            case "PSU":
                return getPsu();
            case "STORAGE":
                return getStorage();
            default:
                return null;
        }
    }

    public void setPartByType(PcPart part, String type) {
        switch (type.toUpperCase(Locale.getDefault())) {
            case "CPU":
                setCpu(part);
                break;
            case "GPU":
                setGpu(part);
                break;
            case "RAM":
                setRam(part);
                break;
            case "MOTHERBOARD":
                setMotherboard(part);
                break;
            case "PSU":
                setPsu(part);
                break;
            case "STORAGE":
                setStorage(part);
                break;
            default:
                break;
        }
    }
}
