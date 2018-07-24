package com.jachin.terrariachesttweaks.common;

import com.jachin.terrariachesttweaks.handler.OpenChestEventHandler;
import com.jachin.terrariachesttweaks.handler.OpenInventoryEventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class CommonProxy {

    private static Logger logger;

    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        new ConfigLoader(event);
        OpenInventoryEventHandler.getInstance();
        OpenChestEventHandler.getInstance();
    }

    public void init(FMLInitializationEvent event){
    }

    public void postInit(FMLPostInitializationEvent event){
    }
}