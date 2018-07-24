package com.jachin.terrariachesttweaks.handler;

import com.google.common.collect.Lists;
import com.jachin.terrariachesttweaks.common.ConfigLoader;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class StackHandler {

    private static StackHandler instance;
    private EntityPlayerMP player;


    private StackHandler(){}

    public static StackHandler getInstance(EntityPlayerMP player){
        if(instance == null){
            instance = new StackHandler();
            instance.player = player;
        }
        return instance;
    }

    public void stack() {
        World world = player.world;
        BlockPos pos = player.getPosition();
        InventoryPlayer playerInv = player.inventory;
        List<TileEntity> blocks = nearbyInteractiveObjects(world, pos, getDistance());
        for (TileEntity te : blocks) {
            if (te != null)
                placeItem(playerInv,te);
        }
    }

    private int getDistance(){
        int distance ;

        if(ConfigLoader.distance < 0)
            distance = 0;
        if (ConfigLoader.distance > 10)
            distance = 10;
        else
            distance = ConfigLoader.distance;
        return distance;
    }

    /**
     * 获取 player 指定范围内的所有坐标的 block
     * @param world : player 所在 world
     * @param pos ：player 坐标
     * @param distance ：指定的范围
     * @return
     */
    private List<TileEntity> nearbyInteractiveObjects(World world, BlockPos pos, int distance) {
        int minX = pos.getX() - distance, maxX = pos.getX() + distance;
        int minY = pos.getY() - distance, maxY = pos.getY() + distance;
        int minZ = pos.getZ() - distance, maxZ = pos.getZ() + distance;
        List<TileEntity> list = Lists.newArrayList();
        for(int x = minX; x <= maxX; ++x)
            for(int y = minY; y <= maxY; ++y)
                for(int z = minZ; z <= maxZ; ++z) {
                    TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                    //判断 tileEntity 的数据是来自于箱子的
                    if(te instanceof TileEntityChest)
                        list.add(te);
                }
        return list;
    }

    /**
     * 整理物品
     */
    private void placeItem(InventoryPlayer playerInv, TileEntity te){
        try{
            IItemHandler chest = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
            for(int i = 0; i < playerInv.mainInventory.size();i++)
            {
                for (int j = 0; j < chest.getSlots(); j++) {

                    if (playerInv.getStackInSlot(i).isItemEqual(chest.getStackInSlot(j))) {

                        // 槽位满一组了，寻找新槽位插入
                        if (chest.getStackInSlot(j).getCount() == chest.getSlotLimit(j)) {
                            boolean flag = true;
                            for (int k = j+1; k < chest.getSlots(); k++){
                                if(chest.getStackInSlot(k).isItemEqual(playerInv.getStackInSlot(i))
                                        && chest.getStackInSlot(k).getCount() != chest.getSlotLimit(k)) {
                                    flag = false;
                                }
                            }
                            if(flag) {
                                for (int k = 0; k < chest.getSlots(); k++) {
                                    if (chest.getStackInSlot(k).isEmpty()) {
                                        ;
                                        chest.insertItem(k, playerInv.getStackInSlot(i), false);
                                        playerInv.removeStackFromSlot(i);
                                        break;
                                    }
                                }
                            }
                        }

                        // 槽位未满一组
                        if (chest.getStackInSlot(j).getCount() != chest.getSlotLimit(j)) {
                            int count1 = playerInv.getStackInSlot(i).getCount(); // 玩家物品数
                            int count2 = chest.getStackInSlot(j).getCount();  // 箱子物品数量
                            // 得到堆叠满一组后的剩余数
                            int flag = count1 + count2 - chest.getSlotLimit(j);
                            /*
                             如果剩余数不为 0，说明需要分堆处理
                              */
                            if (flag > 0) {
                                // 分堆，首先生成一个刚好可以堆叠满一组的物品堆
                                ItemStack temp = playerInv.getStackInSlot(i);
                                temp.setCount(chest.getSlotLimit(j) - count2);
                                chest.insertItem(j, temp, false);

                                // 堆叠后剩余的物品堆
                                temp.setCount(flag);
                                // 处理物品数量越界的问题，使用 setInventorySlotContents 这个方法就可以了。
                                playerInv.setInventorySlotContents(i, temp);

                                boolean flag2 = true;
                                for (int k = j+1; k < chest.getSlots(); k++){
                                    if(chest.getStackInSlot(k).isItemEqual(playerInv.getStackInSlot(i))
                                            && chest.getStackInSlot(k).getCount() != chest.getSlotLimit(k)) {
                                        flag2 = false;
                                    }
                                }
                                if(flag2) {
                                    for (int k = 0; k < chest.getSlots(); k++) {
                                        if (chest.getStackInSlot(k).isEmpty()) {
                                            chest.insertItem(k, playerInv.getStackInSlot(i), false);
                                            playerInv.removeStackFromSlot(i);
                                            break;
                                        }
                                    }
                                }

                            } else {
                                chest.insertItem(j, playerInv.getStackInSlot(i), false);
                                player.inventory.removeStackFromSlot(i);
                            }
                        }

                        // 更新玩家状态--背包
                        player.onUpdate();
                    }
                }
            }
        }catch (Exception e){
            System.err.println("ERROR >> "+e.getMessage());
        }
    }

}
