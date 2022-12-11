package fr.pandonia.uhcapi.utils.role;

public interface Use {
    boolean canUse(String... paramVarArgs);

    void use(String... paramVarArgs);
}
