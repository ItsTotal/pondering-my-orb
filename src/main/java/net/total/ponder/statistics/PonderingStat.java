package net.total.ponder.statistics;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class PonderingStat {

    public static void registerStats(){
        Registry.register(Registries.CUSTOM_STAT, PONDER, PONDER);

        Stats.CUSTOM.getOrCreateStat(PONDER, StatFormatter.DEFAULT);
    }



    public static final Identifier PONDER = Identifier.of("ponder", "pondering");
}
