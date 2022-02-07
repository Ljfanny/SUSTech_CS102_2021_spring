package xyz.view;

import xyz.model.BoardLocation;
import xyz.model.Square;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    public BoardLocation rightMachine() {

//        System.out.println("111111111111111111");
        Square[][] temp = Save.model.getGrid();
//        for (Square[] ints : temp) {
//            for (Square anInt : ints) {
//                System.out.print(anInt.getNumberOfLandMine()+" ");
//            }
//            System.out.println();
//        }
        int[][] judge = new int[Save.model.getRow()][Save.model.getColumn()];
        int[][] judge2 = new int[Save.model.getRow()][Save.model.getColumn()];
        int counter = 0;
        int counter2 = 0;
        for (int i = 0; i < Save.model.getRow(); i++) {
            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                if (temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() > 0) {
                    if (i - 1 >= 0 && !temp[i - 1][i1].isOpened())
                        counter++;
                    if (i1 - 1 >= 0 && !temp[i][i1 - 1].isOpened())
                        counter++;
                    if (i - 1 >= 0 && i1 - 1 >= 0 && !temp[i - 1][i1 - 1].isOpened())
                        counter++;
                    if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && !temp[i - 1][i1 + 1].isOpened())
                        counter++;
                    if (i + 1 < Save.model.getRow() && !temp[i + 1][i1].isOpened())
                        counter++;
                    if (i1 + 1 < Save.model.getColumn() && !temp[i][i1 + 1].isOpened())
                        counter++;
                    if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && !temp[i + 1][i1 - 1].isOpened())
                        counter++;
                    if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && !temp[i + 1][i1 + 1].isOpened())
                        counter++;

                    if (i - 1 >= 0 && temp[i - 1][i1].hasLandMine() && temp[i - 1][i1].isOpened())
                        counter2++;
                    if (i1 - 1 >= 0 && temp[i][i1 - 1].hasLandMine() && temp[i][i1 - 1].isOpened())
                        counter2++;
                    if (i - 1 >= 0 && i1 - 1 >= 0 && temp[i - 1][i1 - 1].hasLandMine() && temp[i - 1][i1 - 1].isOpened())
                        counter2++;
                    if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && temp[i - 1][i1 + 1].hasLandMine() && temp[i - 1][i1 + 1].isOpened())
                        counter2++;
                    if (i + 1 < Save.model.getRow() && temp[i + 1][i1].hasLandMine() && temp[i + 1][i1].isOpened())
                        counter2++;
                    if (i1 + 1 < Save.model.getColumn() && temp[i][i1 + 1].hasLandMine() && temp[i][i1 + 1].isOpened())
                        counter2++;
                    if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && temp[i + 1][i1 - 1].hasLandMine() && temp[i + 1][i1 - 1].isOpened())
                        counter2++;
                    if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && temp[i + 1][i1 + 1].hasLandMine() && temp[i + 1][i1 + 1].isOpened())
                        counter2++;
                }
                judge[i][i1] = counter;
                judge2[i][i1] = counter2;
                counter = 0;
                counter2 = 0;
            }
        }
