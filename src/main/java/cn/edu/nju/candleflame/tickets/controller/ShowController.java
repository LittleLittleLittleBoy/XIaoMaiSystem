package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.ShowType;
import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.ShowInfoEntity;
import cn.edu.nju.candleflame.tickets.entity.TheaterEntity;
import cn.edu.nju.candleflame.tickets.repository.RoomRepository;
import cn.edu.nju.candleflame.tickets.repository.ShowInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.TheaterRepository;
import cn.edu.nju.candleflame.tickets.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class ShowController {
    @Autowired
    ShowInfoRepository showInfoRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    RoomRepository roomRepository;

    @RequestMapping(value = "/theater/myAllShow")
    public String getAllShowsByTheater(@SessionAttribute String theaterID, @SessionAttribute UserType type,ModelMap map){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        TheaterEntity theaterEntity=theaterRepository.findByTheaterid(theaterID);
        List<ShowInfoEntity> showInfos= showInfoRepository.findAllByTheaterOrderByTimeDesc(theaterEntity);
        map.addAttribute("shows",showInfos);

        addAddInformation(map,theaterEntity);

        return "/theater/shows";
    }

    @RequestMapping(value = "/theater/myFutureShow")
    public String getFutureShowsByTheater(@SessionAttribute String theaterID, @SessionAttribute UserType type,ModelMap map){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        TheaterEntity theaterEntity=theaterRepository.findByTheaterid(theaterID);

        List<ShowInfoEntity> showInfos= showInfoRepository.findAllByTimeAfterAndTheaterOrderByTimeDesc(new Timestamp(new java.util.Date().getTime()),theaterEntity);
        map.addAttribute("shows",showInfos);

        addAddInformation(map,theaterEntity);

        return "/theater/shows";
    }
    @RequestMapping(value = "/theater/myBeforeShow")
    public String getBeforeShowsByTheater(@SessionAttribute String theaterID, @SessionAttribute UserType type,ModelMap map){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        TheaterEntity theaterEntity=theaterRepository.findByTheaterid(theaterID);

        List<ShowInfoEntity> showInfos= showInfoRepository.findAllByTimeBeforeAndTheaterOrderByTimeDesc(new Timestamp(new java.util.Date().getTime()),theaterEntity);
        map.addAttribute("shows",showInfos);

        addAddInformation(map,theaterEntity);

        return "/theater/shows";
    }

    @RequestMapping(value = "/theater/addNewShowInfo",method = RequestMethod.POST)
    @ResponseBody
    public String addNewSHowInfo(
            @SessionAttribute String theaterID, @SessionAttribute UserType type,
            @RequestParam("title")String title,
            @RequestParam("roomid")String roomid,
            @RequestParam("time")String timestamp,
            @RequestParam("price1")double price1,
            @RequestParam("price2")double price2,
            @RequestParam("price3")double price3,
            @RequestParam("type")String type1,
            @RequestParam("description")String description
    ){

        if (type!=UserType.THEATER){
            return "redirect:/login";
        }
        Timestamp timestamp1= TimeUtil.getTimeStamp(timestamp);
        if ("".equals(title) || "".equals(roomid) || timestamp1 == null || price1 <= 0 || price2 <= 0 || price3 <= 0 || "".equals(type)||description==null|"".equals(description)) {
            return "填写信息有误";
        }
        ShowInfoEntity showInfoEntity=new ShowInfoEntity();
        showInfoEntity.setTheater(theaterRepository.findByTheaterid(theaterID));
        showInfoEntity.setRoomid(roomid);
        showInfoEntity.setTitle(title);
        showInfoEntity.setTime(timestamp1);
        showInfoEntity.setPrice1(price1);
        showInfoEntity.setPrice2(price2);
        showInfoEntity.setPrice3(price3);
        showInfoEntity.setType(type1);
        showInfoEntity.setDescription(description);
        showInfoRepository.save(showInfoEntity);
        return "true";
    }
    @RequestMapping(value = "/theater/shows/{showid}")
    public String getTheaterShowDetail(@SessionAttribute String theaterID, @SessionAttribute UserType type,ModelMap map,@PathVariable int showid){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }

        map.addAttribute("show",showInfoRepository.findByShowid(showid));
        return "/theater/detail";
    }
    @RequestMapping(value = "/user/shows/{showid}")
    public String getShowDetail(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap map,@PathVariable int showid){
        if (type!=UserType.USER){
            return "redirect:/";
        }


        map.addAttribute("show",showInfoRepository.findByShowid(showid));
        map.addAttribute("name",email);
        return "/user/detail";
    }



    @RequestMapping(value = "/user/allShows")
    public String getAllShows(@SessionAttribute String email, @SessionAttribute UserType type, ModelMap map){

        if (type!=UserType.USER){
            return "redirect:/";
        }

        map.addAttribute("name",email);

        List<ShowInfoEntity> shows=showInfoRepository.findAllByTimeAfterOrderByTimeDesc(new Timestamp(new java.util.Date().getTime()));

        map.addAttribute("shows",shows);
        return "/user/findShows";
    }

    private void addAddInformation(ModelMap map,TheaterEntity theaterEntity){
        map.addAttribute("rooms",roomRepository.findAvaliableRoom(theaterEntity.getTheaterid()));
        map.addAttribute("types", ShowType.getShowType());
    }


}
