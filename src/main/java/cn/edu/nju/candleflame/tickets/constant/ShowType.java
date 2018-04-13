package cn.edu.nju.candleflame.tickets.constant;

import java.util.ArrayList;
import java.util.List;

public class ShowType {

    private static String[] showType=new String[]{
            "音乐会",
            "舞蹈",
            "话剧",
            "体育比赛"
    };

    public static String[] getShowType(){
        return ShowType.showType;
    }
}
