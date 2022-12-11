package fr.pandonia.uhcapi.config.common;

public enum GameAccess {
    OPEN("§aOuvert"),
    CLOSE("§cFermé");

    private final String message;

    private GameAccess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
