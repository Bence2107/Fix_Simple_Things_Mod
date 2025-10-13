package net.bence2107.fixsimplethingsmod.mixin;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin  {
    @Unique
    private int pose_index = 0;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
       getInstance().setShowArms(true);
    }

    @Unique
    private ArmorStandEntity getInstance() {
        return (ArmorStandEntity) (Object) this;
    }

    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    private void onInteract(PlayerEntity player, Vec3d hitPos, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

        if (!player.isSneaking() || hand != Hand.MAIN_HAND) return;

        if (!getInstance().getWorld().isClient) {
            ArmorStandEntity armor = getInstance();

            if (player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
                pose_index = (pose_index + 1) % 13;
                applyPose(armor, pose_index);

                armor.getWorld().playSound(null, armor.getBlockPos(),
                        SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.PLAYERS, 1f, 1f);
                player.swingHand(hand);

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void writeCustomData(WriteView view, CallbackInfo ci) {
        view.putInt("CustomPoseIndex", pose_index);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void readCustomData(ReadView view, CallbackInfo ci) {
        pose_index = view.getInt("CustomPoseIndex", 0);
    }

    @Unique
    private void applyPose(ArmorStandEntity stand, int index) {
        stand.setBodyRotation(new EulerAngle(0f, 0f, 0f));
        stand.setHeadRotation(new EulerAngle(0f, 0f, 0f));
        stand.setLeftArmRotation(new EulerAngle(0f, 0f, 0f));
        stand.setRightArmRotation(new EulerAngle(0f, 0f, 0f));
        stand.setLeftLegRotation(new EulerAngle(0f, 0f, 0f));
        stand.setRightLegRotation(new EulerAngle(0f, 0f, 0f));

        switch(index) {
            case 0:
                stand.setLeftArmRotation(new EulerAngle(7f, 0f, 0f));
                stand.setRightArmRotation(new EulerAngle(353f, 0f, 7f));
                break;
            case 1:
                break;
            case 2:
                stand.setHeadRotation(new EulerAngle(21f, 0f, 0f));
                stand.setLeftArmRotation(new EulerAngle(339f, 0f, 28f));
                stand.setRightArmRotation(new EulerAngle(318f, 0f, 332f));
                break;
            case 3:
                stand.setBodyRotation(new EulerAngle(0f, 173f, 0f));
                stand.setHeadRotation(new EulerAngle(346f, 0f, 0f));
                stand.setLeftArmRotation(new EulerAngle(14f, 0f, 0f));
                stand.setRightArmRotation(new EulerAngle(298f, 0f, 0f));
                stand.setRightLegRotation(new EulerAngle(21f, 256f, 346f));
                break;
            case 4:
                stand.setHeadRotation(new EulerAngle(346f, 0f, 0f));
                stand.setLeftArmRotation(new EulerAngle(28f, 0f, 0f));
                stand.setRightArmRotation(new EulerAngle(249f, 28f, 0f));
                stand.setLeftLegRotation(new EulerAngle(7f, 0f, 353f));
                stand.setRightLegRotation(new EulerAngle(360f, 0f, 7f));
                break;
            case 5:
                stand.setHeadRotation(new EulerAngle(346f, 0f, 0f));
                stand.setLeftArmRotation(new EulerAngle(255f, 29f, 0f));
                stand.setRightArmRotation(new EulerAngle(255f, 319f, 0f));
                stand.setLeftLegRotation(new EulerAngle(7f, 0f, 353f));
                stand.setRightLegRotation(new EulerAngle(360f, 0f, 7f));
                break;
            case 6:
                stand.setHeadRotation(new EulerAngle(346f, 0f, 0f));
                stand.setLeftArmRotation(new EulerAngle(255f, 341f, 0f));
                stand.setRightArmRotation(new EulerAngle(232f, 341f, 293f));
                stand.setLeftLegRotation(new EulerAngle(7f, 0f, 353f));
                stand.setRightLegRotation(new EulerAngle(360f, 0f, 7f));
                break;
            case 7:
                stand.setLeftArmRotation(new EulerAngle(31f, 0f, 0f));
                stand.setRightArmRotation(new EulerAngle(322f, 0f, 338f));
                break;
            case 8:
                stand.setBodyRotation(new EulerAngle(0f, 6f, 0f));
                stand.setHeadRotation(new EulerAngle(0f, 6f, 3f));
                stand.setLeftArmRotation(new EulerAngle(31f, 73f, 0f));
                stand.setRightArmRotation(new EulerAngle(210f, 360f, 271f));
                stand.setLeftLegRotation(new EulerAngle(344f, 274f, 360f));
                stand.setRightLegRotation(new EulerAngle(0f, 118f, 3f));
                break;
            case 9:
                stand.setHeadRotation(new EulerAngle(9f, 29f, 360f));
                stand.setLeftArmRotation(new EulerAngle(9f, 0f, 226f));
                stand.setRightArmRotation(new EulerAngle(121f, 185f, 93f));
                stand.setLeftLegRotation(new EulerAngle(344f, 319f, 0f));
                stand.setRightLegRotation(new EulerAngle(9f, 360f, 0f));
                break;
            case 10:
                stand.setHeadRotation(new EulerAngle(344f, 0f, 0f));
                stand.setLeftArmRotation(new EulerAngle(255f, 0f, 0f));
                stand.setRightArmRotation(new EulerAngle(255f, 0f, 0f));
                stand.setLeftLegRotation(new EulerAngle(9f, 0f, 0f));
                stand.setRightLegRotation(new EulerAngle(322f, 0f, 0f));
                break;
            case 11:
                stand.setBodyRotation(new EulerAngle(0f, 6f, 0f));
                stand.setHeadRotation(new EulerAngle(344f, 51f, 0f));
                stand.setLeftArmRotation(new EulerAngle(360f, 163f, 249f));
                stand.setRightArmRotation(new EulerAngle(255f, 73f, 360f));
                stand.setLeftLegRotation(new EulerAngle(255f, 51f, 0f));
                stand.setRightLegRotation(new EulerAngle(9f, 0f, 0f));
                break;
            case 12:
                stand.setBodyRotation(new EulerAngle(0f, 185f, 360f));
                stand.setHeadRotation(new EulerAngle(360f, 51f, 3f));
                stand.setLeftArmRotation(new EulerAngle(0f, 0f, 249f));
                stand.setRightArmRotation(new EulerAngle(255f, 73f, 360f));
                stand.setLeftLegRotation(new EulerAngle(344f, 73f, 3f));
                stand.setRightLegRotation(new EulerAngle(299f, 297f, 315f));
                break;
        }
    }
}