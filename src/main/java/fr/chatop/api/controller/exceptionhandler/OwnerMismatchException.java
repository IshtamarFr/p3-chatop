package fr.chatop.api.controller.exceptionhandler;

public class OwnerMismatchException extends RuntimeException{
    public OwnerMismatchException() {
        super("Owner doesn't match User");
    }
}
