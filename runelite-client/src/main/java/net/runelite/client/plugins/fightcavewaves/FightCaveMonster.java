package net.runelite.client.plugins.fightcavewaves;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum FightCaveMonster implements WaveMonster
{
    TZ_KIH("Tz-Kih", WaveMonsterType.BAT, 22),
    TZ_KEK("Tz-Kek", WaveMonsterType.BLOB, 45),
    TOK_XIL("Tok-Xil", WaveMonsterType.RANGER, 90),
    YT_MEJKOT("Yt-MejKot", WaveMonsterType.MELEE, 180),
    KET_ZEK("Ket-Zek", WaveMonsterType.MAGE, 360),
    TZTOK_JAD("TzTok-Jad", WaveMonsterType.JAD, 702);

    private final String name;
    private final WaveMonsterType type;
    private final int level;

    @Override
    public int getCountPerSpawn()
    {
        return 1;
    }
}
