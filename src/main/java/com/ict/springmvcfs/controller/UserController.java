package com.ict.springmvcfs.controller;

import com.ict.springmvcfs.service.UserServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserServlet userServlet;

    @GetMapping("/test")
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userServlet.doGet(request, response);
    }

    @PostMapping("/test")
    public void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        userServlet.doPost(request, response);
    }

}
