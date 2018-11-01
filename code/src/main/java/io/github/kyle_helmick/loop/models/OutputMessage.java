package io.github.kyle_helmick.loop.models;

public class OutputMessage {

  private String from;
  private String text;
  private String date;

  public OutputMessage() {}

  /**
   * THIS IS ALL TEMPORARY STUFF.
   * TODO: Refactor Message into alerts and actions for user
   * This is the constructor to make an output message.
   * @param from the string to say who it'sfrom
   * @param text the content for the message
   * @param date the date associated with the message
   */
  public OutputMessage(String from, String text, String date) {
    this.from = from;
    this.text = text;
    this.date = date;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
