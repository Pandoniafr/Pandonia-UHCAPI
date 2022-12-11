package fr.pandonia.uhcapi.utils.jnbt.v2;

public class ByteArrayTag
        extends Tag {
    private final byte[] value;

    public ByteArrayTag(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder hex = new StringBuilder();
        byte[] value = this.value;
        int length = this.value.length;
        for (int i = 0; i < length; ++i) {
            byte b = value[i];
            String hexDigits = Integer.toHexString(b).toUpperCase();
            if (hexDigits.length() == 1) {
                hex.append("0");
            }
            hex.append(hexDigits).append(" ");
        }
        return "TAG_Byte_Array(" + hex + ")";
    }
}
