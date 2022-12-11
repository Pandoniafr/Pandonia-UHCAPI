package fr.pandonia.uhcapi.utils.jnbt.v2;

public class ShortTag
        extends Tag {
    private final short value;

    public ShortTag(short value) {
        this.value = value;
    }

    @Override
    public Short getValue() {
        return this.value;
    }

    public String toString() {
        return "TAG_Short(" + this.value + ")";
    }
}