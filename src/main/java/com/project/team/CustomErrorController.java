package com.project.team;

import jakarta.servlet.RequestDispatcher;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;


@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(WebRequest webRequest) {
        int statusCode = (int) webRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, WebRequest.SCOPE_REQUEST);
        if (statusCode == 404) {
            return "error/404";
        } else if (statusCode == 500) {
            return "error/500";
        } else if (statusCode == 400) {
            return "error/400";
        }
        return "error/default";
    }
}
