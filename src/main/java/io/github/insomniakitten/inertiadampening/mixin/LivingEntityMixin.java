/*
 * Copyright (C) 2018 InsomniaKitten
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

import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
@SuppressWarnings("ConstantConditions")
abstract class LivingEntityMixin extends Entity {
  private LivingEntityMixin() {
    super(null, null);
  }

  @Inject(
    method = "updateMovement",
    at = @At(
      value = "INVOKE_STRING",
      target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V",
      args = "ldc=ai"
    )
  )
  private void resetVelocity(final CallbackInfo ci) {
    if ((Object) this instanceof ClientPlayerEntity) {
      final ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
      if (player.abilities.flying) {
        final Input input = player.input;
        if (!input.forward && !input.back && !input.left && !input.right) {
          velocityX = 0.0;
          velocityZ = 0.0;
        }
      }
    }
  }
}
