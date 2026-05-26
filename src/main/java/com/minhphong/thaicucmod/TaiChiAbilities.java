package com.minhphong.thaicucmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TaiChiAbilities {
    public static void triggerMeditationSlash(World world, PlayerEntity player) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        player.setOnGround(false);
        player.setVelocity(player.getVelocity().x * 0.1, 0, player.getVelocity().z * 0.1);
        
        serverWorld.getServer().execute(() -> {
            if (!player.isRemoved()) {
                executeSlashAttack(serverWorld, player);
            }
        });
    }

    private static void executeSlashAttack(ServerWorld world, PlayerEntity player) {
        if (player.isRemoved()) return;

        Vec3d playerPos = player.getPos();
        double range = 15.0;
        List<Entity> entities = world.getOtherEntities(player, player.getBoundingBox().expand(range, range, range));

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !entity.equals(player)) {
                double distance = player.distanceTo(entity);
                if (distance < range) {
                    livingEntity.damage(world.getDamageSources().playerAttack(player), 15.0f);
                    Vec3d direction = entity.getPos().subtract(playerPos).normalize();
                    livingEntity.addVelocity(direction.x * 2, 0.5, direction.z * 2);
                    TaiChiParticles.spawnSlashParticles(world, entity.getX(), entity.getY(), entity.getZ());
                }
            }
        }

        TaiChiParticles.spawnWindAura(world, playerPos);
        player.sendMessage(net.minecraft.text.Text.literal("\u00a7bMeditation Slash!"), true);
    }

    public static void triggerTaiChiTerritory(World world, PlayerEntity player) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        Vec3d playerPos = player.getPos();
        double territoryRadius = 20.0;
        int duration = 300;

        TaiChiParticles.spawnYinYangCircle(serverWorld, playerPos.x, playerPos.y, playerPos.z, territoryRadius);

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 1, false, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, 1, false, false));

        damageTerritoryEnemies(serverWorld, playerPos, territoryRadius, duration, player);
        player.sendMessage(net.minecraft.text.Text.literal("\u00a7bThai Cuc Territory!"), true);
    }

    private static void damageTerritoryEnemies(ServerWorld world, Vec3d center, double radius, int duration, PlayerEntity player) {
        int damageInterval = 10;
        for (int tick = 0; tick < duration; tick += damageInterval) {
            world.getServer().execute(() -> {
                List<Entity> entities = world.getOtherEntities(player, 
                    new net.minecraft.util.math.Box(center.x - radius, center.y - radius, center.z - radius,
                                                     center.x + radius, center.y + radius, center.z + radius));
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity livingEntity && !entity.equals(player)) {
                        livingEntity.damage(world.getDamageSources().generic(), 5.0f);
                        livingEntity.setVelocity(livingEntity.getVelocity().x * 0.5, livingEntity.getVelocity().y * 0.8,
                                               livingEntity.getVelocity().z * 0.5);
                    }
                }
            });
        }
    }
}