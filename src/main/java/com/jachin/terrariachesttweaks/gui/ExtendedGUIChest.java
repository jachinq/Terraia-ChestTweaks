package com.jachin.terrariachesttweaks.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.io.IOException;

public class ExtendedGUIChest extends GuiChest {

    private GuiButton btnToInventory;
    private GuiButton btnToChest;
    private IInventory upperInv; //player
    private IInventory lowerInv; //chest

    public ExtendedGUIChest(IInventory upperInv, IInventory lowerInv) {
        super(upperInv, lowerInv);
        this.upperInv = upperInv;
        this.lowerInv = lowerInv;
    }

    @Override
    public void initGui(){
        super.initGui();
        int x = (this.width - this.xSize) / 2-30;;
        int y = (this.height - this.ySize)/ 2 + this.ySize - 45;
        buttonList.add(btnToInventory = new GuiButton(0, x,y-20, 20, 20, "T"));
        buttonList.add(btnToChest = new GuiButton(0, x, y+5, 20, 20, "S"));
    }


    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /*
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button == btnToInventory) {
            machItems(true);
        }
        if(button == btnToChest) {
            machItems(false);
        }
    }

    private void machItems(boolean isChest){
        IInventory chest, playerInventory;
        int chestSize, playerInventorySize;
        chest = lowerInv;
        chestSize = chest.getSizeInventory();
        playerInventory = upperInv;
        playerInventorySize = playerInventory.getSizeInventory();

        try {
            for (int i = 0; i < chestSize; i++) {
                ItemStack chestStack = chest.getStackInSlot(i);
                for (int j = 0; j < playerInventorySize; j++) {
                    ItemStack playerInventoryStack = playerInventory.getStackInSlot(j);

                    int slot;
                    if (isChest)
                        slot=i;
                    else
                        slot=slotIndexFromPlayerInventoryIndex(j);

                    if (chestStack.isItemEqual(playerInventoryStack)
                            && ItemStack.areItemStackTagsEqual(chestStack, playerInventoryStack)) {
                        //System.out.println("  from["+i+"] is same as to["+j+"] ("+playerInventory.getName()+"), clicking "+slot);
                        slotClick(slot, 0, ClickType.QUICK_MOVE);
                    }
                }
            }
        }catch (Exception e){
            sendChatMessage(e.getMessage());
        }

    }

    private void slotClick(int slot, int mouseButton, ClickType clickType) {
        // System.out.println("Clicking slot "+slot+" "+(mouseButton==0 ? "left" : "right")+" type:"+clickType.toString());
        mc.playerController.windowClick(mc.player.openContainer.windowId, slot, mouseButton, clickType, mc.player);
    }

    private int slotIndexFromPlayerInventoryIndex(int idx) {
        if (idx<9)
            return lowerInv.getSizeInventory()+27+idx;
        else
            return lowerInv.getSizeInventory()-9+idx;
    }
}
