package io.github.kyle_helmick.loop.models;

public class Message {

  private String status;
  private Object data;

  public Message(String status, Object data) {
    this.status = status;
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public static class Builder {

    private String status;
    private Object data;

    public Builder withError() {
      this.status = "error";
      return this;
    }

    public Builder withSuccess() {
      this.status = "success";
      return this;
    }

    public Builder withFail() {
      this.status = "fail";
      return this;
    }

    public Builder withData(Object data) {
      this.data = data;
      return this;
    }

    public Message build() {
      return new Message(this.status, this.data);
    }

  }

}