package fr.pandonia.uhcapi.utils.jnbt.v2;

public class LongTag
        extends Tag {
    private final long value;

    public LongTag(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    public String toString() {
        return "TAG_Long(" + this.value + ")";
    }
}
