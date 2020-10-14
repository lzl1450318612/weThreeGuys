package application;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import transport.NettyTelnetInitializer;

/**
 * @author lizilin
 */
@AllArgsConstructor
public class ServerApplication {

    private final int port;

    public void start(){
        // 用于处理io的线程组
        EventLoopGroup ioGroup = new NioEventLoopGroup();
        // 用于处理连接的线程组，并发连接量不大的情况下，一般一个就够
        EventLoopGroup connectionGroup = new NioEventLoopGroup(1);
        try {
            // 服务端辅助启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 绑定线程组
            serverBootstrap.group(connectionGroup, ioGroup);
            // 指定NIO模型
            serverBootstrap.channel(NioServerSocketChannel.class);

            serverBootstrap.childHandler(new NettyTelnetInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭线程组
            ioGroup.shutdownGracefully();
            connectionGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new ServerApplication(12345).start();
    }

}
