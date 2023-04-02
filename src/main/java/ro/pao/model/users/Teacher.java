package ro.pao.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.abstracts.User;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {
    private List<Discipline> teachCourses;
}
