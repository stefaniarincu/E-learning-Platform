package ro.pao.model.users;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.users.abstracts.User;

@SuperBuilder
@Getter
@Setter
public class Teacher extends User {
}
