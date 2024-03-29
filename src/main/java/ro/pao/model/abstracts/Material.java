package ro.pao.model.abstracts;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Material implements Comparable<Material> {
    private UUID id;
    private LocalDateTime creationTime;
    private String title;
    private String description;
    private UUID courseId;

    @Override
    public int compareTo(Material o) {
        if (this.creationTime.isBefore(o.creationTime)) {
            return -1;
        } else if  (this.creationTime.isEqual(o.creationTime)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Material material = (Material) o;

        return id.equals(material.id) && creationTime.equals(material.creationTime)  && title.equals(material.title) && Objects.equals(description, material.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationTime, title, description);
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return this.title + " uploaded on " + this.creationTime.format(formatter) + ".\n";
    }
}
