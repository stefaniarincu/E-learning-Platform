package ro.pao.model.materials;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SuperBuilder
@Getter
@Setter
public class Video extends Material {
    private LocalTime duration;

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        if (this.duration != null) {
            return "Time needed for this video " + this.duration.format(formatter) + " ---> " + super.toString();
        } else {
            return super.toString();
        }
    }
}
