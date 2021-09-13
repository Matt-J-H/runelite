package net.runelite.client.plugins.fightcavewaves;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("fightcavewaves")
public interface FightCaveWavesConfig extends Config
{
    @ConfigItem(
            keyName = "waveDisplay",
            name = "Wave display",
            description = "Shows monsters that will spawn on the selected wave(s)."
    )
    default WaveDisplayMode waveDisplay()
    {
        return WaveDisplayMode.BOTH;
    }

    @ConfigItem(
            keyName = "commonNames",
            name = "Use common names",
            description = "Display common names for TzHaar wave monsters, e.g. 'Nibbler', 'Bat', 'Mage'"
    )
    default boolean commonNames()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showMonsterLevel",
            name = "Show monster level",
            description = "Show the level of the monster"
    )
    default boolean showMonsterLevel()
    {
        return true;
    }
}
