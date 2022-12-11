package fr.pandonia.uhcapi.common.rules;

import fr.pandonia.uhcapi.API;

public interface Rule {
    default public void onLoad(API main) {
    }
}
