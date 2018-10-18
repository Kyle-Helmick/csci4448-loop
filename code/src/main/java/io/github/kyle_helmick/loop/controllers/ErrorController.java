package io.github.kyle_helmick.loop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  @RequestMapping("/error")
  public ModelAndView handleError(HttpServletRequest request) {

    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    Map<String, Object> model = new HashMap<>();

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());
      HttpStatus code = HttpStatus.valueOf(statusCode);

      switch (code) {
        case NOT_FOUND:
          model.put("error", "Page misplaced, please don't try again.");
          break;
        case FORBIDDEN:
          model.put("error", "I'm sorry, Dave. I'm afraid I can't do that.");
          break;
        default:
          model.put("error", "Error not supported? Monkeys?");
          break;
      }
    }

    return new ModelAndView("errorPage", model);
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}