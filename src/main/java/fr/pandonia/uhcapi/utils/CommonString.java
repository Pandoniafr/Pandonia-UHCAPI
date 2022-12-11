package fr.pandonia.uhcapi.utils;

public enum CommonString {
    CLICK_HERE_TO_APPLY(" §8» §fCliquez pour §aappliquer§f."),
    CLICK_HERE_TO_ACTIVATE(" §8» §fCliquez pour §aactiver§f."),
    CLICK_HERE_TO_DESACTIVATE(" §8» §fCliquez pour §cdésactiver§f."),
    CLICK_HERE_TO_MODIFY(" §8» §fCliquez pour §6modifier§f."),
    CLICK_HERE_TO_ACCESS(" §8» §fCliquez pour y §caccéder§f."),
    BAR("§f§m                                                                          §r"),
    NO_PERMISSION("§cVous n'avez pas la permission.");

    private String message;

    CommonString(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
