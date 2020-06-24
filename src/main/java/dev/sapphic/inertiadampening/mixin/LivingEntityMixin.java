/*
 * Copyright (C) 2019 InsomniaKitten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sapphic.inertiadampening.mixin;

import dev.sapphic.inertiadampening.InertiaDampening;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {
  LivingEntityMixin() {
    //noinspection ConstantConditions
    super(null, null);
  }

  @Inject(method = "tickMovement", require = 1, allow = 1, at = @At(
    value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", shift = Shift.AFTER))
  private void dampenInertia(final CallbackInfo ci) {
    //noinspection ConstantConditions
    if (InertiaDampening.isEnabled() && ((Object) this instanceof ClientPlayerEntity)) {
      final ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
      if (player.abilities.flying) {
        final Input input = player.input;
        if (!input.pressingForward && !input.pressingBack && !input.pressingLeft && !input.pressingRight) {
          this.setVelocity(0.0, this.getVelocity().y, 0.0); // new Vec3d
        }
      }
    }
  }
}
