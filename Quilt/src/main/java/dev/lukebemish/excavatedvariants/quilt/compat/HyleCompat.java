package dev.lukebemish.excavatedvariants.quilt.compat;

public class HyleCompat {
    /*private static Map<Block, Pair<BaseOre, HashSet<BaseStone>>> excavatedVariantsOres;

    public static void init() {
        StoneTypeCallback.EVENT.register(stoneType -> {
            if (excavatedVariantsOres == null) {
                excavatedVariantsOres = new IdentityHashMap<>();
                for (Pair<BaseOre, HashSet<BaseStone>> p : ExcavatedVariants.oreStoneList) {
                    for (ResourceLocation block_id : p.getFirst().block_id) {
                        Block baseOreBlock = Services.REGISTRY_UTIL.getBlockById(block_id);
                        excavatedVariantsOres.put(baseOreBlock, p);
                    }
                }
            }

            Block baseBlock = stoneType.getBaseBlock().getBlock();
            excavatedVariantsOres.forEach((baseOreBlock, pair) -> {
                HashSet<BaseStone> stones = pair.getSecond();
                stones.stream().filter(stone -> Services.REGISTRY_UTIL.getBlockById(stone.block_id) == baseBlock).findAny().ifPresent(stone -> {
                    Block oreBlock = Services.REGISTRY_UTIL.getBlockById(new ResourceLocation(ExcavatedVariants.MOD_ID, stone.id + "_" + pair.getFirst().id));
                    if (!stoneType.getOreMap().containsKey(baseOreBlock) && oreBlock != null)
                        stoneType.getOreMap().put(baseOreBlock, oreBlock.defaultBlockState());
                });
            });
            return true;
        });
    }*/
}
