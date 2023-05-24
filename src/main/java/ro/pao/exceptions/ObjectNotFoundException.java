package ro.pao.exceptions;

public sealed abstract class ObjectNotFoundException extends Exception permits MaterialNotFoundException, UserNotFoundException, CourseNotFoundException, GradeNotFoundException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}