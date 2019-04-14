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

package io.github.insomniakitten.inertiadampening.mixin;

import io.github.insomniakitten.inertiadampening.InertiaDampening;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
  private LivingEntityMixin() {
    throw new UnsupportedOperationException();
  }

  @Inject(
    id = InertiaDampening.INJECTION_ID,
    method = "updateMovement",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V",
      shift = Shift.AFTER
    )
  )
  private void inertiadampening$resetHorizontalVelocity(final CallbackInfo ci) {
    InertiaDampening.resetHorizontalVelocity(this);
  }
}
