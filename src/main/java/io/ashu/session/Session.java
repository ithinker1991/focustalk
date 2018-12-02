package io.ashu.session;

import lombok.Data;

@Data
public class Session {
  private String userId;
  private String userName;

  public Session(String userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "userId:'" + userId + ", userName='" + userName;
  }
}
