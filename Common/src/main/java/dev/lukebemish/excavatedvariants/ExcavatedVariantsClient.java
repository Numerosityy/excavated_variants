package dev.lukebemish.excavatedvariants;

import com.mojang.datafixers.util.Pair;
import dev.lukebemish.dynamicassetgenerator.api.ResourceCache;
import dev.lukebemish.dynamicassetgenerator.api.client.AssetResourceCache;
import dev.lukebemish.excavatedvariants.client.TextureRegistrar;
import dev.lukebemish.excavatedvariants.data.BaseOre;
import dev.lukebemish.excavatedvariants.data.BaseStone;
import dev.lukebemish.excavatedvariants.data.ModData;
import dev.lukebemish.excavatedvariants.platform.Services;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.stream.Collectors;

public final class ExcavatedVariantsClient {
    private ExcavatedVariantsClient() {}

    public static final LangBuilder LANG_BUILDER = new LangBuilder();

    public static final AssetResourceCache ASSET_CACHE = ResourceCache.register(new AssetResourceCache(new ResourceLocation(ExcavatedVariants.MOD_ID, "assets")));

    public static void init() {
        Collection<String> modids = Services.PLATFORM.getModIds();

        ExcavatedVariants.setupMap();

        Map<String, BaseStone> stoneMap = new HashMap<>();
        for (ModData mod : ExcavatedVariants.getConfig().mods) {
            if (modids.containsAll(mod.modId)) {
                for (BaseStone stone : mod.providedStones) {
                    stoneMap.put(stone.id, stone);
                }
            }
        }

        Map<String, Pair<BaseOre, BaseStone>> extractorMap = ExcavatedVariants.oreStoneList.stream().flatMap(p -> p.getSecond().stream().map(
                stone -> new Pair<>(stone.id + "_" + p.getFirst().id, new Pair<>(p.getFirst(), stoneMap.get(p.getFirst().stone.get(0)))))).collect(Collectors.toMap(
                Pair::getFirst, Pair::getSecond
        ));
        List<Pair<BaseOre, BaseStone>> toMake = new ArrayList<>();

        for (Pair<BaseOre, HashSet<BaseStone>> p : ExcavatedVariants.oreStoneList) {
            var ore = p.getFirst();
            for (BaseStone stone : p.getSecond()) {
                String fullId = stone.id + "_" + ore.id;
                toMake.add(new Pair<>(ore, stone));
                ASSET_CACHE.planSource(new ResourceLocation(ExcavatedVariants.MOD_ID, "models/item/" + fullId + ".json"),
                        (rl, context) -> JsonHelper.getItemModel(fullId));
                LANG_BUILDER.add(fullId, stone, ore);
            }
        }

        ASSET_CACHE.planSource(new ResourceLocation(ExcavatedVariants.MOD_ID+"_generated", "lang/en_us.json"), (rl, context) -> LANG_BUILDER.build());

        ASSET_CACHE.planSource(new TextureRegistrar(extractorMap.values(), toMake, ASSET_CACHE.getContext()));
    }

    static void planLang(String key, String enName) {
        LANG_BUILDER.add(key, enName);
    }
}
