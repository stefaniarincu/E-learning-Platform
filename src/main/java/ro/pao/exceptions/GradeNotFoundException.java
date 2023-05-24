package ro.pao.exceptions;

public final class GradeNotFoundException extends ObjectNotFoundException {
    public GradeNotFoundException(String message) {
        super("Grade not found exception!" + " --- " + message);
    }
}
