package ro.pao.model.sealed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public sealed abstract class User permits Teacher, Student{
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " with the email: " + this.email;
    }
}
