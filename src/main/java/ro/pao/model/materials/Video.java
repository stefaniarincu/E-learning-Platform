package ro.pao.model.materials;

import ro.pao.model.materials.abstracts.Material;

import java.sql.Time;

public class Video extends Material {
    private Time duration;

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }
}
