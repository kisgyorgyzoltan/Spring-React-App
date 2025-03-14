package edu.bbte.idde.kzim2149.dto.incoming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreatePcPartDto implements Serializable {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String producer;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String type;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Positive
    private Integer weight;
}
