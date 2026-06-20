package net.bence2107.fixsimplethingsmod.mixin;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.Rotations;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin {
    @Unique
    private int pose_index = 0;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        getInstance().setShowArms(true);
    }

    @Unique
    private ArmorStand getInstance() {
        return (ArmorStand) (Object) this;
    }

    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    private void onInteract(Player player, Vec3 hitPos, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {

        if (!player.isShiftKeyDown() || hand != InteractionHand.MAIN_HAND) return;

        ArmorStand armor = getInstance();

        if (!armor.level().isClientSide() && player.getItemInHand(hand).isEmpty()) {
            pose_index = (pose_index + 1) % 13;
            applyPose(armor, pose_index);

            armor.level().playSound(null, armor.blockPosition(),
                    SoundEvents.ARMOR_STAND_PLACE, SoundSource.PLAYERS, 1f, 1f);
            player.swing(hand);

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeCustomData(ValueOutput output, CallbackInfo ci) {
        output.putInt("CustomPoseIndex", pose_index);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readCustomData(ValueInput input, CallbackInfo ci) {
        pose_index = input.getIntOr("CustomPoseIndex", 0);
    }

    @Unique
    private void applyPose(ArmorStand stand, int index) {
        stand.setBodyPose(new Rotations(0f, 0f, 0f));
        stand.setHeadPose(new Rotations(0f, 0f, 0f));
        stand.setLeftArmPose(new Rotations(0f, 0f, 0f));
        stand.setRightArmPose(new Rotations(0f, 0f, 0f));
        stand.setLeftLegPose(new Rotations(0f, 0f, 0f));
        stand.setRightLegPose(new Rotations(0f, 0f, 0f));

        switch (index) {
            case 0:
                stand.setLeftArmPose(new Rotations(7f, 0f, 0f));
                stand.setRightArmPose(new Rotations(353f, 0f, 7f));
                break;
            case 1:
                break;
            case 2:
                stand.setHeadPose(new Rotations(21f, 0f, 0f));
                stand.setLeftArmPose(new Rotations(339f, 0f, 28f));
                stand.setRightArmPose(new Rotations(318f, 0f, 332f));
                break;
            case 3:
                stand.setBodyPose(new Rotations(0f, 173f, 0f));
                stand.setHeadPose(new Rotations(346f, 0f, 0f));
                stand.setLeftArmPose(new Rotations(14f, 0f, 0f));
                stand.setRightArmPose(new Rotations(298f, 0f, 0f));
                stand.setRightLegPose(new Rotations(21f, 256f, 346f));
                break;
            case 4:
                stand.setHeadPose(new Rotations(346f, 0f, 0f));
                stand.setLeftArmPose(new Rotations(28f, 0f, 0f));
                stand.setRightArmPose(new Rotations(249f, 28f, 0f));
                stand.setLeftLegPose(new Rotations(7f, 0f, 353f));
                stand.setRightLegPose(new Rotations(360f, 0f, 7f));
                break;
            case 5:
                stand.setHeadPose(new Rotations(346f, 0f, 0f));
                stand.setLeftArmPose(new Rotations(255f, 29f, 0f));
                stand.setRightArmPose(new Rotations(255f, 319f, 0f));
                stand.setLeftLegPose(new Rotations(7f, 0f, 353f));
                stand.setRightLegPose(new Rotations(360f, 0f, 7f));
                break;
            case 6:
                stand.setHeadPose(new Rotations(346f, 0f, 0f));
                stand.setLeftArmPose(new Rotations(255f, 341f, 0f));
                stand.setRightArmPose(new Rotations(232f, 341f, 293f));
                stand.setLeftLegPose(new Rotations(7f, 0f, 353f));
                stand.setRightLegPose(new Rotations(360f, 0f, 7f));
                break;
            case 7:
                stand.setLeftArmPose(new Rotations(31f, 0f, 0f));
                stand.setRightArmPose(new Rotations(322f, 0f, 338f));
                break;
            case 8:
                stand.setBodyPose(new Rotations(0f, 6f, 0f));
                stand.setHeadPose(new Rotations(0f, 6f, 3f));
                stand.setLeftArmPose(new Rotations(31f, 73f, 0f));
                stand.setRightArmPose(new Rotations(210f, 360f, 271f));
                stand.setLeftLegPose(new Rotations(344f, 274f, 360f));
                stand.setRightLegPose(new Rotations(0f, 118f, 3f));
                break;
            case 9:
                stand.setHeadPose(new Rotations(9f, 29f, 360f));
                stand.setLeftArmPose(new Rotations(9f, 0f, 226f));
                stand.setRightArmPose(new Rotations(121f, 185f, 93f));
                stand.setLeftLegPose(new Rotations(344f, 319f, 0f));
                stand.setRightLegPose(new Rotations(9f, 360f, 0f));
                break;
            case 10:
                stand.setHeadPose(new Rotations(344f, 0f, 0f));
                stand.setLeftArmPose(new Rotations(255f, 0f, 0f));
                stand.setRightArmPose(new Rotations(255f, 0f, 0f));
                stand.setLeftLegPose(new Rotations(9f, 0f, 0f));
                stand.setRightLegPose(new Rotations(322f, 0f, 0f));
                break;
            case 11:
                stand.setBodyPose(new Rotations(0f, 6f, 0f));
                stand.setHeadPose(new Rotations(344f, 51f, 0f));
                stand.setLeftArmPose(new Rotations(360f, 163f, 249f));
                stand.setRightArmPose(new Rotations(255f, 73f, 360f));
                stand.setLeftLegPose(new Rotations(255f, 51f, 0f));
                stand.setRightLegPose(new Rotations(9f, 0f, 0f));
                break;
            case 12:
                stand.setBodyPose(new Rotations(0f, 185f, 360f));
                stand.setHeadPose(new Rotations(360f, 51f, 3f));
                stand.setLeftArmPose(new Rotations(0f, 0f, 249f));
                stand.setRightArmPose(new Rotations(255f, 73f, 360f));
                stand.setLeftLegPose(new Rotations(344f, 73f, 3f));
                stand.setRightLegPose(new Rotations(299f, 297f, 315f));
                break;
        }
    }
}