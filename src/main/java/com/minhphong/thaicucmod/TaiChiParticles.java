package com.minhphong.thaicucmod;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class TaiChiParticles {
    public static void spawnSlashParticles(ServerWorld world, double x, double y, double z) {
        for (int i = 0; i < 15; i++) {
            double offsetX = (Math.random() - 0.5) * 3;
            double offsetY = (Math.random() - 0.5) * 3;
            double offsetZ = (Math.random() - 0.5) * 3;
            world.spawnParticles(ParticleTypes.CRIT, x + offsetX, y + offsetY, z + offsetZ, 1, 0, 0, 0, 0.5);
            world.spawnParticles(ParticleTypes.SMOKE, x + offsetX, y + offsetY, z + offsetZ, 1, 0, 0, 0, 0.3);
        }
    }

    public static void spawnWindAura(ServerWorld world, Vec3d center) {
        for (int i = 0; i < 30; i++) {
            double angle = (Math.PI * 2 * i) / 30;
            double radius = 2.0;
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            world.spawnParticles(ParticleTypes.CLOUD, x, center.y + 1, z, 1, 0, 0.1, 0, 0.2);
        }
    }

    public static void spawnYinYangCircle(ServerWorld world, double x, double y, double z, double radius) {
        int particleCount = (int)(radius * 10);
        for (int i = 0; i < particleCount; i++) {
            double angle = (Math.PI * 2 * i) / particleCount;
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            if (i % 2 == 0) {
                world.spawnParticles(ParticleTypes.CLOUD, px, y + 0.1, pz, 1, 0, 0, 0, 0.2);
            } else {
                world.spawnParticles(ParticleTypes.SMOKE, px, y + 0.1, pz, 1, 0, 0, 0, 0.2);
            }
        }
    }
}