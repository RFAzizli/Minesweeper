package MineSweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private String[][] mineField;
    private final String[][] mineFieldCopy;
    private boolean[][] revealed;
    private boolean inGame;

    public Field(int difficulty){
        if(difficulty<0 || difficulty>2){
            throw new IllegalArgumentException();
        }
        switch (difficulty) {
            case 1 -> {
                this.mineField = new String[16][16];
                this.revealed = new boolean[16][16];
                this.fillIntermediate();
            }
            case 2 -> {
                this.mineField = new String[16][30];
                this.revealed = new boolean[16][30];
                this.fillExtreme();
            }
            default -> {
                this.mineField = new String[8][8];
                this.revealed = new boolean[8][8];
                this.fillEasy();
            }
        }
        this.inGame=true;
        this.mineFieldCopy=new String[this.mineField.length][this.mineField[0].length];
        for(int i=0;i<this.mineField.length;i++){
            this.mineFieldCopy[i]= this.mineField[i].clone();
        }
        for (boolean[] booleans : this.revealed) {
            Arrays.fill(booleans, false);
        }
    }
    private int[][] getRandN(int n){
        Random r=new Random();
        int[][] randomPositions=new int[n][2];
        int index=0;
        int x=r.nextInt(this.mineField.length);
        int y=r.nextInt(this.mineField[x].length);
        int[] randPos={x,y};
        boolean contains=checkIfContainsArr(randomPositions,randPos);
        while(index<n){
            while(contains){
                x=r.nextInt(this.mineField.length);
                y=r.nextInt(this.mineField[x].length);
                randPos= new int[]{x, y};
                contains=checkIfContainsArr(randomPositions,randPos);
            }
            randomPositions[index]=randPos;
            index++;
            contains=checkIfContainsArr(randomPositions,randPos);
        }
        return randomPositions;
    }
    private static boolean checkIfContainsArr(int[][] parent, int[] child){
        for(int[] arr:parent){
            if(arr[0]==child[0]&& arr[1]==child[1]){
                return true;
            }
        }
        return false;
    }
    private void fillEasy(){
        int mines=10;
        setBoard(mines);
    }

    private void setBoard(int mines) {
        int[][] pos=getRandN(mines);
        for (String[] strings : this.mineField) {
            Arrays.fill(strings, "N");
        }
        for(int[] arr:pos){
            this.mineField[arr[0]][arr[1]]="m";
        }
        this.setValues();
    }

    private void fillIntermediate(){
        int mines=40;
        setBoard(mines);
    }
    private void fillExtreme(){
        int mines=99;
        setBoard(mines);
    }
    private void setValues(){
        for(int i=0;i<this.mineField.length;i++) {
            for (int j = 0; j < this.mineField[i].length; j++) {
                if(this.mineField[i][j].equals("N")){
                    int around=0;
                    try{
                        for(int k=i-1;k<i+2 && k<this.mineField.length;k++) {
                            for (int l = j - 1; l < j + 2 && l < this.mineField[i].length; l++) {
                                try {
                                    if (this.mineField[k][l].equals("m")) {
                                        around++;
                                    }
                                } catch (IndexOutOfBoundsException ignored) {
                                }
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException ignored){
                    }
                    this.mineField[i][j]=around+"";
                }else {
                    continue;
                }
            }
        }
    }

    public boolean mark(int row,int col){
        if(row<0 || row > this.mineField.length){
            throw new IllegalArgumentException();
        }else if(col<0 || col> this.mineField[row].length){
            throw new IllegalArgumentException();
        }
        if(this.revealed[row][col]){
            return false;
        }
        this.mineField[row][col]="!";
        return true;
    }
    public boolean isLoss(){
        return !this.inGame;
    }
    private boolean isMine(int row,int col){
        this.inGame = false;
        return this.mineFieldCopy[row][col].equals("m");
    }
    private void revealMines(int row,int col){
        for(int i=0;i<this.mineField.length;i++){
            for(int j=0;j<this.mineField[i].length;j++){
                if(this.mineField[i][j].equals("m")){
                    this.revealed[i][j]=true;
                }
            }
        }
        this.mineField[row][col]="X";
    }
    public boolean reveal(int row,int col){
        if(this.isMine(row,col)){
            this.revealMines(row,col);
            return false;
        }
        if(this.revealed[row][col]){
            System.out.println("You have already revealed this cell");
            return false;
        }
        this.revealCell(row,col);
        return true;
    }
    private void revealCell(int row ,int col){
        if(row<0 || row > this.mineField.length){
            throw new IllegalArgumentException();
        }else if(col<0 || col> this.mineField[row].length){
            throw new IllegalArgumentException();
        }
        this.revealed[row][col]=true;
        if(this.mineFieldCopy[row][col].equals("0")) {
            for (int i = row - 1; i < (row + 2) && i < this.mineField.length; i++) {
                try{
                    for (int j = col - 1; j < (col + 2) && j < this.mineField[i].length; j++) {
                        try {
                            if (this.mineFieldCopy[i][j].equals("m")) {
                                continue;
                            }
                            if (this.revealed[i][j]) {
                                continue;
                            }
                            this.reveal(i, j);
                        }catch (ArrayIndexOutOfBoundsException ignored){
                        }
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){
                }
            }
        }

    }
    public String toString(){
        StringBuilder result= new StringBuilder();
        result.append("row");
        result.append((" \u0332" + " \u0332" + " \u0332" + " \u0332").repeat(this.mineField.length));
        result.append(" \u0332 \n");
        for(int i=0;i<this.mineField.length;i++){
            result.append(" ").append(i).append(" ");
            for(int j=0;j<this.mineField[i].length;j++){
                result.append(" \u2502 ");
                if(this.mineField[i][j].equals("!")){
                    result.append("!");
                    continue;
                }
                result.append(this.revealed[i][j] ? this.mineField[i][j] : " ");
            }
            result.append(" \u2502 \n");
        }
        result.append("    \u203E");
        result.append(("\u203E" + "\u203E" + "\u203E" + "\u203E").repeat(this.mineField.length));
        result.append("\n" + "col   ");
        for(int i=0;i<this.mineField.length;i++){
            result.append(i).append("   ");
        }
        return result.toString();
    }
    public boolean lost(){
        return !this.inGame;
    }
    public boolean isWin(){
        if(!this.inGame){
            return false;
        }
        for(int i=0;i<this.mineField.length;i++){
            for(int j=0;j<this.mineField[i].length;j++){
                if(!this.mineFieldCopy[i][j].equals("m") && !this.revealed[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
