package com.carllhw.example.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles a server-side channel.
 *
 * @author carllhw
 */
@Slf4j
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        StringBuilder builder = new StringBuilder();
        while (in.isReadable()) {
            builder.append((char) in.readByte());
        }
        log.info(builder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("discard server error", cause);
        ctx.close();
    }
}