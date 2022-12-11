package fr.pandonia.uhcapi.utils.jnbt;

public final class StringTag
        extends Tag {
    private final String value;

    public StringTag(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String toString() {
        String name = this.getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }
        return "TAG_String" + append + ": " + this.value;
    }
}
