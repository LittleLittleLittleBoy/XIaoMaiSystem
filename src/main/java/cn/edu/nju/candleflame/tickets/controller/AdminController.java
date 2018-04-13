package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.LoginEntity;
import cn.edu.nju.candleflame.tickets.entity.RoomEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterInfoEntity;
import cn.edu.nju.candleflame.tickets.repository.LoginRepository;
import cn.edu.nju.candleflame.tickets.repository.RoomRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterRepository;
import cn.edu.nju.candleflame.tickets.service.MailService;
import cn.edu.nju.candleflame.tickets.util.JsonUtil;
import cn.edu.nju.candleflame.tickets.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    MailService mailService;
    @Autowired
    TheaterInfoRepository theaterInfoRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    RoomRepository roomRepository;

    @RequestMapping(value = "/admin/information")
    public String allInformation(@SessionAttribute UserType type, ModelMap map){
        if (type!= UserType.ADMIN){
            return "redirect:/login";
        }
        List<TheaterInfoEntity> allMessage = theaterInfoRepository.findAllMessage();
        map.addAttribute("messages",allMessage);
        return "/admin/information";
    }

    @RequestMapping(value = "/admin/theaterinfo/{theaterid}")
    public String theaterInfoInformation(@PathVariable String theaterid,@SessionAttribute UserType type, ModelMap map){
        if (type!= UserType.ADMIN){
            return "redirect:/login";
        }

        if (theaterRepository.findByTheaterid(theaterid)==null){
            map.addAttribute("text","输入有误");
            return "emailResponse";
        }
        map.addAttribute("theaterInfo",theaterRepository.findByTheaterid(theaterid));
        map.addAttribute("rooms",roomRepository.findAvaliableRoom(theaterid));

        return "admin/theaterinfo";
    }

    @RequestMapping(value = "/admin/undoinformation")
    public String undoInformation(@SessionAttribute UserType type, ModelMap map){
        if (type!= UserType.ADMIN){
            return "redirect:/login";
        }
        List<TheaterInfoEntity> allMessage = theaterInfoRepository.findAllUndoMessage();
        map.addAttribute("messages",allMessage);
        return "/admin/information";
    }

    @RequestMapping(value = "/admin/action",method = RequestMethod.POST)
    @ResponseBody
    public String handleInformation(@SessionAttribute UserType type, int id,boolean isOk){

        if (type!= UserType.ADMIN){
            return "redirect:/login";
        }
        TheaterInfoEntity theaterInfoEntity=theaterInfoRepository.findById(id);
        theaterInfoEntity.setIsread(true);
        theaterInfoEntity.setIsok(isOk);
        theaterInfoRepository.save(theaterInfoEntity);

        Map<String,String> information= JsonUtil.fromString(theaterInfoEntity.getMessage());

        String toEmail=null;
        if (theaterInfoEntity.getType()==0){
            toEmail=information.get("email");
        }else{
            toEmail=theaterRepository.findByTheaterid(theaterInfoEntity.getTheaterid()).getEmail();
        }
        StringBuffer content=new StringBuffer();
        if (isOk){
            switch (theaterInfoEntity.getType()){
                case 0:
                    TheaterEntity theaterEntity=new TheaterEntity(theaterInfoEntity.getTheaterid());
                    theaterEntity.setName(information.get("name"));
                    theaterEntity.setPlace(information.get("place"));
                    theaterEntity.setEmail(information.get("email"));
                    theaterRepository.save(theaterEntity);

                    LoginEntity loginEntity=new LoginEntity(theaterInfoEntity.getTheaterid(),
                            MD5Util.encode(information.get("pass")),
                                    1,
                                    true,
                                    "");
                    loginEntity.setIsok(true);
                    loginEntity.setType(1);
                    loginRepository.save(loginEntity);
                    content.append("您的账号"+theaterInfoEntity.getTheaterid()+"可以正常使用");

                    break;
                case 1:
                    TheaterEntity theaterEntity1=theaterRepository.findByTheaterid(theaterInfoEntity.getTheaterid());
                    if (information.containsKey("name")){
                        theaterEntity1.setName(information.get("name"));
                    }
                    if (information.containsKey("place")){
                        theaterEntity1.setPlace(information.get("place"));
                    }
                    if (information.containsKey("email")){
                        theaterEntity1.setPlace(information.get("email"));
                    }
                    theaterRepository.save(theaterEntity1);
                    toEmail=theaterRepository.findByTheaterid(theaterInfoEntity.getTheaterid()).getEmail();
                    content.append("您的基本信息已经修改");
                    break;
                case 2:
                    int col=Integer.parseInt(information.get("col"));
                    int row=Integer.parseInt(information.get("row"));
                    String roomid=information.get("roomid");
                    String theaterid=theaterInfoEntity.getTheaterid();
                    String roominfo=information.get("roominfo");
                    RoomEntity roomEntity=new RoomEntity(theaterid,roomid,roominfo,row,col);

                    roomRepository.save(roomEntity);
                    content.append(information.get("roomid")).append("已经保存成功");
                    break;
                case 3:
                    RoomEntity roomEntity1 = roomRepository.findByTheateridAndRoomid(theaterInfoEntity.getTheaterid(), information.get("roomid"));
                    roomEntity1.setIsdelete(true);
                    roomRepository.save(roomEntity1);
                    content.append(information.get("roomid")).append("已经删除");
                    break;
            }
        }else {
            switch (theaterInfoEntity.getType()){
                case 0:
                    content.append("您的注册请求未被处理");
                    break;
                case 1:
                    content.append("您的修改基本信息请求未被处理");
                    break;
                case 2:
                    content.append("管理员拒绝创建").append(information.get("roomid"));
                    break;
                case 3:
                    content.append("管理员拒绝删除").append(information.get("roomid"));
                    break;
            }
        }

        mailService.sendMail(toEmail,"申请信息",content.toString());
        return "ok";
    }
}
