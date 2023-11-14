//Jireh Vivar
//jxv220001

import java.io.*;
import java.util.*;

class Auditorium<T> {
    private Node<Seat> head; // Acts as head pointer of linked list
    public int currColumn = 0;
    public int currRow = 0;
    public int bestRow = -1;
    public int bestCol = -1;
    public int totalSeats = 0;
    public int totalTickets = 0;
    private double bestDistance = Double.MAX_VALUE;

    public Auditorium(Node<Seat> head) {
        this.head = head;
    }

    public void readsAuditorium(String fileName, Scanner scnrFile){
        
        char currChar;
        Node<Seat> currPtr = head;
        Node<Seat> currRowNode = head;
        //read lines of file
        
    while (scnrFile.hasNextLine()) {
        String line = scnrFile.nextLine();//each Line is a string
        //for each column in a row
        for(currColumn = 0; currColumn < line.length(); currColumn++){
            //if lines not empty then read char and input into node and Seat
            if (!line.equals("")) {
                currChar = line.charAt(currColumn);
                Seat seat = new Seat(currChar, currRow, (char) (currColumn + 'A'), currChar);
                
                if (currColumn == 0) {
                    if (currRow == 0) {//0 index 0
                        head = new Node<Seat>(seat, null, null, null);
                        currPtr = head;//placed here to not touch head aka temp
                        currRowNode = head;
                        continue;
                    } else { // make vertically
                        Node<Seat> temp = new Node<Seat>(seat, null, null, currPtr);
                        currRowNode.setDown(temp);
                        currPtr = currRowNode.getDown(); 
                        
                        currRowNode = currPtr;
                        continue;
                    }
                } else  {//makes the horizontally 
                    currPtr.setNext(new Node<Seat>(seat, null, null, currPtr));
                    
                }
                //System.out.print(currPtr.toString()); //this  prints the actual seat letter
            
                
                currPtr = currPtr.getNext();
                
            } else {
                break;
            }

        }//end of forLoop
    //System.out.println();
    currRow++;
    
    } //end of whileLoop
    scnrFile.close();      


    }

    public void convertSeats(){
        Node<Seat> current = head;
        totalSeats = 0;
        totalTickets = 0;
        if (current == null) {//if the head doesnt have anything say this 
            System.out.println("Auditorium Object Empty");
            return;
        }
        
        int row_number = 0;
        //print out the letters for each column
        System.out.print(" ");
        for (int i = 65; i < 65 + currColumn; i++) {
            System.out.print((char) i);
        }
        System.out.println(); 
        
        while (current != null) {
            //print out the rows for each line
            row_number++;
            System.out.print(row_number ); 
        
            Node<Seat> current_seat = current; 
            while (current_seat != null) {
                //print out the seats and get the original Seat letter
                Seat s = current_seat.getPayload();
                if (s.getTicketType() != '.') {
                    //if it doesnt have a letter then place a hash over it 
                    System.out.print('#');
                    totalTickets++;
                    totalSeats++;
                } else {
                    System.out.print(s.getTicketType());
                    totalSeats++;
                }
                current_seat = current_seat.getNext();
            }
        
            System.out.println(); 
            current = current.getDown();
        }
    }  
 
    public boolean checkAvailability(int ticketAmount, int row, int column) {
        // Check if the auditorium is empty
        if (head == null) {
            return false;
        }
        Node<Seat> current = head;
        
        int i = 1;
        while (i < row) {
            current = current.getDown(); 
            i++;
        }

        i = 0; 
        while (i < column) {
            current = current.getNext(); 
            i++;
        }
    
        i = 1;
        while (i <= ticketAmount) {
            if (current.getPayload().getTicketType() != '.') {
                return false;
            } else {
                current = current.getNext();
                i++;
            }
        }
    
        return true;
    }

    public void reserveSeats(int row, int column, int adult, int children, int seniors) {
        Node<Seat> current = head;

        int i = 1;
        while (i < row) {
            current = current.getDown(); 
            i++;
        }
    
        i = 1;
        while (i < column) {
            current = current.getNext();
            i++;
        }
        i = 1;
        while (i <= adult) {//adults
            current.getPayload().setTicketType('A');
            current = current.getNext();
            i++;
        }
        i = 1;
        while (i <= children) {//children
            current.getPayload().setTicketType('C');
            current = current.getNext();
            i++;
        }
        i = 1;
        while (i <= seniors) {//seniors
            current.getPayload().setTicketType('S');
            current = current.getNext();
            i++;
        }
    }
    
