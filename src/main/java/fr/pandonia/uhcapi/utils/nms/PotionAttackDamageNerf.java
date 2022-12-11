package fr.pandonia.uhcapi.utils.nms;

import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MobEffectAttackDamage;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class PotionAttackDamageNerf extends MobEffectAttackDamage {
    public PotionAttackDamageNerf(int i, MinecraftKey minecraftKey, boolean b, int i1) {
        super(i, minecraftKey, b, i1);
    }

    public double a(int id, AttributeModifier modifier) {
        double result = super.a(id, modifier);
        if (this.id == MobEffectList.INCREASE_DAMAGE.id)
            result /= 5.0D;
        return result;
    }
}

