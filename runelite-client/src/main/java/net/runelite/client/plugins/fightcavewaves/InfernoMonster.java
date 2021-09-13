package net.runelite.client.plugins.fightcavewaves;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum InfernoMonster implements WaveMonster
{
    JAL_NIB("Jal-Nib", WaveMonsterType.NIBBLER, 32, 3),
    JAL_MEJRAH("Jal-MejRah", WaveMonsterType.BAT, 85),
    JAL_AK("Jal-Ak", WaveMonsterType.BLOB, 165),
    JAL_IMKOT("Jal-ImKot", WaveMonsterType.MELEE, 240),
    JAL_XIL("Jal-Xil", WaveMonsterType.RANGER, 370),
    JAL_ZEK("Jal-Zek", WaveMonsterType.MAGE, 490),
    JALTOK_JAD("JalTok-Jad", WaveMonsterType.JAD, 900),
    TZKAL_ZUK("TzKal-Zuk", WaveMonsterType.ZUK, 1400);

    private final String name;
    private final WaveMonsterType type;
    private final int level;
    private final int countPerSpawn;

    InfernoMonster(final String name, final WaveMonsterType type, final int level)
    {
        this(name, type, level, 1);
    }
}
