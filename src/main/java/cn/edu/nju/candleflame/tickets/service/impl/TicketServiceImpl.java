package cn.edu.nju.candleflame.tickets.service.impl;

import cn.edu.nju.candleflame.tickets.entity.*;
import cn.edu.nju.candleflame.tickets.model.CheckInformationModel;
import cn.edu.nju.candleflame.tickets.model.RoomModel;
import cn.edu.nju.candleflame.tickets.model.SeatModel;
import cn.edu.nju.candleflame.tickets.model.TicketModel;
import cn.edu.nju.candleflame.tickets.repository.*;
import cn.edu.nju.candleflame.tickets.service.BankService;
import cn.edu.nju.candleflame.tickets.service.TicketService;
import cn.edu.nju.candleflame.tickets.service.UserRankService;
import cn.edu.nju.candleflame.tickets.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TicketServiceImpl implements TicketService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ShowInfoRepository showInfoRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    DistributionTicketRepository destributionTicketRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    BankService bankService;
    @Autowired
    UserRankService userRankService;
    @Autowired
    OffTicketRepository offTicketRepository;

    @Override
    public RoomModel getRoomModel(int showid) {
        ShowInfoEntity showinfo = showInfoRepository.findByShowid(showid);
        RoomEntity roomEntity=roomRepository.findByTheateridAndRoomid(showinfo.getTheater().getTheaterid(),showinfo.getRoomid());

        String roominfo=roomEntity.getRoominfo();
        RoomModel roomModel=createRoomModel(roominfo);
        roomModel.setCol(roomEntity.getCol());
        roomModel.setRow(roomEntity.getRow());

        List<TicketEntity> ticketEntities=ticketRepository.findAllByShowAndIscancel(showinfo,false);
        List<OffTicketEntity> offTicketEntities=offTicketRepository.findAllByShow(showinfo);
        getAllTicket(roomModel.getCol(),roomModel.getRooms(),ticketEntities,offTicketEntities);

        return roomModel;
    }

    @Override
    public String orderTicket(int showid, String position, String userid, int number, double price,boolean ispayed) {


        UserInfoEntity user=userInfoRepository.findByEmail(userid);
        ShowInfoEntity show=showInfoRepository.findByShowid(showid);
        if (ticketRepository.findAllByShowAndUserAndIscancel(show,user,false).size()>0){
            return "您已进行过购买";
        }
        RoomEntity roomEntity=roomRepository.findByTheateridAndRoomid(show.getTheater().getTheaterid(),show.getRoomid());
        if (!checkSeat(roomEntity.getCol(),position,showid)){
            return "该位置已被购买";
        }


        TicketEntity ticketEntity=new TicketEntity();
        ticketEntity.setShow(show);
        ticketEntity.setNumber(number);
        ticketEntity.setPosition(position);
        ticketEntity.setPrice(userRankService.getPrice(price,user.getTotal()));
        ticketEntity.setTicketid(MD5Util.encode(position+userid+showid+(new Date().toString())));
        ticketEntity.setUser(user);
        ticketEntity.setIspayed(ispayed);
        ticketRepository.save(ticketEntity);
        return "购买成功";
    }

    @Override
    public String destribute(String email, int number1, int number2, int number3, int showid) {

        ShowInfoEntity showInfoEntity=showInfoRepository.findByShowid(showid);
        UserInfoEntity userInfoEntity=userInfoRepository.findByEmail(email);

        List<DistributionTicketEntity> destributionTicketEntities=destributionTicketRepository.findByShowAndUserAndIscancel(showInfoEntity,userInfoEntity,false);
        if (destributionTicketEntities.size()>0){
            return "不能多次购买";
        }
        RoomModel roomModel=getRoomModel(showid);
        int[] roomRemain= getRoomRemain(roomModel);
        if (!checkEnough(roomRemain,number1,number2,number3)){
            return "当前剩余三等座位"+roomRemain[0]+"个，二等座位"+roomRemain[1]+"个，一等座位"+roomRemain[2]+"个";
        }

        DistributionTicketEntity destributionTicketEntity=new DistributionTicketEntity(MD5Util.encode(email+showid+(new Date().toString())));
        destributionTicketEntity.setIspayed(false);
        destributionTicketEntity.setIssuccess(false);
        destributionTicketEntity.setIscancel(false);
        //TODO change
        double price=showInfoEntity.getPrice1()*number1+showInfoEntity.getPrice2()*number2+showInfoEntity.getPrice3()*number3;
        destributionTicketEntity.setPrice(userRankService.getPrice(price,userInfoEntity.getTotal()));
        destributionTicketEntity.setSeat1(number1);
        destributionTicketEntity.setSeat2(number2);
        destributionTicketEntity.setSeat3(number3);
        destributionTicketEntity.setUser(userInfoEntity);
        destributionTicketEntity.setShow(showInfoEntity);
        destributionTicketRepository.save(destributionTicketEntity);

        return "ok";
    }


    private List<TicketModel> changeList(List<TicketEntity> list, String type){
        List<TicketModel> result=new ArrayList<>();

        long time = 30*60*1000;//30分钟
        final long endTime =new Date().getTime() - time;
        Timestamp endTimeStamp = new Timestamp(endTime);
        for (int i=0;i<list.size();i++){
            TicketEntity ticketEntity = list.get(i);
            TicketModel ticketModel=new TicketModel();
            ticketModel.setUserEmail(ticketEntity.getUser().getEmail());
            ticketModel.setTicketid(ticketEntity.getTicketid());
            ticketModel.setShowTitle(ticketEntity.getShow().getTitle());
            ticketModel.setTheaterName(ticketEntity.getShow().getTheater().getName());
            ticketModel.setShowtime(ticketEntity.getShow().getTime());
            ticketModel.setPrice(ticketEntity.getPrice());

            String[] seatInfo=ticketEntity.getPosition().split(";");
            StringBuilder stringBuilder=new StringBuilder();
            for (int j=0;j<seatInfo.length;j++){
                String[] temp=seatInfo[j].split(",");
                stringBuilder.append(temp[0]+"排"+temp[1]+"座");
            }
            ticketModel.setPosition(stringBuilder.toString());

            if (type.equals("")){
                if (ticketEntity.isIscancel()){
                    ticketModel.setStatus("已取消");
                }else {
                    if (ticketEntity.isIspayed()){
                        if (ticketEntity.isIssuccess()){
                            ticketModel.setStatus("已完成");
                        }else{
                            ticketModel.setStatus("未开始");
                        }
                    }else{
                        ticketModel.setStatus("未支付");
                    }

                }
            }else{
                ticketModel.setStatus(type);
            }
            result.add(ticketModel);
        }

        return result;

    }

    @Override
    public List<TicketModel> getAllTicket(String email) {

        UserInfoEntity user=userInfoRepository.findByEmail(email);
        List<TicketEntity> tickets = ticketRepository.findAllByUserOrderByCreatetimeDesc(user);

        return changeList(tickets, "");
    }

    @Override
    public List<TicketModel> getFutureTicket(String email) {
        UserInfoEntity user=userInfoRepository.findByEmail(email);
        List<TicketEntity> tickets = ticketRepository.findAllByUserAndIspayedAndIssuccessAndIscancelOrderByCreatetimeDesc(user,true,false,false);

        return changeList(tickets,"未开始");
    }

    @Override
    public List<TicketModel> getUnpayTicket(String email) {
        UserInfoEntity user=userInfoRepository.findByEmail(email);
        List<TicketEntity> tickets = ticketRepository.findAllByUserAndIscancelAndIspayedOrderByCreatetimeDesc(user,false,false);
        return changeList(tickets,"未支付");
    }

    @Override
    public List<TicketModel> getCompleteTicket(String email) {
        UserInfoEntity user=userInfoRepository.findByEmail(email);
        List<TicketEntity> tickets = ticketRepository.findAllByUserAndIspayedAndIssuccessAndIscancelOrderByCreatetimeDesc(user,true,true,false);

        return changeList(tickets,"已完成");
    }


    @Override
    public List<TicketModel> getCancelTicket(String email) {
        UserInfoEntity user=userInfoRepository.findByEmail(email);
        List<TicketEntity> tickets = ticketRepository.findAllByUserAndIscancelOrderByCreatetimeDesc(user,true);
        return changeList(tickets,"已取消");
    }

    @Override
    public String cancelTicket(String email, String ticketid) {
        UserInfoEntity user=userInfoRepository.findByEmail(email);
        TicketEntity ticketEntity = ticketRepository.findByUserAndTicketid(user,ticketid);
        ticketEntity.setIscancel(true);


        if (ticketEntity.isIspayed()){
            double money=getRealRefundMoney(ticketEntity.getPrice(),ticketEntity.getShow().getTime());
            boolean isRefund = bankService.refund(ticketEntity.getShow().getTheater().getTheaterid(), email,money );
            if (isRefund){
                return "退票成功";
            }else{
                return "退票失败，请重试";
            }
        }
        ticketRepository.save(ticketEntity);

        return "退票成功";
    }

    /**
     * 退票时间的比例
     *
     * 一星期内退70%
     * 否则退90%
     *
     * @param price
     * @param time
     * @return
     */
    private double getRealRefundMoney(double price, Timestamp time) {
        long currentTime=System.currentTimeMillis();

        long length=7*24*60*60*1000;

        if (currentTime+length>time.getTime()){
            return 0.7*price;
        }else{
            return 0.9*price;
        }
    }

    @Override
    public TicketEntity findTicket(String email, String ticketid) {
        UserInfoEntity user=userInfoRepository.findByEmail(email);
        TicketEntity ticketEntity=ticketRepository.findByUserAndTicketid(user,ticketid);
        return ticketEntity;
    }

    @Override
    public String userPay(boolean useIntegral, String email, String ticketid, String pass) {
        LoginEntity loginEntity = loginRepository.findByEmailAndPass(email, MD5Util.encode(pass));
        if (loginEntity==null||!loginEntity.isIsok()){
            return "密码有误";
        }else{
            UserInfoEntity userInfoEntity=userInfoRepository.findByEmail(email);
            TicketEntity ticketEntity=ticketRepository.findByUserAndTicketid(userInfoEntity,ticketid);
            int integral=userInfoEntity.getScore();
            int count=0;
            if (useIntegral){
                count= Math.min(integral/1000,(int) (ticketEntity.getPrice()/10));
                integral=integral-count*1000;
                userInfoEntity.setScore(integral);
                userInfoRepository.save(userInfoEntity);
            }

            double currentPrice=ticketEntity.getPrice()+TicketService.FEES-count*10;
            BigDecimal b   =   new   BigDecimal(currentPrice);
            currentPrice =b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            if (currentPrice>userInfoEntity.getMoney()){
                return "余额不足";
            }else{
                boolean result = bankService.buyTicket(userInfoEntity.getEmail(), ticketEntity.getShow().getTheater().getTheaterid(), currentPrice);
                if (result){
                    ticketEntity.setIspayed(true);
                    ticketRepository.save(ticketEntity);
                    return "购票成功";
                }else{
                    return "购票失败";
                }

            }
        }
    }

    @Override
    public String addOffTicket(int showid, String position) {

        ShowInfoEntity show=showInfoRepository.findByShowid(showid);
        RoomEntity roomEntity=roomRepository.findByTheateridAndRoomid(show.getTheater().getTheaterid(),show.getRoomid());
        if (checkSeat(roomEntity.getCol(),position,showid)){
            OffTicketEntity offTicketEntity=new OffTicketEntity(MD5Util.encode(show.getTheater().getTheaterid()+new Date()+position),position,show);
            offTicketRepository.save(offTicketEntity);
            return "购票成功";
        }
        return "座位已经被占用";
    }

    @Override
    public CheckInformationModel checkTicket(String ticketid) throws NullPointerException{
        CheckInformationModel checkInformationModel=new CheckInformationModel();
        TicketEntity ticketEntity = ticketRepository.findByTicketid(ticketid);
        if (ticketEntity==null){
            checkInformationModel.setCurrent(false);
            return checkInformationModel;
        }
        if (ticketEntity.isIssuccess()){
            checkInformationModel.setCurrent(true);
            checkInformationModel.setUsed(true);
            return checkInformationModel;
        }else{
            ticketEntity.setIssuccess(true);
            UserInfoEntity user = ticketEntity.getUser();
            user.setScore(user.getScore()+(int)ticketEntity.getPrice());
            user.setTotal(user.getTotal()+(int)ticketEntity.getPrice());
            userInfoRepository.save(user);
            ticketRepository.save(ticketEntity);

            checkInformationModel.setUserid(ticketEntity.getUser().getEmail());
            checkInformationModel.setUsed(false);
            checkInformationModel.setCurrent(true);
            checkInformationModel.setSeat(ticketEntity.getPosition());
            checkInformationModel.setTheaterid(ticketEntity.getTicketid());
            checkInformationModel.setRoomid(ticketEntity.getShow().getRoomid());

            return checkInformationModel;
        }

    }

    private boolean checkEnough(int[] roomRemain, int number1, int number2, int number3) {
        return number1<=roomRemain[0]&&number2<=roomRemain[1]&&number3<=roomRemain[2];
    }

    private int[] getRoomRemain(RoomModel roomModel) {
        int number1=0;
        int number2=0;
        int number3=0;
        for (SeatModel seatModel:roomModel.getRooms()){
            if (seatModel.isEmpty()){
                switch (seatModel.getSeatType()){
                    case 1:number1++;break;
                    case 2:number2++;break;
                    case 3:number3++;break;
                }
            }
        }
        return new int[]{number1,number2,number3};
    }

    private boolean checkSeat(int col, String position, int showid) {
        List<SeatModel> rooms = getRoomModel(showid).getRooms();
        String[] temp=position.split(";");
        for (int j=0;j<temp.length;j++){
            int index=getIndex(temp[j],col);
            if (!rooms.get(index).isEmpty()){
                return false;
            }
        }
        return true;
    }

    private void getAllTicket(int col, List<SeatModel> roomModel, List<TicketEntity> ticketEntities,List<OffTicketEntity> offTicketEntities) {
        for (TicketEntity ticketEntity : ticketEntities) {
            String[] temp = ticketEntity.getPosition().split(";");
            for (int j = 0; j < temp.length; j++) {
                int index = getIndex(temp[j], col);
                roomModel.get(index).setEmpty(false);
            }
        }
        for (OffTicketEntity offTicketEntity:offTicketEntities){
            String[] temp=offTicketEntity.getPosition().split(";");
            for (int j=0;j<temp.length;j++){
                int index=getIndex(temp[j],col);
                roomModel.get(index).setEmpty(false);
            }
        }
    }

    private int getIndex(String seatInfo,int colLines){
        String[] strings=seatInfo.split(",");
        int row= Integer.parseInt(strings[0])-1;
        int col= Integer.parseInt(strings[1])-1;
        return row*colLines+col;
    }

    private RoomModel createRoomModel(String roominfo) {
        List<SeatModel> seatModelList=new ArrayList<>();
        String[] strings=roominfo.split(";");
        for (int i=0;i<strings.length;i++){
            String temp=strings[i];
            for (int j=0;j<temp.length();j++){
                SeatModel seatModel=new SeatModel();
                seatModel.setSeatType(Integer.parseInt(temp.charAt(j)+""));
                seatModelList.add(seatModel);
            }
        }

        RoomModel roomModel=new RoomModel();
        roomModel.setRooms(seatModelList);

        return roomModel;
    }



}
