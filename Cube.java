package com.shierve.rubik;

public class Cube {

    private static final int GOD_NUM = 11;
    private static final char[] MOVES = {'R','F','U','r','f','u','1','2','3'};

    Piece[] pieces;
    int[] metaCube;

    public Cube(Piece[] p, int[] metaCube) {
        pieces = p;
        this.metaCube = metaCube;  //metaCube[i] has as value the piece in place i
    }

    public String solve(){
        String sol = "";
        boolean found = false;
        for(int i = 1; i <= GOD_NUM && !found; i++){
            sol = genAlgs(i);
            if (!sol.equals("")) found = true;
        }
        return sol;
    }

    private String genAlgs(int n) { //Generates all valid algs with n moves. Returns null if not found
        boolean found;
        String sol = "";
        if(n == 1){
            for(int i = 0; i < 9; i++){
                found = run(String.valueOf(MOVES[i]), true);
                if(found) return String.valueOf(MOVES[i]);
            }
        }else{
            for(int i = 0; i < 9; i++) {
                sol = genAlgs(n, 1, String.valueOf(MOVES[i]), i % 3);
                if (!sol.equals("")) return sol;
            }
        }
        return sol;
    }

    private String genAlgs(int n, int moves, String alg, int last){
        boolean found;
        if (moves == n - 1){
            for(int i = 0; i < 9; i++){
                if (i % 3 != last) {
                    found = run(alg + MOVES[i], true);
                    if (found) return (alg + MOVES[i]);
                }
            }
            return "";
        }else{
            for(int i = 0; i < 9; i++) {
                if(i % 3 != last){
                    String sol = genAlgs(n, moves + 1, alg + MOVES[i], i % 3);
                    if (!sol.equals("")) return sol;
                }
            }
            return "";
        }
    }

    public boolean run(String alg, boolean back){ //returns true if it was solution
        boolean sol = false;

        char[] algArray = alg.toCharArray();
        for (int i = 0; i < algArray.length; i++){
            switch (algArray[i]){
                case 'R':
                    r();
                    break;
                case 'r':
                    r3();
                    break;
                case '1':
                    r2();
                    break;
                case 'F':
                    f();
                    break;
                case 'f':
                    f3();
                    break;
                case '2':
                    f2();
                    break;
                case 'U':
                    u();
                    break;
                case 'u':
                    u3();
                    break;
                case '3':
                    u2();
                    break;
                case 'x':
                    x();
                    break;
                case 'y':
                    y();
                    break;
                case 'z':
                    z();
                    break;
            }
        }
        if(isSolved()) sol = true;
        if(back){
            for (int i = algArray.length - 1; i >= 0; i--){
                switch (algArray[i]){
                    case 'R':
                        r3();
                        break;
                    case 'r':
                        r();
                        break;
                    case '1':
                        r2();
                        break;
                    case 'F':
                        f3();
                        break;
                    case 'f':
                        f();
                        break;
                    case '2':
                        f2();
                        break;
                    case 'U':
                        u3();
                        break;
                    case 'u':
                        u();
                        break;
                    case '3':
                        u2();
                        break;
                }
            }
        }

        return sol;
    }

    public String translate(String alg){
        char[] algArray = alg.toCharArray();
        String translation = "";
        for(int i = 0; i < algArray.length; i++){
            switch(algArray[i]){
                case 'R':
                    translation += 'R';
                    break;
                case 'r':
                    translation += "R'";
                    break;
                case '1':
                    translation += "R2";
                    break;
                case 'F':
                    translation += 'F';
                    break;
                case 'f':
                    translation += "F'";
                    break;
                case '2':
                    translation += "F2";
                    break;
                case 'U':
                    translation += 'U';
                    break;
                case 'u':
                    translation += "U'";
                    break;
                case '3':
                    translation += "U2";
                    break;
            }
            if (i != algArray.length - 1) translation += " ";
        }
        return translation;
    }

    public int findOrientation(){
        boolean out1 = true;
        boolean out2 = true;
        boolean out3 = true;
        boolean out = true;
        for (int i = 0; i < 8 && out; i++) {
            if (pieces[i].orientation % 3 != 0){
                out1 = false;
            }
            if (i % 2 == 0){
                if ((pieces[metaCube[i]].orientation - 1) % 3 != 0){
                    out2 = false;
                }
                if ((pieces[metaCube[i]].orientation + 1) % 3 != 0){
                    out3 = false;
                }
            }else{
                if ((pieces[metaCube[i]].orientation + 1) % 3 != 0){
                    out2 = false;
                }
                if ((pieces[metaCube[i]].orientation - 1) % 3 != 0){
                    out3 = false;
                }
            }
            out = (out1 || out2 || out3);
        }
        if(out1) return 1;
        if(out2) return 2;
        if(out3) return 3;
        return 0;
    }

    public boolean isSolved(){
        boolean out = true;
        int orientation = findOrientation();
        //oriented
        if (orientation != 0){
            if (orientation == 1){  //WY
                if (pieces[metaCube[0]].id + pieces[metaCube[7]].id != 7) out = false;


                if(pieces[metaCube[0]].id < 4){
                    for (int i = 0; i < 4 && out; i++){
                        if(pieces[metaCube[(i + 1) % 4]].id != (pieces[metaCube[i]].id + 1) % 4) out = false;
                        if(pieces[metaCube[((i + 1) % 4) + 4]].id != ((pieces[metaCube[i + 4]].id + 1) % 4) + 4) out = false;
                    }
                }else{
                    for (int i = 0; i < 4 && out; i++){
                        if(pieces[metaCube[(i + 1) % 4]].id != ((pieces[metaCube[i]].id + 1) % 4) + 4) out = false;
                        if(pieces[metaCube[((i + 1) % 4) + 4]].id != (pieces[metaCube[i + 4]].id + 1) % 4) out = false;
                    }
                }
            }
            else if (orientation == 2){
                this.z();
                out = isSolved();
                this.z3();
            }else{
                this.x();
                out = isSolved();
                this.x3();
            }
        }
        else out = false;

        return out;
    }

