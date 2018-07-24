package com.jachin.terrariachesttweaks;

import com.jachin.terrariachesttweaks.common.CommonProxy;
import com.jachin.terrariachesttweaks.network.MessageTCT;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = TerrariaChestTweaks.MODID, name = TerrariaChestTweaks.NAME, version = TerrariaChestTweaks.VERSION)
public class TerrariaChestTweaks
{
    public static final String MODID = "terrariachesttweaks";
    public static final String NAME = "Terraria-Chest Tweaks";
    public static final String VERSION = "1.0";

    //代理
    @SidedProxy(clientSide = "com.jachin.terrariachesttweaks.client.ClientProxy"
            ,serverSide = "com.jachin.terrariachesttweaks.common.CommonProxy")
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper network;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        network = NetworkRegistry.INSTANCE.newSimpleChannel("TerrariaChestTweaks");
        // 进行服务端线程的事件注册？
        network.registerMessage(MessageTCT.Handler.class,MessageTCT.class, 0, Side.SERVER);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

}
