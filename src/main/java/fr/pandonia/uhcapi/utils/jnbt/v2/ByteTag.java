package fr.pandonia.uhcapi.utils.jnbt.v2;

public class ByteTag
        extends Tag {
    private final byte value;

    public ByteTag(byte value) {
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return this.value;
    }

    public String toString() {
        return "TAG_Byte(" + this.value + ")";
    }
}
