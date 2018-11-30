package io.ashu.protocol.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public abstract class Packet {

  @JSONField(deserialize = false, serialize = false)
  private Byte version = 1;

  @JSONField(serialize = false)
  public abstract Byte getCommand();

}
