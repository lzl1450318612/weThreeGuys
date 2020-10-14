package handler;

import entity.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.StringUtils;
import service.ChatService;
import utils.SingleFactory;

import java.util.Date;

/**
 * @author lizilin
 */
public class NettyTelnetHandler extends ChannelInboundHandlerAdapter {

    final ChatService chatService;

    public NettyTelnetHandler() {
        chatService = SingleFactory.getInstance(ChatService.class);
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String username = ctx.channel().localAddress().toString().replace("/", "");
        chatService.addUser(username, ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String username = ctx.channel().localAddress().toString().replace("/", "");
        chatService.removeUser(username, ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String username = ctx.channel().localAddress().toString().replace("/", "");
        String message = msg.toString();
        if (StringUtils.isNotBlank(message)) {
            if (StringUtils.equals(message, "exit\r\n")) {
                chatService.removeUser(username, ctx.channel());
                ctx.writeAndFlush("尻会想你的～\n");
                ctx.close();
            }
            chatService.sendMessageToAll(new Message(new Date(), username, message), true);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
