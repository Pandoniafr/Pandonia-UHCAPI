package fr.pandonia.uhcapi.utils.jnbt.v2;

public class IntTag
        extends Tag {
    private int value;

    public IntTag(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String toString() {
        return "TAG_Int(" + this.value + ")";
    }
}

