package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.LoginEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import cn.edu.nju.candleflame.tickets.repository.LoginRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.UserInfoRepository;
import cn.edu.nju.candleflame.tickets.service.MailService;
import cn.edu.nju.candleflame.tickets.util.JsonUtil;
import cn.edu.nju.candleflame.tickets.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private TheaterInfoRepository theaterInfoRepository;

    @RequestMapping(value = {"/login","/"})
    public String login(ModelMap map){
        return "login";
    }

    @PostMapping(value = "login")
    public String login(HttpSession session,ModelMap map, @RequestParam("account")String username,@RequestParam("pass")String pass){
        LoginEntity loginEntity=loginRepository.findByEmailAndPass(username,MD5Util.encode(pass));

        if (loginEntity==null){
            map.addAttribute("tip","用户名或者密码错误");
            return "login";
        }

        switch (loginEntity.getType()){
            case 0:
                if (userInfoRepository.findByEmail(username).isMember()){
                    session.setAttribute("email",username);
                    session.setAttribute("type", UserType.USER);
                    return "redirect:/user/allShows";
                }else{
                    map.addAttribute("tip","此用户已被注销");
                    return "/login";
                }
            case 1:
                session.setAttribute("theaterID",username);
                session.setAttribute("type", UserType.THEATER);
                return "redirect:/theater/myAllShow";
            case 2:
                session.setAttribute("type",UserType.ADMIN);
                return "redirect:/admin/information";
            default:
                return "login";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session,ModelMap map){
        session.removeAttribute("email");
        session.removeAttribute("theaterID");
        session.removeAttribute("type");
        return "login";
    }

    @RequestMapping(value = "/register")
    public String register(ModelMap map){

        return "register";
    }
    @RequestMapping(value = "/registerTheater")
    public String registerTheater(ModelMap map){

        return "registerForTheater";
    }

    @RequestMapping(value = "/registerMail",method = RequestMethod.POST)
    public String sendRegisterMail(ModelMap map,@RequestParam("inputEmail")String email, @RequestParam("inputPassword")String pass, @RequestParam("inputPasswordConfirm")String confirm){

        //两次密码不正确
        if (!pass.equals(confirm)){
            map.addAttribute("tip","两次密码不一致");
            return "register";
        }

        LoginEntity login=loginRepository.findByEmail(email);
        if (login!=null){
            if (login.isIsok()){ //已经注册成功
                map.addAttribute("tip","此邮箱已经注册");
                return "register";
            }else{  //邮箱尚未验证
                map.addAttribute("tip","请去邮箱认证");
                return "register";
            }
        }

        //创建新用户
        String code= MD5Util.encode(email+pass+ Math.random());
        LoginEntity user=new LoginEntity(email,MD5Util.encode(pass),0,false,code);
        loginRepository.save(user);


        String url="http:localhost:8080/activate/"+code;
        String content = ("Hi , <br/>" +
                "<br/>" +
                "Your user account with the e-mail address <strong>" + email + "</strong> has been created. \n" +
                "<br/>" +
                "Please follow the link below to activate your account.\n") +
                "<br/>" + url;

        mailService.sendMail(email,"注册新会员", content);

        return "sendMail";
    }

    @RequestMapping(value = "/registerTheater",method = RequestMethod.POST)
    public String registerTheater(ModelMap map, @RequestParam("inputPassword")String pass,@RequestParam("inputEmail")String email,@RequestParam("inputName")String name,@RequestParam("inputPlace")String place ){

        //查找最大的酒店编号，设置新的编号
        int maxNumber=0;
        try {
            maxNumber=Integer.parseInt(theaterInfoRepository.findMaxTheaterId());
        }catch (Exception ignored){
            //如果是第一个注册酒店的人
        }
        maxNumber+=1;
        TheaterInfoEntity theaterInfoEntity=new TheaterInfoEntity();
        theaterInfoEntity.setTheaterid(String.format("%07d",maxNumber));
        //创建message
        HashMap<String ,String> messageMap=new HashMap<>();
        messageMap.put("name",name);
        messageMap.put("place",place);
        messageMap.put("email",email);
        messageMap.put("pass",pass);
        theaterInfoEntity.setMessage(JsonUtil.toJson(messageMap));
        theaterInfoRepository.save(theaterInfoEntity);

        //跳转提示界面
        map.addAttribute("text","管理员认证中，请等待邮件通知");
        return "emailResponse";
    }


    @RequestMapping(value = "activate/{code}")
    public String activate(ModelMap map,@PathVariable String code){
        LoginEntity user=loginRepository.findByCode(code);
        if (user==null){  //用户不存在
            map.addAttribute("text","链接不正确");
        }else{
            if (!user.isIsok()){ //用户确实还没验证
                user.setIsok(true);
                loginRepository.save(user);

                UserInfoEntity userInfoEntity=new UserInfoEntity();
                userInfoEntity.setEmail(user.getEmail());
                userInfoRepository.save(userInfoEntity);
                logger.info(user.getEmail()+"邮箱验证完成");
                map.addAttribute("text","成功验证邮箱");
            }else{ //用户已经验证成功
                map.addAttribute("text","无需重复验证");
            }
        }


        return "emailResponse";
    }

}
