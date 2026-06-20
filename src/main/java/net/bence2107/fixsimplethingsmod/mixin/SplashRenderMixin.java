package net.bence2107.fixsimplethingsmod.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import net.minecraft.network.chat.Component;

import java.util.Random;

@Mixin(TitleScreen.class)
public abstract class SplashRenderMixin {
    @Shadow @Mutable @Nullable private SplashRenderer splash;

    @Inject(method = "init", at = @At("RETURN"))
    private void replaceAllSplashes(CallbackInfo ci) {
        String[] customSplashes = {
                "Almost everyone passes through, by 'thime'",
                "Factorial hurts very much",
                "One strategy dominates the other.",
                "Never erode a cat"
        };

        Random random = new Random();
        if (random.nextFloat() < 0.1f) {
            String customSplash = customSplashes[random.nextInt(customSplashes.length)];
            this.setSplashText(new SplashRenderer(Component.literal(customSplash)));
        }
    }

    @Unique
    public void setSplashText(@Nullable SplashRenderer splashText) {
        this.splash = splashText;
    }
}