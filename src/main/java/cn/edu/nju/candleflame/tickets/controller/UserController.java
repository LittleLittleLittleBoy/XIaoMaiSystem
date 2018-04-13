package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import cn.edu.nju.candleflame.tickets.repository.UserInfoRepository;
import cn.edu.nju.candleflame.tickets.service.UserRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRankService userRankService;

    @RequestMapping(value = "/information")
    public String getInformation(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map) throws Exception {
        if (type!=UserType.USER){
            return "redirect:/";
        }

        UserInfoEntity userInfoEntity=userInfoRepository.findByEmail(email);
        if (userInfoEntity==null){
            throw new Exception("无此用户");
        }
        map.addAttribute("email",userInfoEntity.getEmail());
        map.addAttribute("contentname",userInfoEntity.getName());
        map.addAttribute("integral",userInfoEntity.getScore());
        map.addAttribute("rank",userRankService.getRank(userInfoEntity.getTotal()));
        map.addAttribute("money",userInfoEntity.getMoney());

        map.addAttribute("name",email);

        return "/user/information";
    }

    @RequestMapping(value = "/changeName",method = RequestMethod.POST)
    @ResponseBody
    public String changeName(@SessionAttribute String email, @SessionAttribute UserType type,@RequestParam("name") String name){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        UserInfoEntity userInfoEntity=userInfoRepository.findByEmail(email);
        if (userInfoEntity==null){
            return "false";
        }
        userInfoEntity.setName(name);
        userInfoRepository.save(userInfoEntity);
        return "true";
    }

    @RequestMapping(value = "/addMoney",method = RequestMethod.POST)
    @ResponseBody
    public String addMoney(@SessionAttribute String email, @SessionAttribute UserType type,@RequestParam("money")double money){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        UserInfoEntity userInfoEntity=userInfoRepository.findByEmail(email);
        if (userInfoEntity==null){
            return "false";
        }
        userInfoEntity.setMoney(userInfoEntity.getMoney()+money);
        userInfoRepository.save(userInfoEntity);
        return "true";
    }


    @RequestMapping(value = "/deleteAccount")
    public String deleteAccount(@SessionAttribute String email, @SessionAttribute UserType type){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        UserInfoEntity userInfoEntity=userInfoRepository.findByEmail(email);
        userInfoEntity.setMember(false);
        userInfoRepository.save(userInfoEntity);
        return "redirect:/logout";
    }

}
