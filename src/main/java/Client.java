import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
class Client {
    private int port;
    private String host;
    private Channel channel;
    private EventLoopGroup workGroup;

    Client (String host, int port) {
        this.port = port;
        this.host = host;
        workGroup = new NioEventLoopGroup();
    }

    void run() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                        new ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).sync();
        this.channel = channelFuture.channel();
    }

    void shutdown() throws InterruptedException {
        workGroup.awaitTermination(2, TimeUnit.SECONDS);
        workGroup.shutdownGracefully();
    }
}