//        for (int[] ints : judge) {
//            for (int anInt : ints) {
//                System.out.print(anInt+" ");
//            }
//            System.out.println();
//        }
        for (int i = 0; i < Save.model.getRow(); i++) {
            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                if (temp[i][i1].getNumberOfLandMine() > 0 && temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() == judge[i][i1] + judge2[i][i1]) {
//                    System.out.println(temp[i][i1].getLocation());

                    if (i - 1 >= 0 && temp[i - 1][i1].hasLandMine() && !temp[i - 1][i1].isOpened())
                        return temp[i - 1][i1].getLocation();
                    else if (i1 - 1 >= 0 && temp[i][i1 - 1].hasLandMine() && !temp[i][i1 - 1].isOpened())
                        return temp[i][i1 - 1].getLocation();
                    else if (i - 1 >= 0 && i1 - 1 >= 0 && temp[i - 1][i1 - 1].hasLandMine() && !temp[i - 1][i1 - 1].isOpened())
                        return temp[i - 1][i1 - 1].getLocation();
                    else if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && temp[i - 1][i1 + 1].hasLandMine() && !temp[i - 1][i1 + 1].isOpened())
                        return temp[i - 1][i1 + 1].getLocation();
                    else if (i + 1 < Save.model.getRow() && temp[i + 1][i1].hasLandMine() && !temp[i + 1][i1].isOpened())
                        return temp[i + 1][i1].getLocation();
                    else if (i1 + 1 < Save.model.getColumn() && temp[i][i1 + 1].hasLandMine() && !temp[i][i1 + 1].isOpened())
                        return temp[i][i1 + 1].getLocation();
                    else if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && temp[i + 1][i1 - 1].hasLandMine() && !temp[i + 1][i1 - 1].isOpened())
                        return temp[i + 1][i1 - 1].getLocation();
                    else if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && temp[i + 1][i1 + 1].hasLandMine() && !temp[i + 1][i1 + 1].isOpened())
                        return temp[i + 1][i1 + 1].getLocation();

                }
            }
        }
//        System.out.println("noRight");
        return null;
    }

    public BoardLocation leftMachine() {
        Square[][] temp = Save.model.getGrid();
        int[][] judge = new int[Save.model.getRow()][Save.model.getColumn()];
        int counter = 0;
        for (int i = 0; i < Save.model.getRow(); i++) {
            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                if (temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() > 0) {

                    if (i - 1 >= 0 && temp[i - 1][i1].hasLandMine() && temp[i - 1][i1].isOpened())
                        counter++;
                    if (i1 - 1 >= 0 && temp[i][i1 - 1].hasLandMine() && temp[i][i1 - 1].isOpened())
                        counter++;
                    if (i - 1 >= 0 && i1 - 1 >= 0 && temp[i - 1][i1 - 1].hasLandMine() && temp[i - 1][i1 - 1].isOpened())
                        counter++;
                    if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && temp[i - 1][i1 + 1].hasLandMine() && temp[i - 1][i1 + 1].isOpened())
                        counter++;
                    if (i + 1 < Save.model.getRow() && temp[i + 1][i1].hasLandMine() && temp[i + 1][i1].isOpened())
                        counter++;
                    if (i1 + 1 < Save.model.getColumn() && temp[i][i1 + 1].hasLandMine() && temp[i][i1 + 1].isOpened())
                        counter++;
                    if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && temp[i + 1][i1 - 1].hasLandMine() && temp[i + 1][i1 - 1].isOpened())
                        counter++;
                    if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && temp[i + 1][i1 + 1].hasLandMine() && temp[i + 1][i1 + 1].isOpened())
                        counter++;
                }
                judge[i][i1] = counter;
                counter = 0;
            }
        }
        for (int i = 0; i < Save.model.getRow(); i++) {
            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                if (temp[i][i1].getNumberOfLandMine() > 0 && temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() == judge[i][i1]) {
                    if (i - 1 >= 0 && !temp[i - 1][i1].isOpened())
                        return temp[i - 1][i1].getLocation();
                    else if (i1 - 1 >= 0 && !temp[i][i1 - 1].isOpened())
                        return temp[i][i1 - 1].getLocation();
                    else if (i - 1 >= 0 && i1 - 1 >= 0 && !temp[i - 1][i1 - 1].isOpened())
                        return temp[i - 1][i1 - 1].getLocation();
                    else if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && !temp[i - 1][i1 + 1].isOpened())
                        return temp[i - 1][i1 + 1].getLocation();
                    else if (i + 1 < Save.model.getRow() && !temp[i + 1][i1].isOpened())
                        return temp[i + 1][i1].getLocation();
                    else if (i1 + 1 < Save.model.getColumn() && !temp[i][i1 + 1].isOpened())
                        return temp[i][i1 + 1].getLocation();
                    else if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && !temp[i + 1][i1 - 1].isOpened())
                        return temp[i + 1][i1 - 1].getLocation();
                    else if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && !temp[i + 1][i1 + 1].isOpened())
                        return temp[i + 1][i1 + 1].getLocation();
                }
            }
        }
        return null;
    }

    public BoardLocation maybeLeftMachine() {
        Random b = new Random();
        ArrayList<BoardLocation> temp=new ArrayList<>();
        while (temp.size()==0) {
            int i = b.nextInt(Save.model.getRow());
            int i1 = b.nextInt(Save.model.getColumn());
            if (Save.model.getGrid()[i][i1].isOpened()) {
                if (i - 1 >= 0 && !Save.model.getGrid()[i - 1][i1].isOpened())
                    temp.add(Save.model.getGrid()[i - 1][i1].getLocation());
                else if (i1 - 1 >= 0 && !Save.model.getGrid()[i][i1 - 1].isOpened())
                    temp.add(Save.model.getGrid()[i][i1 - 1].getLocation());
                else if (i - 1 >= 0 && i1 - 1 >= 0 && !Save.model.getGrid()[i - 1][i1 - 1].isOpened())
                    temp.add(Save.model.getGrid()[i - 1][i1 - 1].getLocation());
                else if (i1 + 1 < Save.model.getColumn() && !Save.model.getGrid()[i][i1 + 1].isOpened())
                    temp.add(Save.model.getGrid()[i][i1 + 1].getLocation());
                else if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && !Save.model.getGrid()[i + 1][i1 - 1].isOpened())
                    temp.add(Save.model.getGrid()[i + 1][i1 - 1].getLocation());
                else if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && !Save.model.getGrid()[i - 1][i1 + 1].isOpened())
                    temp.add(Save.model.getGrid()[i - 1][i1 + 1].getLocation());
                else if (i + 1 < Save.model.getRow() && !Save.model.getGrid()[i + 1][i1].isOpened())
                    temp.add(Save.model.getGrid()[i + 1][i1].getLocation());
                else if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && !Save.model.getGrid()[i + 1][i1 + 1].isOpened())
                    temp.add(Save.model.getGrid()[i + 1][i1 + 1].getLocation());
            }


