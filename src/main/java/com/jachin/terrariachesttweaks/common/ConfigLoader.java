package com.jachin.terrariachesttweaks.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {

    private  static Configuration config;

    private static Logger logger;

    public static int distance;

    public ConfigLoader(FMLPreInitializationEvent event){

        logger = event.getModLog();

        config = new Configuration (event.getSuggestedConfigurationFile());

        config.load();

        // 确保在第一次运行 mod 时可以产生默认配置文件
        load();

    }

    private static void  load(){
        logger.info("Started loading config");
        String comment;

        comment = "What distance do you want to set for the stack range.[0-10]";
        distance = config.get(Configuration.CATEGORY_GENERAL,"DISTANCE",5,comment).getInt();

        config.save();
        logger.info("Finished loading config");
    }

    public static Logger logger(){
        return logger;
    }
}
