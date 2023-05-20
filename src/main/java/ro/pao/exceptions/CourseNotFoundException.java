package ro.pao.exceptions;

public final class CourseNotFoundException extends ObjectNotFoundException {
    public CourseNotFoundException(String message) {
        super("Course not found exception!" + " --- " + message);
    }
}
