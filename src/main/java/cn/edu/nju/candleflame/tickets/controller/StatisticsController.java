package cn.edu.nju.candleflame.tickets.controller;

import cn.edu.nju.candleflame.tickets.constant.UserType;
import cn.edu.nju.candleflame.tickets.entity.UserInfoEntity;
import cn.edu.nju.candleflame.tickets.repository.TheaterRepository;
import cn.edu.nju.candleflame.tickets.repository.UserInfoRepository;
import cn.edu.nju.candleflame.tickets.repository.UserStatisticsRepository;
import cn.edu.nju.candleflame.tickets.service.UserRankService;
import cn.edu.nju.candleflame.tickets.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {

    @Autowired
    UserStatisticsRepository userStatisticsRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserRankService userRankService;
    @Autowired
    TheaterRepository theaterRepository;

    @RequestMapping(value = "/user/statistics")
    public String getUserStatistics(@SessionAttribute String email, @SessionAttribute UserType type,ModelMap modelMap){
        if (type!= UserType.USER){
            return "redirect:/";
        }

        String[] result = userStatisticsRepository.findByUserStatistics(email);

        UserInfoEntity userInfoEntity = userInfoRepository.findByEmail(email);

        if (result!=null){
            try {
                modelMap.addAttribute("allTicket",result[0]==null?0:(int)(Double.parseDouble(result[0])));

            }catch (Exception e){

            }
            try {
                modelMap.addAttribute("cancelTicket",result[1]==null?0:(int)(Double.parseDouble(result[1])));

            }catch (Exception e){

            }
            try {
                modelMap.addAttribute("totalMoney",result[2]==null?0:Double.parseDouble(result[2]));

            }catch (Exception e){

            }
        }

        modelMap.addAttribute("name",email);

        return "/user/statistics";
    }

    @RequestMapping(value = "/admin/statistics")
    public String getAdminStatistics(@SessionAttribute UserType type,ModelMap modelMap){

        if (type!= UserType.ADMIN){
            return "redirect:/login";
        }

        Object[] scores= userStatisticsRepository.findAllScore();
        int[] peopleNumber=new int[5];
        for (int i=0;i<scores.length;i++){
            int rank = userRankService.getRank((int) scores[i]);
            peopleNumber[rank-1]++;
        }
        List<Info> vipList=new ArrayList<>();
        for (int i=0;i<peopleNumber.length;i++){
            vipList.add(new Info("vip"+(i+1),peopleNumber[i]));
        }

        modelMap.addAttribute("vipInfo", GsonUtil.toString(vipList));


        List<Object[]> money=userStatisticsRepository.findMoneyByDate();

        String[] dates=new String[money.size()];
        double[] buys=new double[money.size()];
        double[] cancels=new double[money.size()];
        for (int i=0;i<money.size();i++){
            dates[i]=money.get(i)[0].toString();
            buys[i]=money.get(i)[1]==null?0:Double.parseDouble(money.get(i)[1].toString());
            cancels[i]=money.get(i)[2]==null?0:Double.parseDouble(money.get(i)[2].toString());
        }
        Map<String,Object> moneyMap=new HashMap<>();
        moneyMap.put("dates",dates);
        moneyMap.put("buys",buys);
        moneyMap.put("cancels",cancels);
        modelMap.addAttribute("moneyInfo",GsonUtil.toString(moneyMap));

        return "/admin/statistics";
    }


    @RequestMapping(value = "/theater/statistics")
    public String getTheaterStatistics(@SessionAttribute String theaterID, @SessionAttribute UserType type,ModelMap modelMap){
        if (type!=UserType.THEATER){
            return "redirect:/login";
        }

        double money = theaterRepository.findByTheaterid(theaterID).getMoney();

        List<Object[]> result = userStatisticsRepository.findByTheaterStatistics(theaterID);
        ShowStatistics[] showStatistics=new ShowStatistics[result.size()];
        for (int i=0;i<result.size();i++){
            showStatistics[i]=new ShowStatistics();
            showStatistics[i].showid=Integer.parseInt(result.get(i)[0].toString());
            showStatistics[i].success=Integer.parseInt(result.get(i)[1].toString());
            showStatistics[i].cancel=Integer.parseInt(result.get(i)[2].toString());
        }



        modelMap.addAttribute("shows",showStatistics);
        modelMap.addAttribute("money",money);

        return "/theater/statistics";
    }

    class ShowStatistics{
        int showid;
        int success;
        int cancel;

        public int getShowid() {
            return showid;
        }

        public void setShowid(int showid) {
            this.showid = showid;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public int getCancel() {
            return cancel;
        }

        public void setCancel(int cancel) {
            this.cancel = cancel;
        }
    }

    class Info{
        String name;
        double value;

        public Info(String name, double value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

}
