package com.baizhi.wsk.controller;

import com.baizhi.wsk.entity.Admin;
import com.baizhi.wsk.service.AdminService;
import com.baizhi.wsk.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //登陆
    @RequestMapping("/login")
    @ResponseBody
    public String login(Admin admin,String vcode,HttpSession session){

        Object code = session.getAttribute("ServerCode");

        //判断验证码是否正确
        if (code.equals(vcode)) {
            Admin login = adminService.login(admin);

            if (login == null) {
                return "error";
            } else {
                session.setAttribute("admin",login);
                return "ok";
            }
        }else {
            //验证码错误
            return "codeerror";
        }

    }
    //退出
    @RequestMapping("/out")
    public String out(HttpSession session){

        session.invalidate();

        return "redirect:/jsp/login.jsp";
    }

    //验证码
    @RequestMapping("/code")
    public void execute(HttpServletResponse response, HttpSession session) throws IOException {

        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode();
        vcode.write(response.getOutputStream());
        session.setAttribute("ServerCode", code);
    }


}

