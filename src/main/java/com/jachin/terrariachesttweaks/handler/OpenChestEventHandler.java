package com.jachin.terrariachesttweaks.handler;

import com.jachin.terrariachesttweaks.gui.ExtendedGUIChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OpenChestEventHandler {

    static private OpenChestEventHandler instance;
    private Minecraft mc;

    // 注册事件
    private OpenChestEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    //
    public static OpenChestEventHandler getInstance() {
        if (instance==null) {
            instance=new OpenChestEventHandler();
            instance.mc=Minecraft.getMinecraft();
        }
        return instance;
    }

    @SubscribeEvent
    public void guiOpenEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiChest) {
            GuiChest originalChest=(GuiChest) event.getGui();
            ContainerChest originalContainer=(ContainerChest) originalChest.inventorySlots;
            IInventory lowerChestInventory = originalContainer.getLowerChestInventory();
            InventoryPlayer upperChestInventory = Minecraft.getMinecraft().player.inventory;
            ExtendedGUIChest egc=new ExtendedGUIChest(upperChestInventory, lowerChestInventory);
            event.setGui(egc);
        }
    }
}
