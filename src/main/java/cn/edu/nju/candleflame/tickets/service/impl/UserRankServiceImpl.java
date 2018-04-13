package cn.edu.nju.candleflame.tickets.service.impl;

import cn.edu.nju.candleflame.tickets.service.UserRankService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserRankServiceImpl implements UserRankService {
    @Override
    public int getRank(int score) {
        if (score>=0&&score<100){
            return 1;
        }else if (score<500){
            return 2;
        }else if (score<1500){
            return 3;
        }else if (score<5000){
            return 4;
        }else{
            return 5;
        }
    }

    @Override
    public double getPrice(double price,int score) {
        int rank=getRank(score);
        BigDecimal b   =   new   BigDecimal(price*(1-0.02*(rank-1)));
        double newPrice=b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return newPrice;
    }
}
