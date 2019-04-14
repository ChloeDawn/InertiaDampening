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

package io.github.insomniakitten.inertiadampening;

import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;

public final class InertiaDampening {
  public static final String INJECTION_ID = "inertiadampening:reset_horizontal_velocity";

  private InertiaDampening() {
    throw new UnsupportedOperationException();
  }

  /**
   * Resets the given entity's horizontal velocity if they are
   * a client player and client input is applying no horizontal movement
   * 
   * @author InsomniaKitten
   */
  public static void resetHorizontalVelocity(final Object entity) {
    if (entity instanceof ClientPlayerEntity) {
      final ClientPlayerEntity player = (ClientPlayerEntity) entity;
      if (player.abilities.flying) {
        final Input input = player.input;
        if (!input.pressingForward && !input.pressingBack && !input.pressingLeft && !input.pressingRight) {
          player.setVelocity(0.0, player.getVelocity().y, 0.0);
        }
      }
    }
  }
}
