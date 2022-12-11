package fr.pandonia.uhcapi.utils.jnbt;

public final class EndTag
        extends Tag {
    public EndTag() {
        super("");
    }

    @Override
    public Object getValue() {
        return null;
    }

    public String toString() {
        return "TAG_End";
    }
}
