package fr.pandonia.uhcapi.utils.jnbt.v2;

import com.google.common.base.Preconditions;

public class IntArrayTag
        extends Tag {
    private final int[] value;

    public IntArrayTag(int[] value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    public int[] getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder hex = new StringBuilder();
        int[] value = this.value;
        int length = this.value.length;
        for (int i = 0; i < length; ++i) {
            int b = value[i];
            String hexDigits = Integer.toHexString(b).toUpperCase();
            if (hexDigits.length() == 1) {
                hex.append("0");
            }
            hex.append(hexDigits).append(" ");
        }
        return "TAG_Int_Array(" + hex + ")";
    }
}
