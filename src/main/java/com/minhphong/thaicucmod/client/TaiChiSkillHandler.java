package com.minhphong.thaicucmod.client;

import com.minhphong.thaicucmod.TaiChiAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class TaiChiSkillHandler {
    private static long lastZKeyPress = 0;
    private static final long COOLDOWN = 60 * 1000;

    public static void onZKeyPressed(PlayerEntity player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastZKeyPress < COOLDOWN) {
            long remainingSeconds = (COOLDOWN - (currentTime - lastZKeyPress)) / 1000;
            player.sendMessage(Text.literal("Cooldown: " + remainingSeconds + "s"), true);
            return;
        }
        lastZKeyPress = currentTime;
        TaiChiAbilities.triggerTaiChiTerritory(player.getWorld(), player);
    }
}