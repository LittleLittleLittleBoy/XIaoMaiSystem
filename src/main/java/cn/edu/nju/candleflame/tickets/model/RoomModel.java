package cn.edu.nju.candleflame.tickets.model;

import java.util.List;

public class RoomModel {
    private int row;
    private int col;
    private List<SeatModel> rooms;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public List<SeatModel> getRooms() {
        return rooms;
    }

    public void setRooms(List<SeatModel> rooms) {
        this.rooms = rooms;
    }
}
