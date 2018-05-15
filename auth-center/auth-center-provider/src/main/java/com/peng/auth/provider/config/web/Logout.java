package com.peng.auth.provider.config.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fp295 on 2018/5/15.
 */
@Controller
public class Logout {
    @RequestMapping("/backReferer")
    public void exit(HttpServletRequest request, HttpServletResponse response) {

        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
