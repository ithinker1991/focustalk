package io.ashu.serialize;

import io.ashu.serialize.impl.JSONSerializer;

public interface Serializer {
  Serializer DEFAULT = new JSONSerializer();

  byte getSerializerAlogrithm();

  byte[] serialize(Object object);

  <T> T deserialize(Class<T> clazz, byte[] bytes);
}
