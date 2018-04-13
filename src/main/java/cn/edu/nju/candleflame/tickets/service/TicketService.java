package cn.edu.nju.candleflame.tickets.service;

import cn.edu.nju.candleflame.tickets.entity.TicketEntity;
import cn.edu.nju.candleflame.tickets.model.CheckInformationModel;
import cn.edu.nju.candleflame.tickets.model.RoomModel;
import cn.edu.nju.candleflame.tickets.model.TicketModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TicketService {

    final double FEES=5;

    RoomModel getRoomModel(int showid);

    String orderTicket(int showid, String position, String userid, int number, double price,boolean ispayed);

    String destribute(String email, int number1, int number2, int number3, int showid);

    List<TicketModel> getAllTicket(String email);

    List<TicketModel> getFutureTicket(String email);

    List<TicketModel> getUnpayTicket(String email);

    List<TicketModel> getCompleteTicket(String email);


    List<TicketModel> getCancelTicket(String email);

    @Transactional
    String cancelTicket(String email, String ticketid);

    TicketEntity findTicket(String email, String ticketid);

    String userPay(boolean useIntegral, String email, String ticketid, String pass);

    String addOffTicket(int showid, String position);

    CheckInformationModel checkTicket(String ticketid) throws NullPointerException;
}
