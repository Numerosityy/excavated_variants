package dev.lukebemish.excavatedvariants.worldgen;

import com.mojang.datafixers.util.Pair;
import dev.lukebemish.excavatedvariants.platform.Services;
import dev.lukebemish.excavatedvariants.ExcavatedVariants;
import dev.lukebemish.excavatedvariants.data.BaseOre;
import dev.lukebemish.excavatedvariants.data.BaseStone;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;

public final class OreFinderUtil {
    private OreFinderUtil() {
    }

    public static void setupBlocks() {
        for (Block block : Services.REGISTRY_UTIL.getAllBlocks()) {
            ((IOreFound) block).excavated_variants$setPair(null);
            ((IOreFound) block).excavated_variants$setStone(null);
            if (ExcavatedVariants.setupMap()) {
                for (Pair<BaseOre, HashSet<BaseStone>> p : ExcavatedVariants.oreStoneList) {
                    for (ResourceLocation rl : p.getFirst().block_id) {
                        Block bl2 = Services.REGISTRY_UTIL.getBlockById(rl);
                        if (bl2 == block) {
                            ((IOreFound) block).excavated_variants$setPair(p);
                        }
                    }
                    for (BaseStone stone : p.getSecond()) {
                        if (stone.block_id.equals(Services.REGISTRY_UTIL.getRlByBlock(block))) {
                            ((IOreFound) block).excavated_variants$setStone(stone);
                        }
                    }
                }
            }
        }
    }
}
