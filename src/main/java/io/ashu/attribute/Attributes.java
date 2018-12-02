package io.ashu.attribute;

import io.netty.util.AttributeKey;
import io.ashu.session.*;


public interface Attributes {
  AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
