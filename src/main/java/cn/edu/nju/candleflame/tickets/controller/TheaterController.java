package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.TheaterInfoEntity;
import cn.edu.nju.candleflame.tickets.repository.RoomRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterRepository;
import cn.edu.nju.candleflame.tickets.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    TheaterInfoRepository theaterInfoRepository;
    @Autowired
    RoomRepository roomRepository;

    @RequestMapping(value = "/information")
    public String getTheaterInformation(@SessionAttribute String theaterID, @SessionAttribute UserType type, ModelMap map){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }


        map.addAttribute("theaterInfo",theaterRepository.findByTheaterid(theaterID));
        map.addAttribute("rooms",roomRepository.findAvaliableRoom(theaterID));
        return "/theater/information";
    }

    @RequestMapping(value = "/changeInformation",method = RequestMethod.POST)
    @ResponseBody
    public String changeInformation(@SessionAttribute String theaterID, @SessionAttribute UserType type,@RequestParam("userName") String name,@RequestParam("place") String place,@RequestParam("email") String email){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }

        HashMap<String,String> hashMap=new HashMap<>();
        if (name != null && !"".equals(name)) {
            hashMap.put("name",name);
        }
        if (place != null && !"".equals(place)) {
            hashMap.put("place",place);
        }
        if (email != null && !"".equals(email)) {
            hashMap.put("email",email);
        }
        TheaterInfoEntity theaterInfoEntity=new TheaterInfoEntity();
        theaterInfoEntity.setTheaterid(theaterID);
        theaterInfoEntity.setMessage(JsonUtil.toJson(hashMap));
        theaterInfoEntity.setType(1);
        theaterInfoRepository.save(theaterInfoEntity);
        return "true";
    }

    @RequestMapping(value = "/addNewRoom",method = RequestMethod.POST)
    @ResponseBody
    public String addNewRoom(@SessionAttribute String theaterID, @SessionAttribute UserType type,
                             @RequestParam("roomid")String roomName,
                             @RequestParam("roominfo")String message,
                             @RequestParam("row")int row,
                             @RequestParam("col")int col){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }

        if (roomName==null||message==null||roomName.equals("")||message.equals("")||row<=0||col<=0){
            return "信息有误";
        }
        if (roomRepository.findByTheateridAndRoomid(theaterID,roomName)==null){
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("roomid",roomName);
            hashMap.put("roominfo",message);
            hashMap.put("row",row+"");
            hashMap.put("col",col+"");
            TheaterInfoEntity theaterInfoEntity=new TheaterInfoEntity();
            theaterInfoEntity.setTheaterid(theaterID);
            theaterInfoEntity.setMessage(JsonUtil.toJson(hashMap));
            theaterInfoEntity.setType(2);
            theaterInfoRepository.save(theaterInfoEntity);


            return "创建成功";
        }else{
            return "房间已存在";
        }

    }

    @RequestMapping(value = "/deleteRoom",method = RequestMethod.POST)
    public String deleteRoom(@SessionAttribute String theaterID, @SessionAttribute UserType type,
                             @RequestParam("roomid")String roomName){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("roomid",roomName);
        TheaterInfoEntity theaterInfoEntity=new TheaterInfoEntity();
        theaterInfoEntity.setTheaterid(theaterID);
        theaterInfoEntity.setMessage(JsonUtil.toJson(hashMap));
        theaterInfoEntity.setType(3);
        theaterInfoRepository.save(theaterInfoEntity);
        return "redirect:/theater/information";
    }


}
