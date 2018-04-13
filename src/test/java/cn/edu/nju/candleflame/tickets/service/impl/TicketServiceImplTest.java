package cn.edu.nju.candleflame.tickets.service.impl;

import cn.edu.nju.candleflame.tickets.TicketsApplication;
import cn.edu.nju.candleflame.tickets.model.RoomModel;
import cn.edu.nju.candleflame.tickets.service.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class TicketServiceImplTest {
    @Autowired
    TicketService ticketService;
    @Test
    public void getRoomModel() throws Exception {
        RoomModel roomModel = ticketService.getRoomModel(5);
        for (int i=0;i<roomModel.getRooms().size();i++){
            if (roomModel.getRooms().get(i).getSeatType()!=0){
                System.out.println("enen");
            }
        }
    }

}