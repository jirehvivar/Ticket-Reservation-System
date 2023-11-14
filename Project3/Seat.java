public class Seat {
    private int row;
    private char column;
    private char currSeat;
    private char ticketType;

    public Seat() {
        // Default constructor
    }

    public Seat(char currSeat, int row, char column, char ticketType) {
        // Overloaded constructor
        this.row = row;
        this.currSeat = currSeat;
        this.column = column;
        this.ticketType = ticketType;
    }

    // Mutators
    public void setRow(int row) {
        this.row = row;
    }

    public void setSeat(char currSeat) {
        this.currSeat = currSeat;
    }

    public void setColumn(char column) {
        this.column = column;
    }
    public void setTicketType(char ticketType){
        this.ticketType = ticketType;
    }

    // Accessors
    public int getRow() {
        return row;
    }

    public char getSeat() {
        return currSeat;
    }

    public char getColumn() {
        return column;
    }

    public char getTicketType(){
        return ticketType;
    }

    @Override
    public String toString(){
        return currSeat+ "";
    }
}