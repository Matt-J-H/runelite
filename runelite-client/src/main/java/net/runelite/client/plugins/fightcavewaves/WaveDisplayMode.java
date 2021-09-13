package net.runelite.client.plugins.fightcavewaves;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WaveDisplayMode
{
    CURRENT("Current wave"),
    NEXT("Next wave"),
    BOTH("Both");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}
