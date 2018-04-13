package cn.edu.nju.candleflame.tickets;

import cn.edu.nju.candleflame.tickets.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class MailTest {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailService mailService;


    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1216832052@qq.com");
        message.setTo("jqgsdm79521@chacuo.net");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

//        mailSender.send(message);
    }

    @Test
    public void sendTemplateMail() throws Exception {

//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setFrom("1075778700@qq.com");
//        helper.setTo("1150170525@qq.com");
//        helper.setSubject("主题：模板邮件");
//
//        Map<String, Object> model = new HashMap();
//        model.put("username", "didi");
//        String text = VelocityEngineUtils.mergeTemplateIntoString(
//                velocityEngine, "register.html", "UTF-8", model);
//        helper.setText(text, true);
        //创建邮件正文
//        Context context = new Context();
//        context.setVariable("username", "明知着");
//        String emailContent = templateEngine.process("register", context);

//        String emailContent="尊敬的Github用户您好，您在我们平台创建项目中的代码可能涉及到隐私泄漏，为保护您的个人信息，请及时修改QQ邮箱授权码";
//        mailService.sendMail("151250114@smail.nju.edu.cn","安全漏洞",emailContent);

    }


}
