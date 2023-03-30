package ro.pao.model.materials;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;

import java.time.LocalTime;

@SuperBuilder
@Getter
@Setter
public class Video extends Material {
    private LocalTime duration;
}
