package ro.pao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Material;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Video extends Material {
    private LocalTime duration;

    public Video() {

    }

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
