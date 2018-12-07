package io.ashu.util;

import io.ashu.attribute.Attributes;
import io.netty.channel.Channel;
import io.ashu.session.Session;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
  private static Map<String, Channel> userIDChannelMap = new ConcurrentHashMap<>();
  private static Map<String, List<String>> groupMap = new ConcurrentHashMap<>();

  public static void buildSession(Channel channel, Session session) {
    channel.attr(Attributes.SESSION).set(session);
    userIDChannelMap.put(session.getUserId(), channel);
  }

  public static boolean createGroup(String groupId, List<String> users) {
    if (groupMap.containsKey(groupId)) {
      System.err.println(new Date() + " 该群[" + groupId + "]已经存在");
      return false;
    } else {
      groupMap.put(groupId, users);
      return true;
    }
  }

  public static List<String> getGroupMembersById(String id) {
    return groupMap.get(id);
  }

  public static boolean hasLogin(Channel channel) {
    return channel.attr(Attributes.SESSION).get() != null;
  }

  public static Channel getChannelByUserId(String userId) {
    return userIDChannelMap.get(userId);
  }

  public static Session getSession(Channel channel) {
    return channel.attr(Attributes.SESSION).get();
  }



}
