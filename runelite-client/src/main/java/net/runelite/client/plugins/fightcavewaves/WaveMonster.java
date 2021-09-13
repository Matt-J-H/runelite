package net.runelite.client.plugins.fightcavewaves;

import net.runelite.client.util.Text;

interface WaveMonster
{
    String getName();
    WaveMonsterType getType();
    int getLevel();
    int getCountPerSpawn();

    default String displayString(final boolean commonName, final boolean showMonsterLevel)
    {
        final StringBuilder sb = new StringBuilder();

        if (commonName)
        {
            sb.append(Text.titleCase(getType()));
        }
        else
        {
            sb.append(getName());
        }

        if (showMonsterLevel)
        {
            sb.append(" - Level ");
            sb.append(getLevel());
        }

        return sb.toString();
    }
}