    public void displayReport() {
        Node<Seat> current = head;
        Node<Seat> current_row = head; // This pointer will always point to the first Node of the current row
    
        // Initialize the following variables for counting sold tickets
        int sold_adult = 0;
        int sold_child = 0;
        int sold_senior = 0;
    
        while (current_row != null) {
            while (current != null) {
                Seat s = current.getPayload();
    
                // Check the ticket type and update the counters
                if (s.getTicketType() == 'A') {
                    sold_adult++;
                } else if (s.getTicketType() == 'C') {
                    sold_child++;
                } else if (s.getTicketType() == 'S') {
                    sold_senior++;
                }
    
                current = current.getNext();
            }
    
            // Move to the next row
            current_row = current_row.getDown();
            current = current_row;
        }
    
        // Print the number of sold tickets for each category
        System.out.println("Total Seats:     " + totalSeats);
        System.out.println("Total Tickets:   " + totalTickets);
        System.out.println("Adult Tickets:   " + sold_adult);
        System.out.println("Child Tickets:   " + sold_child);
        System.out.println("Senior Tickets:  " + sold_senior);
    
        // Calculate and print the total sales price
        double total_sales = sold_adult * 10 + sold_child * 5 + sold_senior * 7.5;
        String forTotalSales = String.format("%.2f", total_sales);
        System.out.println("Total Sales:     $" + forTotalSales);
    }
 
    public void printToFile(String inFile) {
        
        try (FileWriter outWrite = new FileWriter(inFile)) {
            Node<Seat> current = head;
            if (current == null) {
                System.out.println("Auditorium Object Empty");
                return;
            }

            
            while (current != null) {
                

                Node<Seat> current_seat = current;
                while (current_seat != null) {
                    Seat s = current_seat.getPayload();
                    outWrite.write(s.getTicketType());

                    current_seat = current_seat.getNext();
                }

                outWrite.write("\n");
                current = current.getDown();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public boolean bestAvailable(int totalSeats) {
        Node<Seat> currentRow = head;
        int currentRowNumber = 1;//save the row number
        bestCol = -1;
        bestRow = -1;
        bestDistance = Double.MAX_VALUE; 
    
        while (currentRow != null) {
            Node<Seat> currentSeat = currentRow;
            int currentSeatNumber = 1;//save the seat column
    
            while (currentSeat != null) {
                if (currentSeat.getPayload().getTicketType() == '.') {//check the seat if its available
                    Node<Seat> startSeat = currentSeat; 
                    Node<Seat> endSeat = checkAvailability(currentSeat, totalSeats);//check the seats of the totalSeats
                    if (endSeat != null && endSeat.getPayload().getTicketType() == '.') { 
                        //calculate the distance 
                        double distance = calculateDistance(currentRowNumber, currentSeatNumber, totalSeats);
                        //update based on distance
                        if (updateBestSeat(distance, currentRowNumber, currentSeatNumber)) {
                            bestCol = currentSeatNumber; 
                            bestRow = currentRowNumber; 
                        }
                    
                        if (!(bestRow == currentRowNumber && bestCol == currentSeatNumber)) {
                            while (currentSeat != endSeat) {
                                currentSeat = currentSeat.getNext();
                                currentSeatNumber++;
                            }
                        }
                    }
    
                }

                currentSeatNumber++;
                currentSeat = currentSeat.getNext();
            }
            currentRowNumber++;
            currentRow = currentRow.getDown();
        }
    
        return bestRow != -1 && bestCol != -1;
    }

    private Node<Seat> checkAvailability(Node<Seat> startSeat, int seatsRequired) {
        int availableSeats = 0;
        Node<Seat> currentSeat = startSeat;
        Node<Seat> lastAvailableSeat = null;
    
        while (currentSeat != null && availableSeats < seatsRequired) {
            if (currentSeat.getPayload().getTicketType() == '.') {
                availableSeats++;
                lastAvailableSeat = currentSeat; 
            } else {
                return null;
            }//if it equals the seats required simply exit the loop<--- this fixed error of endSeats being one seat ahead of totalSeats
            if (availableSeats == seatsRequired) {
                return lastAvailableSeat;
            }
            currentSeat = currentSeat.getNext(); 
        }
        return null; 
    }
      
    private double calculateDistance(int rowNumber, int seatNumber, int seatsRequired) {//this works !! dont touch 
        double centerX = (currColumn + 1) / 2.0;
        double centerY = (currRow + 1) / 2.0;
        double seatCenterX = seatNumber + (seatsRequired - 1) / 2.0;
        // Assuming  calculation is the distance from the center of the theater to the center of the group of seats
        return Math.sqrt(Math.pow(seatCenterX - centerX, 2) + Math.pow(rowNumber - centerY, 2));
    }

    private boolean updateBestSeat(double distance, int rowNumber, int seatNumber) {
        //updates based on the distance being the smallest 
        if (distance < this.bestDistance) {
            this.bestDistance = distance;
            this.bestRow = rowNumber;
            this.bestCol = seatNumber;
            return true;
        } else if (distance == this.bestDistance) {
            if (Math.abs(rowNumber - (currRow + 1) / 2.0) < Math.abs(this.bestRow - (currRow + 1) / 2.0)) {
                this.bestRow = rowNumber;
                this.bestCol = seatNumber;
                return true;
            } else if (rowNumber == this.bestRow) {
                if (seatNumber < this.bestCol) {
                    this.bestCol = seatNumber;
                    return true;
                }
            }
        }
        return false;
    }
    
}