package com.jachin.terrariachesttweaks.gui;

import com.jachin.terrariachesttweaks.TerrariaChestTweaks;
import com.jachin.terrariachesttweaks.network.MessageTCT;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class ExtendedGUIInventory extends GuiInventory {

    private GuiButton btnClose;
    private EntityPlayer player;

    public ExtendedGUIInventory(EntityPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public void initGui(){
        super.initGui();
        // 初始化按钮
        int x = (this.width - this.xSize) / 2 + this.xSize*2/3 + 10;
        int y = (this.height - this.ySize)/ 2 + this.ySize /2 - 22  ;
        buttonList.add(btnClose = new GuiButton(0, x, y, 16, 16, "S"));

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
        if(button.id == btnClose.id) {
            System.out.println(player.getName()+" click the stack.");
            //　用户点击按钮，触发事件，将该事件提交到服务端线程
            TerrariaChestTweaks.network.sendToServer(MessageTCT.instance);
        }
    }
}
