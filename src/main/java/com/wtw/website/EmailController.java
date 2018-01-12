package com.wtw.website;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HPC
 * @create 2018-01-11 12:50
 * @desc 发送邮箱控制器
 **/
@RestController
@RequestMapping("/email")
public class EmailController {
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public void sendEmail(@RequestBody JSONObject json) throws Exception {
        String name=json.getString("name");
        String type=json.getString("type");
        String email=json.getString("email");
        String message=json.getString("message");
        String [] mailTo = {"874694517@qq.com","1785320551@qq.com"};
        String [] cc =null;
        String [] ms =null;
        String host = "smtp.163.com"; // smtp服务器
        String user = "15111066861@163.com"; // 用户名
        String pwd = "email163"; // 密码
        String from = "15111066861@163.com"; // 发件人地址
        MailUtils mu = MailUtils.getInstance(host, user, pwd, from);
        mu.send(name,mailTo,cc,ms, "威拓威官网", message, null);
    }
}
