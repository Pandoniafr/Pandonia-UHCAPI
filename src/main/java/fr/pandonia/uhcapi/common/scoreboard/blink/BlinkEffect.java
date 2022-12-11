package fr.pandonia.uhcapi.common.scoreboard.blink;

public class BlinkEffect {
    private int count = 0;
    private boolean back = false;
    String text = "mc.pandonia.fr";

    public void next() {
        if (this.count == 0) {
            this.text = "§cmc.pandonia.fr";
        }
        if (this.count == 1) {
            this.text = "§f§c§4m§cc§f.§cpandonia.fr"; //§f§4m§fc§c.pandonia.fr
        }
        if (this.count == 2) {
            this.text = "§f§cm§4c§c.§fp§candonia.fr";//§fm§4c§f.§cpandonia.fr
        }
        if (this.count == 3) {
            this.text = "§fm§cc§4.§cp§fa§cndonia.fr";//§cm§fc§4.§fp§candonia.fr
        }
        if (this.count == 4) {
            this.text = "§cm§fc§c.§4p§ca§fn§cdonia.fr";//§cmc§f.§4p§fa§cndonia.fr
        }
        if (this.count == 5) {
            this.text = "§cmc§f.§cp§4a§cn§fd§conia.fr";//§cmc.§fp§4a§fn§cdonia.fr
        }
        if (this.count == 6) {
            this.text = "§cmc.§fp§ca§4n§cd§fo§cnia.fr";//§cmc.p§fa§4n§fd§conia.fr
        }
        if (this.count == 7) {
            this.text = "§cmc.p§fa§cn§4d§co§fn§cia.fr";//§cmc.pa§fn§4d§fo§cnia.fr
        }
        if (this.count == 8) {
            this.text = "§cmc.pa§fn§cd§4o§cn§fi§ca.fr";//§cmc.pan§fd§4o§fn§cia.fr
        }
        if (this.count == 9) {
            this.text = "§cmc.pan§fd§co§4n§ci§fa§c.fr";//§mc.pand§fo§4n§fi§ca.fr
        }
        if (this.count == 10) {
            this.text = "§cmc.pand§fo§cn§4i§ca§f.§cfr";//§cmc.pando§fn§4i§fa§c.fr
        }
        if (this.count == 11) {
            this.text = "§cmc.pando§fn§ci§4a§c.§ff§cr";//§cmc.pandon§fi§4a§f.§cfr
        }
        if (this.count == 12) {
            this.text = "§cmc.pandon§fi§ca§4.§cf§fr§c";//§cmc.pandoni§fa§4.§ff§cr
        }
        if (this.count == 13) {
            this.text = "§cmc.pandoni§fa§c.§4f§cr§f§c";//§mc.pandonia§f.§4f§fr§c
        }
        if (this.count == 14) {
            this.text = "§cmc.pandonia§f.§cf§4r§c§f§c";//§cmc.pandonia.§ff§4r§c
        }
        if (this.count == 15) {
            this.text = "§cmc.pandonia.§ff§cr";//§cmc.pandonia.f§fr§4§c
        }
        if (this.count == 16) {
            this.text = "§cmc.pandonia.f§fr§c";//§cmc.pandonia.fr
        }
        if (this.count == 17) {
            this.text = "§cmc.pandonia.f§fr§c";
        }
        if (this.count == 18) {
            this.text = "§cmc.pandonia.fr";
        }
        if (this.count == 19) {
            this.back = true;
        }
        if (this.count == -10) {
            this.back = false;
            this.count = 0;
        }
        this.count = !this.back ? ++this.count : --this.count;
    }

    public String getText() {
        return this.text;
    }
}