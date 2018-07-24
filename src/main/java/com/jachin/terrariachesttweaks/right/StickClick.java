package com.jachin.terrariachesttweaks.right;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StickClick{

    private Minecraft mc;

    public StickClick() {
        mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void rightClick(PlayerInteractEvent event){
        if(event.getWorld().isRemote && event.getItemStack().getItem().getRegistryName().equals("item.stick")) {
            PlayerInteractEvent.RightClickItem evt = new PlayerInteractEvent.RightClickItem(event.getEntityPlayer(), EnumHand.MAIN_HAND);
            Item item = evt.getItemStack().getItem().setUnlocalizedName("ghost");
            System.out.println("delegate >> "+item.delegate.name());
            System.out.println("unlocalize >> "+evt.getItemStack().getItem().getUnlocalizedName());
        }
    }
}
