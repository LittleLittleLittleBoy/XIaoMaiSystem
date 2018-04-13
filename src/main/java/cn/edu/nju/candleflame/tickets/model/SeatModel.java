package cn.edu.nju.candleflame.tickets.model;

public class SeatModel {
    private int seatType;
    private boolean empty=true;

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
