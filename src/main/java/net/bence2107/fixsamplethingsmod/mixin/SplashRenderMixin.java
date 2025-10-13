package net.bence2107.fixsamplethingsmod.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@Mixin(TitleScreen.class)
public abstract class SplashRenderMixin {
    @Shadow @Mutable @Nullable private SplashTextRenderer splashText;

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
            this.setSplashText(new SplashTextRenderer(customSplash));
        }
    }

    public void setSplashText(@Nullable SplashTextRenderer splashText) {
        this.splashText = splashText;
    }

    public @Nullable SplashTextRenderer getSplashText() {
        return splashText;
    }
}