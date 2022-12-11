package fr.pandonia.uhcapi.utils.jnbt.v2;

public class DoubleTag
        extends Tag {
    private final double value;

    public DoubleTag(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    public String toString() {
        return "TAG_Double(" + this.value + ")";
    }
}