//        Square[][] temp = Save.model.getGrid();
//        int[][] judge = new int[Save.model.getRow()][Save.model.getColumn()];
//        ArrayList<BoardLocation> back = new ArrayList<>();
//        int counter = 0;
//        for (int i = 0; i < Save.model.getRow(); i++) {
//            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
//                if (temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() > 0) {
//                    if (i - 1 >= 0 && temp[i - 1][i1].hasLandMine() && temp[i - 1][i1].isOpened())
//                        counter++;
//                    if (i1 - 1 >= 0 && temp[i][i1 - 1].hasLandMine() && temp[i][i1 - 1].isOpened())
//                        counter++;
//                    if (i - 1 >= 0 && i1 - 1 >= 0 && temp[i - 1][i1 - 1].hasLandMine() && temp[i - 1][i1 - 1].isOpened())
//                        counter++;
//                    if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && temp[i - 1][i1 + 1].hasLandMine() && temp[i - 1][i1 + 1].isOpened())
//                        counter++;
//                    if (i + 1 < Save.model.getRow() && temp[i + 1][i1].hasLandMine() && temp[i + 1][i1].isOpened())
//                        counter++;
//                    if (i1 + 1 < Save.model.getColumn() && temp[i][i1 + 1].hasLandMine() && temp[i][i1 + 1].isOpened())
//                        counter++;
//                    if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && temp[i + 1][i1 - 1].hasLandMine() && temp[i + 1][i1 - 1].isOpened())
//                        counter++;
//                    if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && temp[i + 1][i1 + 1].hasLandMine() && temp[i + 1][i1 + 1].isOpened())
//                        counter++;
//                }
//                judge[i][i1] = counter;
//                counter = 0;
//            }
//        }
//        for (int i = 0; i < Save.model.getRow(); i++) {
//            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
//                if (temp[i][i1].getNumberOfLandMine() > 0 && temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() - 1 > judge[i][i1]) {
//                    if (i - 1 >= 0 && !temp[i - 1][i1].isOpened())
//                        back.add(temp[i - 1][i1].getLocation());
//                    else if (i1 - 1 >= 0 && !temp[i][i1 - 1].isOpened())
//                        back.add(temp[i][i1 - 1].getLocation());
//                    else if (i - 1 >= 0 && i1 - 1 >= 0 && !temp[i - 1][i1 - 1].isOpened())
//                        back.add(temp[i - 1][i1 - 1].getLocation());
//                    else if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && !temp[i - 1][i1 + 1].isOpened())
//                        back.add(temp[i - 1][i1 + 1].getLocation());
//                    else if (i + 1 < Save.model.getRow() && !temp[i + 1][i1].isOpened())
//                        back.add(temp[i + 1][i1].getLocation());
//                    else if (i1 + 1 < Save.model.getColumn() && !temp[i][i1 + 1].isOpened())
//                        back.add(temp[i][i1 + 1].getLocation());
//                    else if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && !temp[i + 1][i1 - 1].isOpened())
//                        back.add(temp[i + 1][i1 - 1].getLocation());
//                    else if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && !temp[i + 1][i1 + 1].isOpened())
//                        back.add(temp[i + 1][i1 + 1].getLocation());
//                }
//                if (back.size() > 0)
//                    break;
//            }
//            if (back.size() > 0)
//                break;
//
//        }
//        Random a = new Random();
//        int b = a.nextInt(back.size());
//        return back.get(b);
        }
        int out=b.nextInt(temp.size());
        return temp.get(out);
    }

    public BoardLocation maybeRightMachine() {
        Random a = new Random();
        ArrayList<BoardLocation> temp=new ArrayList<>();
        while (temp.size()==0) {
            int i = a.nextInt(Save.model.getRow());
            int i1 = a.nextInt(Save.model.getColumn());
            if (Save.model.getGrid()[i][i1].isOpened()) {
                if (i - 1 >= 0 && !Save.model.getGrid()[i - 1][i1].isOpened())
                    temp.add(Save.model.getGrid()[i - 1][i1].getLocation());
                else if (i1 - 1 >= 0 && !Save.model.getGrid()[i][i1 - 1].isOpened())
                    temp.add(Save.model.getGrid()[i][i1 - 1].getLocation());
                else if (i - 1 >= 0 && i1 - 1 >= 0 && !Save.model.getGrid()[i - 1][i1 - 1].isOpened())
                    temp.add(Save.model.getGrid()[i - 1][i1 - 1].getLocation());
                else if (i1 + 1 < Save.model.getColumn() && !Save.model.getGrid()[i][i1 + 1].isOpened())
                    temp.add(Save.model.getGrid()[i][i1 + 1].getLocation());
                else if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && !Save.model.getGrid()[i + 1][i1 - 1].isOpened())
                    temp.add(Save.model.getGrid()[i + 1][i1 - 1].getLocation());
                else if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && !Save.model.getGrid()[i - 1][i1 + 1].isOpened())
                    temp.add(Save.model.getGrid()[i - 1][i1 + 1].getLocation());
                else if (i + 1 < Save.model.getRow() && !Save.model.getGrid()[i + 1][i1].isOpened())
                    temp.add(Save.model.getGrid()[i + 1][i1].getLocation());
                else if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && !Save.model.getGrid()[i + 1][i1 + 1].isOpened())
                    temp.add(Save.model.getGrid()[i + 1][i1 + 1].getLocation());
            }
        }
        int out=a.nextInt(temp.size());
        return temp.get(out);

