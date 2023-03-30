package ro.pao.model.users.abstracts;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public abstract class User {
    private UUID id;
    private String firstName;
    public String lastName;
    public String email;
    public String password;

    public List<Material> materials;
}
