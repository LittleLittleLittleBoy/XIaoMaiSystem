package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.LoginEntity;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import cn.edu.nju.candleflame.tickets.model.RoomModel;
import cn.edu.nju.candleflame.tickets.repository.LoginRepository;
import cn.edu.nju.candleflame.tickets.repository.UserInfoRepository;
import cn.edu.nju.candleflame.tickets.service.TicketService;
import cn.edu.nju.candleflame.tickets.util.GsonUtil;
import cn.edu.nju.candleflame.tickets.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OffTicketController {
    @Autowired
    TicketService ticketService;
    @Autowired
    LoginRepository loginRepository;

    @RequestMapping(value = "/theater/getShowSeat",method = RequestMethod.POST)
    @ResponseBody
    public String getSeats(@SessionAttribute String theaterID, @SessionAttribute UserType type,
                           @RequestParam("showid")int showid){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        RoomModel roomModel = ticketService.getRoomModel(showid);
        return GsonUtil.toString(roomModel);
    }

    @RequestMapping(value = "/theater/addOffTicket",method = RequestMethod.POST)
    @ResponseBody
    public String addOffTicket(@SessionAttribute String theaterID, @SessionAttribute UserType type,
                               @RequestParam("showid")int showid,@RequestParam("position")String position){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        String result= ticketService.addOffTicket(showid,position);
        return result;
    }

    @RequestMapping(value = "/theater/addOrderOffTicket",method = RequestMethod.POST)
    @ResponseBody
    public String addOrderOffTicket(@SessionAttribute String theaterID, @SessionAttribute UserType type,
                               @RequestParam("email")String email,@RequestParam("pass")String pass,
                                    @RequestParam("showid")int showid,
                                    @RequestParam("position")String position,
                                    @RequestParam("number")int number,
                                    @RequestParam("price")double price){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        if (email==null||email.equals("")||pass==null||pass.equals("")){
            return "用户名和密码不能为空";
        }

        LoginEntity loginEntity=loginRepository.findByEmailAndPass(email, MD5Util.encode(pass));
        if (loginEntity!=null&&loginEntity.getType()==0){
            return ticketService.orderTicket(showid,position,email,number,price,true);
        }else{
            return "用户名或者密码有误";
        }
    }
}
