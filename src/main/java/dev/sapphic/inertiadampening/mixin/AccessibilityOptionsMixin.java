package dev.sapphic.inertiadampening.mixin;

import dev.sapphic.inertiadampening.InertiaDampening;
import net.minecraft.client.gui.screen.options.AccessibilityOptionsScreen;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AccessibilityOptionsScreen.class)
abstract class AccessibilityOptionsMixin {
  @Shadow @Final @Mutable private static Option[] OPTIONS;

  static {
    final Option[] options = new Option[OPTIONS.length + 1];
    System.arraycopy(OPTIONS, 0, options, 0, OPTIONS.length);
    options[OPTIONS.length] = InertiaDampening.getOption();
    OPTIONS = options;
  }
}
