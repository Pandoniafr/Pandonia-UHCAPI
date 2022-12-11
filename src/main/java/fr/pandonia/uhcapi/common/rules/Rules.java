package fr.pandonia.uhcapi.common.rules;

import fr.pandonia.uhcapi.API;

public class Rules {
    public static final PvP pvp = new PvP();
    public static final NoDamage noDamage = new NoDamage();

    public static void load(API main) {
        pvp.onLoad(main);
        noDamage.onLoad(main);
    }
}
