package de.jdufner.websocket.domain;

public class Message {

  private long id;
  private String content;

  public Message() {
  }

  public Message(final long id, final String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }
}