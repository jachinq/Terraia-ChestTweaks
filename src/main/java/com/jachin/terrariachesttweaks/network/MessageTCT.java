package com.jachin.terrariachesttweaks.network;

import com.jachin.terrariachesttweaks.handler.StackHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTCT implements IMessage {

    public static final MessageTCT instance = new MessageTCT();

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<MessageTCT, IMessage> {

        @Override
        public IMessage onMessage(MessageTCT message, MessageContext ctx) {
            // 处理服务端线程事件的主要代码
            StackHandler.getInstance(ctx.getServerHandler().player).stack();
            return null;
        }
    }
}
