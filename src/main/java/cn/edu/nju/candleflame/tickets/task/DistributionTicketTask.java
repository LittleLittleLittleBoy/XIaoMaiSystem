package cn.edu.nju.candleflame.tickets.task;

import cn.edu.nju.candleflame.tickets.entity.DistributionTicketEntity;
import cn.edu.nju.candleflame.tickets.model.RoomModel;
import cn.edu.nju.candleflame.tickets.model.SeatModel;
import cn.edu.nju.candleflame.tickets.repository.DistributionTicketRepository;
import cn.edu.nju.candleflame.tickets.service.MailService;
import cn.edu.nju.candleflame.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistributionTicketTask {

    @Autowired
    TicketService ticketService;
    @Autowired
    DistributionTicketRepository distributionTicketRepository;
    @Autowired
    MailService mailService;

    @Scheduled(fixedRate = 60000)
    public void distributeTicket(){
        List<String> ticketList=distributionTicketRepository.findTodoTicket();
        for (int i=0;i<ticketList.size();i++){
            DistributionTicketEntity distributionTicketEntity = distributionTicketRepository.findByTicketid(ticketList.get(i));
            RoomModel roomModel = ticketService.getRoomModel(distributionTicketEntity.getShow().getShowid());
            int[] roomRemain = getRoomRemain(roomModel);
            if (roomRemain[0]>=distributionTicketEntity.getSeat1()&&roomRemain[1]>=distributionTicketEntity.getSeat2()&&roomRemain[2]>=distributionTicketEntity.getSeat3()){
                StringBuilder stringBuilder=new StringBuilder();
                int seat1=distributionTicketEntity.getSeat1();
                int seat2=distributionTicketEntity.getSeat2();
                int seat3=distributionTicketEntity.getSeat3();
                List<SeatModel> rooms = roomModel.getRooms();
                int col=roomModel.getCol();
                for (int j=0;j<rooms.size();j++) {
                    SeatModel seatModel = rooms.get(j);
                    if (seatModel.isEmpty()) {
                        switch (seatModel.getSeatType()) {
                            case 1:
                                if (seat1 > 0) {
                                    seat1--;
                                    stringBuilder.append(((j/col)+1)+","+((j%col)+1)+";");
                                }
                                break;
                            case 2:
                                if (seat2 > 0) {
                                    seat2--;
                                    stringBuilder.append(((j/col)+1)+","+((j%col)+1)+";");
                                }
                                break;
                            case 3:
                                if (seat3 > 0) {
                                    seat3--;
                                    stringBuilder.append(((j/col)+1)+","+((j%col)+1)+";");
                                }
                                break;
                        }
                    }
                }
//                System.out.println(stringBuilder.toString());
                int number=distributionTicketEntity.getSeat1()+distributionTicketEntity.getSeat2()+distributionTicketEntity.getSeat3();
                ticketService.orderTicket(distributionTicketEntity.getShow().getShowid(),stringBuilder.toString(),distributionTicketEntity.getUser().getEmail(),number,distributionTicketEntity.getPrice(),false);

                distributionTicketRepository.delete(distributionTicketEntity);
                mailService.sendMail(distributionTicketEntity.getUser().getEmail(),"配票情况","已经为您配好票，请在30分钟内完成支付");
            }else{
                distributionTicketRepository.delete(distributionTicketEntity);
                mailService.sendMail(distributionTicketEntity.getUser().getEmail(),"配票情况","座位不足，请您重新买票");
            }

        }
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

}
