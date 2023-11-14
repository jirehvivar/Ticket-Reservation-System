import java.util.*;
import java.io.*;

public class Main {
    

    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        boolean inputFileExists = false;
        File inputFile = null;
        Node<Seat> head = null;
        Auditorium<Seat> auditorium = new Auditorium<>(head);
       try{
        while(!inputFileExists){
            System.out.print("Enter file name: ");
            String fileName = scnr.next();
            
            inputFile = new File(fileName);
            File inFile = new File(fileName);
            Scanner scnrFile = new Scanner(inFile);
    
            if(inputFile.exists()){
                inputFileExists = true;
                auditorium.readsAuditorium(fileName, scnrFile);

            }else {
                System.out.println("File doesnt exist");
            }
                
        } 
        }catch(InputMismatchException e){

        }
        boolean seatsAvailable = false;
        int mainMenuChoice =0;
        int totalSold = 0;
        System.out.println("1. Reserve Seats");
        System.out.println("2. Exit");
       
        // validate user input for Menu Choice
        do {
            try {///this works!! for validation!! do not touch!!
                mainMenuChoice = scnr.nextInt();
                if (mainMenuChoice != 1 && mainMenuChoice != 2) {
                    System.out.println("Invalid. Retry input: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scnr.next();
                continue;
            }
        } while (mainMenuChoice != 1 && mainMenuChoice != 2);


        //run the options 
          while(mainMenuChoice ==1){
            totalSold = 0;

            if (mainMenuChoice == 1) {
               //output the seats with hashs
                auditorium.convertSeats();
                int column = 0;
                char seat = 'A';
                System.out.println("Row: ");
                int row = -1; // Initialize with an invalid value to enter the loop
                while (row < 0 || row >= auditorium.currRow) {
                    try {
                        row = scnr.nextInt() - 1; // Adjust for zero-based index if needed
                        if (row < 0 || row >= auditorium.currRow) {
                            // Only print error if the input is out of range
                            System.out.print("Retry row number: ");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a numerical value for the row.");
                        scnr.next(); // Clear the scanner buffer to avoid an infinite loop
                        System.out.print("Retry row number: ");
                    }
                }
                //System.out.println((row+1));

            System.out.println("Seat: ");
            String input = scnr.next();
            //System.out.println(input);
            //allow lowercase inputs
            input = input.toUpperCase();
            seat = input.charAt(0);
            column = seat - 'A';
            //validate column input 
            while (column >= auditorium.currColumn || column < 0) {
                try{
                System.out.print("Retry column letter (UPPERCASE): ");
                input = scnr.next();
                //System.out.println(input);
                input = input.toUpperCase();
                seat = input.charAt(0);
                column = seat - 'A';
                } catch (InputMismatchException e){
                    System.out.println("Invalid input.");
                    scnr.next(); 
                }
            }

            //input validation for ticket amount
            int adults = -1;
            while (adults < 0) {
                try {
                    System.out.println("adults: ");
                    adults = scnr.nextInt();

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    scnr.next(); 
                }
            }
            //System.out.println( adults);
            
            int children = -1;
            while (children < 0) {
                try {
                    System.out.println("children: ");
                    children = scnr.nextInt();

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. ");
                    scnr.next(); 
                }
            }
            //System.out.println(children);
            
            int seniors = -1;
            while (seniors < 0) {
                try {
                    System.out.print("seniors: ");
                    seniors = scnr.nextInt();

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    scnr.next(); 
                }
            }
            //System.out.println(seniors);
            
            totalSold = adults + children + seniors;
            //Check if seats are available
            seatsAvailable = auditorium.checkAvailability(totalSold, (row+1), column);
            
            if(seatsAvailable){
                System.out.println("Seats are available ");
                column = seat - 'A';
                auditorium.reserveSeats( (row+1), (column+1), adults, children, seniors);
                auditorium.convertSeats();
            }else if(!seatsAvailable){
                //run bestAvailable here;
                System.out.println("No seats available");
                column = seat - 'A';
                boolean foundBestAvailable = auditorium.bestAvailable(totalSold);
                
                if (foundBestAvailable) {
                    //if the size of total seats does exist in the row
                    char startColumnLetter = (char) ('A' + auditorium.bestCol -1);
                    char endColumnLetter = (char) (startColumnLetter + totalSold -1);
                    //print next best seats
                    if(startColumnLetter == endColumnLetter){
                        System.out.println("" + (auditorium.bestRow ) + startColumnLetter ); 
                    }else {
                        System.out.println("" + (auditorium.bestRow ) + startColumnLetter + "-" + (auditorium.bestRow ) + endColumnLetter);
                    }
                    System.out.println("Would you like to reserve(Y/N): ");
                    char reserveNewSeats = scnr.next().charAt(0);
                    reserveNewSeats = Character.toUpperCase(reserveNewSeats);
                    //System.out.println(reserveNewSeats);
                    //validate new reservation seats 
                    while(reserveNewSeats != 'Y' && reserveNewSeats != 'N'){
                        System.out.println("Invalid input. Would you like to reserve(Y/N): ");
                        reserveNewSeats = scnr.next().charAt(0);
                        reserveNewSeats = Character.toUpperCase(reserveNewSeats);
                    }
                    if (reserveNewSeats == 'Y') {
                        auditorium.reserveSeats( (auditorium.bestRow), (auditorium.bestCol ), adults, children, seniors);
                        auditorium.convertSeats();
                    } else if (reserveNewSeats == 'N') {
                        //simply return the normal main 
                    }
                    
                } else {
                    //if there is no possible seating in that row for user
                    System.out.println("No seats available");
                }
            }
               
               
            }
            String inFile = "A1.txt";
            //write the nodes into the file
            auditorium.printToFile(inFile);
            
            //prompt user another reserve seats option
            System.out.println("1. Reserve Seats");
            System.out.println("2. Exit");
            mainMenuChoice = scnr.nextInt();
        } 
        auditorium.displayReport();
       scnr.close();
    }

}
