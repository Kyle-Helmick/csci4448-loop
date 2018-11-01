package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.Message;
import io.github.kyle_helmick.loop.models.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SocketController {

  /**
   * This route handles websocket requests on /secured/chat and sends results to /secured/messages.
   * @param msg is a Message that the client has sent to the server
   * @return an Output message with the sender and internal text
   */
  @MessageMapping("/secured/chat")
  @SendTo("/secured/messages")
  public OutputMessage send(Message msg) {
    return new OutputMessage(
      msg.getFrom(),
      msg.getText(),
      new SimpleDateFormat("HH:mm").format(new Date()));
  }
}
