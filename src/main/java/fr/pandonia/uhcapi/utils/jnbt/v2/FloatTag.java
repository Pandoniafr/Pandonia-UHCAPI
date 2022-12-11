package fr.pandonia.uhcapi.utils.jnbt.v2;

public class FloatTag
        extends Tag {
    private final float value;

    public FloatTag(float value) {
        this.value = value;
    }

    @Override
    public Float getValue() {
        return Float.valueOf(this.value);
    }

    public String toString() {
        return "TAG_Float(" + this.value + ")";
    }
}