    public boolean isSolvable(){
        int total = 0;
        for(int i = 0; i < 8; i++){
            total += pieces[i].orientation;
        }
        return (total % 3 == 0);
    }

    public void r(){
        int aux = metaCube[2];
        metaCube[2] = metaCube[3];
        metaCube[3] = metaCube[4];
        metaCube[4] = metaCube[5];
        metaCube[5] = aux;
        pieces[metaCube[2]].orientation--;
        pieces[metaCube[3]].orientation++;
        pieces[metaCube[4]].orientation--;
        pieces[metaCube[5]].orientation++;
    }

    public void f(){
        int aux = metaCube[0];
        metaCube[0] = metaCube[7];
        metaCube[7] = metaCube[4];
        metaCube[4] = metaCube[3];
        metaCube[3] = aux;
        pieces[metaCube[0]].orientation++;
        pieces[metaCube[7]].orientation--;
        pieces[metaCube[4]].orientation++;
        pieces[metaCube[3]].orientation--;
    }

    public void u(){
        int aux = metaCube[0];
        metaCube[0] = metaCube[3];
        metaCube[3] = metaCube[2];
        metaCube[2] = metaCube[1];
        metaCube[1] = aux;
    }

    public void r2(){
        int aux = metaCube[2];
        metaCube[2] = metaCube[4];
        metaCube[4] = aux;
        aux = metaCube[3];
        metaCube[3] = metaCube[5];
        metaCube[5] = aux;
    }

    public void f2(){
        int aux = metaCube[0];
        metaCube[0] = metaCube[4];
        metaCube[4] = aux;
        aux = metaCube[3];
        metaCube[3] = metaCube[7];
        metaCube[7] = aux;
    }

    public void u2(){
        int aux = metaCube[0];
        metaCube[0] = metaCube[2];
        metaCube[2] = aux;
        aux = metaCube[1];
        metaCube[1] = metaCube[3];
        metaCube[3] = aux;
    }

    public void r3(){
        int aux = metaCube[2];
        metaCube[2] = metaCube[5];
        metaCube[5] = metaCube[4];
        metaCube[4] = metaCube[3];
        metaCube[3] = aux;
        pieces[metaCube[2]].orientation--;
        pieces[metaCube[5]].orientation++;
        pieces[metaCube[4]].orientation--;
        pieces[metaCube[3]].orientation++;
    }

    public void f3(){
        int aux = metaCube[0];
        metaCube[0] = metaCube[3];
        metaCube[3] = metaCube[4];
        metaCube[4] = metaCube[7];
        metaCube[7] = aux;
        pieces[metaCube[0]].orientation++;
        pieces[metaCube[7]].orientation--;
        pieces[metaCube[4]].orientation++;
        pieces[metaCube[3]].orientation--;
    }

    public void u3(){
        int aux = metaCube[0];
        metaCube[0] = metaCube[1];
        metaCube[1] = metaCube[2];
        metaCube[2] = metaCube[3];
        metaCube[3] = aux;
    }

    private void x(){
        this.r();
        int aux = metaCube[0];
        metaCube[0] = metaCube[7];
        metaCube[7] = metaCube[6];
        metaCube[6] = metaCube[1];
        metaCube[1] = aux;
        pieces[metaCube[0]].orientation--;
        pieces[metaCube[7]].orientation++;
        pieces[metaCube[6]].orientation--;
        pieces[metaCube[1]].orientation++;
    }

    private void y(){
        this.u();
        int aux = metaCube[7];
        metaCube[7] = metaCube[4];
        metaCube[4] = metaCube[5];
        metaCube[5] = metaCube[6];
        metaCube[6] = aux;
    }

    private void z(){
        this.f();
        int aux = metaCube[1];
        metaCube[1] = metaCube[6];
        metaCube[6] = metaCube[5];
        metaCube[5] = metaCube[2];
        metaCube[2] = aux;
        pieces[metaCube[1]].orientation--;
        pieces[metaCube[2]].orientation++;
        pieces[metaCube[5]].orientation--;
        pieces[metaCube[6]].orientation++;
    }

    private void x3(){
        this.r3();
        int aux = metaCube[0];
        metaCube[0] = metaCube[1];
        metaCube[1] = metaCube[6];
        metaCube[6] = metaCube[7];
        metaCube[7] = aux;
        pieces[metaCube[0]].orientation--;
        pieces[metaCube[7]].orientation++;
        pieces[metaCube[6]].orientation--;
        pieces[metaCube[1]].orientation++;
    }

    private void y3(){
        this.u3();
        int aux = metaCube[7];
        metaCube[7] = metaCube[6];
        metaCube[6] = metaCube[5];
        metaCube[5] = metaCube[4];
        metaCube[4] = aux;
    }

    private void z3(){
        this.f3();
        int aux = metaCube[1];
        metaCube[1] = metaCube[2];
        metaCube[2] = metaCube[5];
        metaCube[5] = metaCube[6];
        metaCube[6] = aux;
        pieces[metaCube[1]].orientation--;
        pieces[metaCube[2]].orientation++;
        pieces[metaCube[5]].orientation--;
        pieces[metaCube[6]].orientation++;
    }
}
