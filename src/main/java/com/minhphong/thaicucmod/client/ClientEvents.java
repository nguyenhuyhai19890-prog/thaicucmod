package com.minhphong.thaicucmod.client;

import com.minhphong.thaicucmod.TaiChiSword;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientEvents {
    public static KeyBinding zKeyBinding;

    public static void registerEvents() {
        zKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.thaicucmod.taichi_territory",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.thaicucmod.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && zKeyBinding.wasPressed()) {
                if (client.player.getMainHandStack().getItem() instanceof TaiChiSword) {
                    TaiChiSkillHandler.onZKeyPressed(client.player);
                }
            }
        });
    }
}