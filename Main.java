package com.shierve.rubik;

public class Main {

    public static void main(String[] args) {
        final int[] metaCube = {1, 0, 2, 3, 4, 5, 6, 7};

        new Thread(new Runnable() {
            @Override
            public void run() {
                Piece[] pieces = new Piece[8];
                pieces[0] = new Piece(false, true, false, 0);
                pieces[1] = new Piece(false, true, true, 0);
                pieces[2] = new Piece(false, false, true, 0);
                pieces[3] = new Piece(false, false, false, 0);
                pieces[4] = new Piece(true, false, false, 0);
                pieces[5] = new Piece(true, false, true, 0);
                pieces[6] = new Piece(true, true, true, 0);
                pieces[7] = new Piece(true, true, false, 0);
                Cube cube = new Cube(pieces, metaCube);

                String solveet = "Fu2U2R2R3F3r3RFrFr";
                String pll = "rUruyrf1urUrFRF";
                String test = "3rxF1";
                //cube.run(pll, false);
                if(cube.isSolved()){
                    System.out.print("solved");
                    System.out.print(cube.findOrientation());
                }
                else if(cube.isSolvable()) {
                    String solution = cube.solve();
                    if(solution.equals("")){
                        System.out.print("no solution found");
                    }else{
                        System.out.print(cube.translate(solution));
                    }
                }
            }
        }).start();
    }
}
