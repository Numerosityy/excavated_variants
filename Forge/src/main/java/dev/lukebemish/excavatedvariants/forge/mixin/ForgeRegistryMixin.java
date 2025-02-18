package dev.lukebemish.excavatedvariants.forge.mixin;

import dev.lukebemish.excavatedvariants.forge.registry.BlockAddedCallback;
import dev.lukebemish.excavatedvariants.ExcavatedVariants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ForgeRegistry.class, remap = false)
public abstract class ForgeRegistryMixin<V> {
    @Shadow
    @Final
    private ResourceKey<Registry<V>> key;

    @Inject(method = "register(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/Object;)V", at = @At("RETURN"))
    private void excavated_variants$registryRegisterHackery(ResourceLocation rl, V value, CallbackInfo ci) {
        if (key.equals(ForgeRegistries.Keys.BLOCKS)) {
            if (ExcavatedVariants.neededRls.contains(rl)) {
                ExcavatedVariants.loadedBlockRLs.add(rl);
            }
            BlockAddedCallback.register();
        }
    }
}
