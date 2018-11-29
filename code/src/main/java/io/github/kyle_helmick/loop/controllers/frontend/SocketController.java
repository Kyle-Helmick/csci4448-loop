package io.github.kyle_helmick.loop.controllers.frontend;

import io.github.kyle_helmick.loop.models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

  /**
   * Probably deprecated.
   * @param msg is a Message that the client has sent to the server
   */
  @MessageMapping("/secured/chat")
  @SendTo("/secured/messages")
  public void send(Message msg) {
  }

}
