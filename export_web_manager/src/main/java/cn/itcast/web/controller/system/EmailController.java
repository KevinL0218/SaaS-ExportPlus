package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Email;
import cn.itcast.service.system.EmailService;
import cn.itcast.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController extends BaseController {
    @Autowired
    private EmailService emailService;

    @RequestMapping("/findEmail")
    @ResponseBody
    public List<Email> findEmail(String userId) {
        List<Email> emailList = emailService.findByUserId(userId);
        session.setAttribute("emailList", emailList);
        return emailList;
    }

    @RequestMapping("/deleteEmail")
    public String deleteEmail(String emailId, String userId) {
        emailService.deleteEmail(emailId);
        List<Email> emailList = emailService.findByUserId(userId);
        session.setAttribute("emailList", emailList);
        return "/home/main";
    }
}
