package net.runelite.client.plugins.deomicgorillas;

import net.runelite.api.AnimationID;
import net.runelite.api.Prayer;

public enum DemonicGorillasAttack
{
    MAGIC(AnimationID.DEMONIC_GORILLA_MAGIC_ATTACK, Prayer.PROTECT_FROM_MAGIC),
    RANGE(AnimationID.DEMONIC_GORILLA_RANGED_ATTACK, Prayer.PROTECT_FROM_MISSILES),
    MELEE(AnimationID.DEMONIC_GORILLA_MELEE_ATTACK, Prayer.PROTECT_FROM_MELEE);

    private final int animation;
    private final Prayer prayer;

    DemonicGorillasAttack(int animation, Prayer prayer)
    {
        this.animation = animation;
        this.prayer = prayer;
    }

    public int getAnimation()
    {
        return animation;
    }

    public Prayer getPrayer()
    {
        return prayer;
    }
}
