package cn.edu.nju.candleflame.tickets.task;

import cn.edu.nju.candleflame.tickets.entity.TicketEntity;
import cn.edu.nju.candleflame.tickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class TicketCancelTask {

    @Autowired
    TicketRepository ticketRepository;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        long currentTime = System.currentTimeMillis();
        currentTime -=2*60*1000;
        Timestamp timestamp=new Timestamp(currentTime);
        List<TicketEntity> tickets = ticketRepository.findAllByIspayedAndIscancelAndCreatetimeBefore(false,false,timestamp);

        if (tickets.size()!=0){
            for(TicketEntity ticketEntity:tickets){
                ticketEntity.setIscancel(true);
                ticketRepository.save(ticketEntity);
                System.out.println(ticketEntity.getTicketid()+" is cancel");
            }
        }
    }
}