//        Square[][] temp = Save.model.getGrid();
//        int[][] judge = new int[Save.model.getRow()][Save.model.getColumn()];
//        ArrayList<BoardLocation> back = new ArrayList<>();
//        int counter = 0;
//        for (int i = 0; i < Save.model.getRow(); i++) {
//            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
//                if (temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() > 0) {
//                    if (i - 1 >= 0 && temp[i - 1][i1].hasLandMine() && temp[i - 1][i1].isOpened())
//                        counter++;
//                    if (i1 - 1 >= 0 && temp[i][i1 - 1].hasLandMine() && temp[i][i1 - 1].isOpened())
//                        counter++;
//                    if (i - 1 >= 0 && i1 - 1 >= 0 && temp[i - 1][i1 - 1].hasLandMine() && temp[i - 1][i1 - 1].isOpened())
//                        counter++;
//                    if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && temp[i - 1][i1 + 1].hasLandMine() && temp[i - 1][i1 + 1].isOpened())
//                        counter++;
//                    if (i + 1 < Save.model.getRow() && temp[i + 1][i1].hasLandMine() && temp[i + 1][i1].isOpened())
//                        counter++;
//                    if (i1 + 1 < Save.model.getColumn() && temp[i][i1 + 1].hasLandMine() && temp[i][i1 + 1].isOpened())
//                        counter++;
//                    if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && temp[i + 1][i1 - 1].hasLandMine() && temp[i + 1][i1 - 1].isOpened())
//                        counter++;
//                    if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && temp[i + 1][i1 + 1].hasLandMine() && temp[i + 1][i1 + 1].isOpened())
//                        counter++;
//                }
//                judge[i][i1] = counter;
//                counter = 0;
//            }
//        }
//        for (int i = 0; i < Save.model.getRow(); i++) {
//            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
//                if (temp[i][i1].getNumberOfLandMine() > 0 && temp[i][i1].isOpened() && temp[i][i1].getNumberOfLandMine() - 1 > judge[i][i1]) {
//                    if (i - 1 >= 0 && !temp[i - 1][i1].isOpened())
//                        back.add(temp[i - 1][i1].getLocation());
//                    else if (i1 - 1 >= 0 && !temp[i][i1 - 1].isOpened())
//                        back.add(temp[i][i1 - 1].getLocation());
//                    else if (i - 1 >= 0 && i1 - 1 >= 0 && !temp[i - 1][i1 - 1].isOpened())
//                        back.add(temp[i - 1][i1 - 1].getLocation());
//                    else if (i - 1 >= 0 && i1 + 1 < Save.model.getColumn() && !temp[i - 1][i1 + 1].isOpened())
//                        back.add(temp[i - 1][i1 + 1].getLocation());
//                    else if (i + 1 < Save.model.getRow() && !temp[i + 1][i1].isOpened())
//                        back.add(temp[i + 1][i1].getLocation());
//                    else if (i1 + 1 < Save.model.getColumn() && !temp[i][i1 + 1].isOpened())
//                        back.add(temp[i][i1 + 1].getLocation());
//                    else if (i1 - 1 >= 0 && i + 1 < Save.model.getRow() && !temp[i + 1][i1 - 1].isOpened())
//                        back.add(temp[i + 1][i1 - 1].getLocation());
//                    else if (i + 1 < Save.model.getRow() && i1 + 1 < Save.model.getColumn() && !temp[i + 1][i1 + 1].isOpened())
//                        back.add(temp[i + 1][i1 + 1].getLocation());
//                }
//                if (back.size() > 0)
//                    break;
//            }
//            if (back.size() > 0)
//                break;
//        }
//
//        Random a = new Random();
//        int b = a.nextInt(back.size());
//        return back.get(b);
//    }
    }
}

