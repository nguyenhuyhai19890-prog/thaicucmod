package com.minhphong.thaicucmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ThaicucmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientEvents.registerEvents();
        ItemModelProviders.registerModels();
        ScreenProviders.registerScreens();
    }
}