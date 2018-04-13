package cn.edu.nju.candleflame.tickets.service;

public interface UserRankService {
    public int getRank(int score);
    public double getPrice(double price,int score);
}
