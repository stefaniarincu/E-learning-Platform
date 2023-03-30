package ro.pao.model.materials.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.enums.Discipline;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public abstract class Material {
    private UUID id;
    private LocalDateTime creationTime;
    private Discipline discipline;
    private String title;
    private String description;
}
