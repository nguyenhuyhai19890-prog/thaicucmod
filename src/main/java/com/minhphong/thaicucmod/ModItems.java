package com.minhphong.thaicucmod;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final SwordItem THAI_CUC_SWORD = new TaiChiSword(
        ToolMaterials.NETHERITE,
        10,
        -1.5f,
        new Item.Settings()
    );

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(ThaicucmodMod.MOD_ID, "thai_cuc_sword"), THAI_CUC_SWORD);
        ThaicucmodMod.LOGGER.info("Items registered!");
    }
}