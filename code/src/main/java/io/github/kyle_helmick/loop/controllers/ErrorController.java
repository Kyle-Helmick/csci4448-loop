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

  /**
   * This route handles visible errors.
   * @param request the request causing the error
   * @return a model and view that fills out the error template
   */
  @RequestMapping("/error")
  public ModelAndView handleError(HttpServletRequest request) {

    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    Map<String, Object> view = new HashMap<>();

    view.put("pageTitle", "Error");

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());
      HttpStatus code = HttpStatus.valueOf(statusCode);

      switch (code) {
        case NOT_FOUND:
          view.put("error", "Page misplaced, please don't try again.");
          break;
        case FORBIDDEN:
          view.put("error", "I'm sorry, Dave. I'm afraid I can't do that.");
          break;
        default:
          System.out.println(code);
          view.put("error", "Error not supported? Monkeys?");
          break;
      }
    }

    return new ModelAndView("errorPage", view);
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
