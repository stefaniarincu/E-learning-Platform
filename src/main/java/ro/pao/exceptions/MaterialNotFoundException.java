package ro.pao.exceptions;

public final class MaterialNotFoundException extends ObjectNotFoundException {
    public MaterialNotFoundException(String message) {
        super("Material not found exception!" + " --- " + message);
    }
}
