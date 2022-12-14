package fr.pandonia.uhcapi.utils.jnbt;

public abstract class Tag {
    private final String name;

    public Tag(String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public abstract Object getValue();
}

