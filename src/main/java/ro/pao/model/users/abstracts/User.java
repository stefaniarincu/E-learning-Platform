package ro.pao.model.users.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
