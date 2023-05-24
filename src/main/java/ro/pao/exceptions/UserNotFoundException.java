package ro.pao.exceptions;
public final class UserNotFoundException extends ObjectNotFoundException {
    public UserNotFoundException(String message ) {
         super("User not found exception!" + " --- " + message);
    }
}
