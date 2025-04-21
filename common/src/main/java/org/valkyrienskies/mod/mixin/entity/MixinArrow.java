package org.valkyrienskies.mod.mixin.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class MixinArrow extends Entity {

    @Unique
    private int bounceCount = 0;

    public MixinArrow(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "onHitBlock", at = @At("TAIL"))
    private void onHitBlock(net.minecraft.world.phys.BlockHitResult hitResult, CallbackInfo ci) {
        incrementBounceCount();
    }

    @Unique
    private void incrementBounceCount() {
        bounceCount++;
        if (bounceCount >= 3) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

}
