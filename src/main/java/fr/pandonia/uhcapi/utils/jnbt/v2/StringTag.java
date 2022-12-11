package fr.pandonia.uhcapi.utils.jnbt.v2;

import com.google.common.base.Preconditions;

public class StringTag
        extends Tag {
    private final String value;

    public StringTag(String value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "TAG_String(" + this.value + ")";
    }
}
