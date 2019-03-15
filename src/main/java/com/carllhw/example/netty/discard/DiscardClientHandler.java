package com.carllhw.example.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Handle client-side channel
 *
 * @author carllhw
 */
@Slf4j
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;

    private ChannelHandlerContext ctx;

    private final ChannelFutureListener trafficGenerator = future -> {
        if (future.isSuccess()) {
            generateTraffic();
        } else {
            log.error("future error", future.cause());
            future.channel().close();
        }
    };

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        // Initialize the message.
        content = ctx.alloc()
                .directBuffer(DiscardClient.SIZE)
                .writeBytes("hello".getBytes());

        // Send the initial messages.
        generateTraffic();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        content.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("discard client error", cause);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Server is supposed to send nothing, but if it sends something, discard it.
    }

    private void generateTraffic() throws InterruptedException {
        Thread.sleep(3000);
        // Flush the outbound buffer to the socket.
        // Once flushed, generate the same amount of traffic again.
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
    }
}
