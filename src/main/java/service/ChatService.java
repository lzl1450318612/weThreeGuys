package service;

import entity.Message;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import utils.DateUtils;

import java.util.Date;

/**
 * @author lizilin
 */
public class ChatService {

    private static ChannelGroup channelGroup;


    static {
        channelGroup = new DefaultChannelGroup("WeThreeGuys", GlobalEventExecutor.INSTANCE);
    }

    public void addUser(String username, Channel channel) {
        channelGroup.add(channel);
        Message message = new Message(new Date(), username, username + "已经连接了尻！");
        sendMessageToAll(message, false);
        System.out.println(message);
    }

    public void removeUser(String username, Channel channel) {
        channelGroup.remove(channel);
        Message message = new Message(new Date(), username, username + "的尻连接已丢失！");
        sendMessageToAll(message, false);
        System.out.println(message);
    }

    public String sendMessageToAll(Message message, boolean isMessage) {
        StringBuilder result = new StringBuilder();
        if (isMessage) {
            result.append(DateUtils.parseDate(message.getDate())).append("---").append(message.getUsername()).append(" ").append("抠了一下尻然后说：").append(message.getMessage()).append("\n");
        } else {
            result.append(DateUtils.parseDate(message.getDate())).append("---").append(message.getMessage());
        }
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(result.toString());
        }
        return result.toString();
    }
}
