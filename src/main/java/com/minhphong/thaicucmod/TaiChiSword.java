package com.minhphong.thaicucmod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TaiChiSword extends SwordItem {
    private static final int RIGHT_CLICK_COOLDOWN = 300;
    private static final int SKILL_Z_COOLDOWN = 1200;

    public TaiChiSword(ToolMaterial toolMaterial, int attackDamageBonus, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamageBonus, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<net.minecraft.item.ItemStack> use(World world, PlayerEntity player, Hand hand) {
        net.minecraft.item.ItemStack itemStack = player.getStackInHand(hand);
        
        if (world.isClient) {
            return TypedActionResult.pass(itemStack);
        }

        if (player.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.fail(itemStack);
        }

        TaiChiAbilities.triggerMeditationSlash(world, player);
        player.getItemCooldownManager().set(this, RIGHT_CLICK_COOLDOWN);

        return TypedActionResult.success(itemStack);
    }

    @Override
    public boolean postHit(net.minecraft.item.ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
            TaiChiParticles.spawnSlashParticles(serverWorld, target.getX(), target.getY(), target.getZ());
        }
        return super.postHit(stack, target, attacker);
    }
}