package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.TicketEntity;
import cn.edu.nju.candleflame.tickets.model.CheckInformationModel;
import cn.edu.nju.candleflame.tickets.model.RoomModel;
import cn.edu.nju.candleflame.tickets.model.TicketModel;
import cn.edu.nju.candleflame.tickets.service.TicketService;
import cn.edu.nju.candleflame.tickets.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/user/getShowSeat",method = RequestMethod.POST)
    @ResponseBody
    public String getSeats(@SessionAttribute UserType type, @RequestParam("showid")int showid){
        RoomModel roomModel = ticketService.getRoomModel(showid);
        return GsonUtil.toString(roomModel);
    }

    @RequestMapping(value = "/user/orderTicket",method = RequestMethod.POST)
    @ResponseBody
    public String orderTicket(@SessionAttribute String email, @SessionAttribute UserType type,
                              @RequestParam("showid")int showid,
                              @RequestParam("position")String position,
                              @RequestParam("number")int number,
                              @RequestParam("price")double price){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        return ticketService.orderTicket(showid,position,email,number,price,false);
    }
    @RequestMapping(value = "/user/destribute",method = RequestMethod.POST)
    @ResponseBody
    public String destribute(@SessionAttribute String email, @SessionAttribute UserType type,
                             @RequestParam("number1")int number1,
                             @RequestParam("number2")int number2,
                             @RequestParam("number3")int number3,
                             @RequestParam("showid")int showid){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        return ticketService.destribute(email,number1,number2,number3,showid);
    }

    @RequestMapping(value = "/user/showTicketInfo",method = RequestMethod.POST)
    public String showTicketInfo(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map,@RequestParam("ticketid")String ticketid){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        TicketEntity ticketEntity=ticketService.findTicket(email,ticketid);
        map.addAttribute("ticketid",ticketEntity.getTicketid());
        map.addAttribute("price",ticketEntity.getPrice());
        map.addAttribute("createTime",ticketEntity.getCreatetime());
        map.addAttribute("money",ticketEntity.getUser().getMoney());
        map.addAttribute("integral",ticketEntity.getUser().getScore());
        map.addAttribute("fees",TicketService.FEES);

        map.addAttribute("name",email);

        return "user/pay";
    }

    @RequestMapping(value = "/user/pay",method = RequestMethod.POST)
    @ResponseBody
    public String userPay(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap modelMap,@RequestParam("useIntegral")boolean  useIntegral,@RequestParam("ticketid")String ticketid,@RequestParam("pass")String pass){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        String message=ticketService.userPay(useIntegral,email,ticketid,pass);

        return message;
    }


    @RequestMapping(value = "/user/getAllTicket")
    public String getAllTicket(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        List<TicketModel> tickets=ticketService.getAllTicket(email);

        map.addAttribute("tickets",tickets);
        map.addAttribute("name",email);

        return "/user/orders";
    }
    @RequestMapping(value = "/user/getFutureTicket")
    public String getFutureTicket(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        List<TicketModel> tickets=ticketService.getFutureTicket(email);
        map.addAttribute("tickets",tickets);
        map.addAttribute("name",email);

        return "/user/orders";
    }
    @RequestMapping(value = "/user/getUnpayTicket")
    public String getUnpayTicket(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        List<TicketModel> tickets=ticketService.getUnpayTicket(email);
        map.addAttribute("tickets",tickets);
        map.addAttribute("name",email);

        return "/user/orders";
    }
    @RequestMapping(value = "/user/getCompleteTicket")
    public String getCompleteTicket(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        List<TicketModel> tickets=ticketService.getCompleteTicket(email);
        map.addAttribute("tickets",tickets);
        map.addAttribute("name",email);

        return "/user/orders";
    }
    @RequestMapping(value = "/user/getCancelTicket")
    public String getCancelTicket(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        List<TicketModel> tickets=ticketService.getCancelTicket(email);
        map.addAttribute("tickets",tickets);
        map.addAttribute("name",email);

        return "/user/orders";
    }

    @RequestMapping(value = "/user/cancelTicket",method = RequestMethod.POST)
    @ResponseBody
    public String cancelTicket(@SessionAttribute String email, @SessionAttribute UserType type,@RequestParam("ticketid")String ticketid){
        if (type!=UserType.USER){
            return "redirect:/";
        }

        String result=ticketService.cancelTicket(email,ticketid);
        return result;
    }

    @RequestMapping(value = "/checkTicket",method = RequestMethod.POST)
    @ResponseBody
    public String checkTicket(@RequestParam("ticketid")String ticketid){
        return GsonUtil.toString(ticketService.checkTicket(ticketid));
    }

}
