package com.jachin.terrariachesttweaks.handler;

import com.jachin.terrariachesttweaks.gui.ExtendedGUIInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OpenInventoryEventHandler {
    private static OpenInventoryEventHandler instance;
    private Minecraft mc;

    // 注册事件
    private OpenInventoryEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    //
    public static OpenInventoryEventHandler getInstance() {
        if (instance==null) {
            instance=new OpenInventoryEventHandler();
            instance.mc=Minecraft.getMinecraft();
        }
        return instance;
    }

    @SubscribeEvent
    public void guiOpenEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiInventory) {
            InventoryPlayer playerInv = Minecraft.getMinecraft().player.inventory;
            ExtendedGUIInventory egc=new ExtendedGUIInventory(playerInv.player);
            event.setGui(egc);
        }
    }

}
