package com.carllhw.example.netty.echo;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import com.carllhw.example.netty.Example;

/**
 * Echo client
 *
 * @author carllhw
 */
@Slf4j
public class EchoClient implements Example {

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    @Override
    public void run(ApplicationArguments args) {
        final SslContext sslCtx;
        if (SSL) {
            try {
                sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } catch (SSLException e) {
                log.error("ssl context error", e);
                return;
            }
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            p.addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(HOST, PORT).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("start error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
