package com.shierve.rubik;

public class Piece {

    int orientation;
    int id;

    public Piece(boolean w, boolean r, boolean b, int orientation) {
        if(w){
            if(r){
                if(b) id = 6;
                else id = 7;
            }else{
                if(b) id = 5;
                else id = 4;
            }
        }else{
            if(r){
                if(b) id = 1;
                else id = 0;
            }else{
                if(b) id = 2;
                else id = 3;
            }
        }
        this.orientation = orientation;
    }
}
