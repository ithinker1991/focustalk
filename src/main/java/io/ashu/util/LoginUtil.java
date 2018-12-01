package io.ashu.util;

import io.ashu.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class LoginUtil {
  public static void markAsLogin(Channel channel) {
    channel.attr(Attributes.LOGIN).set(true);
  }

  public static boolean hasLogin(Channel channel) {
    return channel.attr(Attributes.LOGIN).get() != null;
  }

}
