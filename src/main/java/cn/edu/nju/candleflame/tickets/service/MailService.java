package cn.edu.nju.candleflame.tickets.service;

public interface MailService {
    public boolean sendMail(String to,String subject,String content);
}
