package MineSweeper;

import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args){
        Minesweeper m=new Minesweeper();
        m.start();
    }
    private Field f;
    private void start(){
        this.printInstructions();
        this.selectDifficulty();
        this.playGame();
    }
    private void playGame(){
        Scanner s=new Scanner(System.in);
        System.out.println("This is the board right now");
        System.out.println(f);
        while(!f.isWin()){
            System.out.println("Want to mark a cell?");
            System.out.println("Just type in y for yes or n for no");
            String dec=s.nextLine();
            if(dec.trim().equalsIgnoreCase("y")){
                if(this.placeMark()) {

                }
                while(!this.placeMark()){
                    System.out.println("Can't mark a revealed cell");
                    System.out.println("Choose another cell");
                }
            }else {
                System.out.println("Sure, choose a cell to reveal.");
            }
            boolean canProceed=this.makeMove();
            if(!canProceed){
                if(f.isLoss()){
                    break;
                }else {
                    while(!canProceed){
                        canProceed=this.makeMove();
                    }
                }
            }
            System.out.println(f);
        }
        if(f.isWin()){
            System.out.println("Congratulations, you won!");

        }else if(f.isLoss()){
            System.out.println("I am sorry, you lost :(");
        }
        System.out.println(f);
    }
    private boolean placeMark(){
        Scanner s=new Scanner(System.in);
        System.out.println("Choose a row");
        System.out.print("Row: ");
        int row=s.nextInt();
        if(row<0 || row>7){
            System.out.println("Type in the number from 0 to 7");
            row=s.nextInt();
        }
        System.out.println("Choose a column");
        System.out.print("Column: ");
        int col=s.nextInt();

        if(col<0 || col>7){
            System.out.println("Type in the number from 0 to 7");
            col=s.nextInt();
        }
        return f.mark(row,col);
    }
    private boolean makeMove(){
        Scanner s=new Scanner(System.in);
        System.out.println("Choose a row");
        System.out.print("Row: ");
        int row=s.nextInt();
        if(row<0 || row>7){
            System.out.println("Type in the number from 0 to 7");
            row=s.nextInt();
        }
        System.out.println("Choose a column");
        System.out.print("Column: ");
        int col=s.nextInt();

        if(col<0 || col>7){
            System.out.println("Type in the number from 0 to 7");
            col=s.nextInt();
        }
        return f.reveal(row,col);
    }
    private void selectDifficulty(){
        Scanner s=new Scanner(System.in);
        System.out.println("Now, I want you to select a difficulty.");
        System.out.println("There are 3 difficulties:");
        System.out.println("1) Easy, board size- 8x8, amount of mines-10");
        System.out.println("2) Intermediate, board size- 16x16, amount of mines- 40");
        System.out.println("3) Extreme, board size- 16x30, amount of mines- 99");
        System.out.println("Type in the number of the difficulty");
        int diff=s.nextInt();
        while(diff<1 || diff>3){
            System.out.println("Please, type in the number from 1 to 3");
            diff=s.nextInt();
        }
        this.f=new Field(diff-1);
    }
    private void printInstructions(){
        System.out.println("Welcome to minesweeper!");
        System.out.println("For those who don't know the rules of the game, I will briefly explain them.");
        System.out.println("First of all, minesweeper is a game where you are given an empty board, the size of the boards depends on the difficulty.");
        System.out.println("There are 3 difficulties:");
        System.out.println("1) Easy, board size- 8x8, amount of mines-10");
        System.out.println("2) Intermediate, board size- 16x16, amount of mines- 40");
        System.out.println("3) Extreme, board size- 16x30, amount of mines- 99");
        System.out.println("All you have to do, is to select a cell to reveal, if you are lucky, it will be a cell without a mine.");
        System.out.println("If the cell is a mine cell, you lose, cause you explode. Otherwise, you are good to go");
        System.out.println("For the sake of difficulty, since we can get lost on huge boards, you can mark cells, as if there is a mine there, or maybe you are wrong.");
        System.out.println("Therefore, I wish you good luck ;)");
    }
}
