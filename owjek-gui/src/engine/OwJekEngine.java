package engine;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by Arga Ghulam Ahmad on 4/16/2017.
 */


/**
 * Kelas OwJekEngine merupakan main program pengolah utama input perintah dari pengguna.
 */
public class OwJekEngine {
    private Map map;

    private OwJek owjek;
    private int shortestLength;
    private int[] shortestRoute;

    public OwJekEngine() {
        map = new Map();
        this.map = map;
        shortestRoute = new int[getMap().getHEIGHT()*getMap().getWIDTH()];
    }

    public Map getMap() {
        return map;
    }

    public int getShortestLength() {
        return shortestLength;
    }

    public void setShortestLength(int shortestLength) {
        this.shortestLength = shortestLength;
    }

    public OwJek getOwjek() {
        return owjek;
    }

    public void setOwjek(OwJek owjek) {
        this.owjek = owjek;
    }

    /**
     * Method untuk mensimulasikan perjalanan dalam map.
     * @param start lokasi mulai perjalanan dalam string kode koordinat
     * @param finish lokasi mulai perjalanan dalam string kode koordinat
     * @param tipeOjek tipe owjek yang digunakan
     * @return array double hasil perhitungan.
     */
    public double[] cmdGo(String start, String finish, String tipeOjek) {
        map.initMap();

        int yStart = (((int) start.charAt(0)) - 65)*10 + Integer.parseInt(start.substring(1,2));
        int xStart = (((int) start.charAt(2)) - 81)*10 + Integer.parseInt(start.substring(3,4));

        int yFinish = (((int) finish.charAt(0)) - 65)*10 + Integer.parseInt(finish.substring(1,2));
        int xFinish = (((int) finish.charAt(2)) - 81)*10 + Integer.parseInt(finish.substring(3,4));

        if ((map.get(yStart, xStart)==' ') && (map.get(yFinish, xFinish)==' ')) {
            try {
                int distance = simulateRide(yStart, xStart, yFinish, xFinish);

                map.print();

                switch (tipeOjek) {
                    case "Regular": {
                        OwJek owjek = new Regular();
                        setOwjek(owjek);
                        break;
                    }
                    case "Sporty": {
                        OwJek owjek = new Sporty();
                        setOwjek(owjek);
                        break;
                    }
                    case "Exclusive": {
                        OwJek owjek = new Exclusive();
                        setOwjek(owjek);
                        break;
                    }
                }

                return displayRideInfo((double) distance/10);
            } catch (NullPointerException | ArrayIndexOutOfBoundsException error) {
                JOptionPane.showMessageDialog(null, "Maaf, sistem mengalami gangguan. Silahkan coba lagi.");
            }
        } else if ((map.get(yStart, xStart)=='#' && map.get(yFinish, xFinish)=='#')) {
            JOptionPane.showMessageDialog(null, "Input invalid: " + start + " dan " + finish + " bukan jalan", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (map.get(yStart, xStart)=='#') {
                JOptionPane.showMessageDialog(null, "Input invalid: " + start + " bukan jalan", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (map.get(yFinish, xFinish)=='#') {
                JOptionPane.showMessageDialog(null, "Input invalid: " + finish + " bukan jalan", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return new double[0];
    }

    /**
     * Method untuk menenetukan dan menggambar rute terpendek pada peta, kemudian menghitung jarak posisi awal dan akhir.
     * @param yCurrent posisi awal dalam sumbu y
     * @param xCurrent posisi awal dalam sumbu x
     * @param yFinish posisi awal dalam sumbu y
     * @param xFinish posisi awal dalam sumbu x
     * @return jarak dari posisi awal sampai akhir dalam satuan 100 meter
     */
    public int simulateRide(int yCurrent, int xCurrent, int yFinish, int xFinish) {
        int distance = 0;
        int[] route = new int[getMap().getHEIGHT()*getMap().getWIDTH()];
        int routelength = 0;

        for (int i=0; i<map.getHEIGHT()*map.getWIDTH(); i++){
            shortestRoute[i]=-1;
            route[i]=-1;
        }

        setShortestLength(getMap().getHEIGHT()*getMap().getWIDTH());
        findRoute(yCurrent, xCurrent, yFinish, xFinish, route, routelength);

        //menggambar rute pada map
        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap()[i].length; j++) {
                if (wasHere(i ,j , shortestRoute, getShortestLength())) {
                    if (i==yCurrent && j==xCurrent) {
                        map.set('S', i, j);
                    } else if (i==yFinish && j==xFinish) {
                        map.set('F', i, j);
                        distance++;
                    } else {
                        map.set('.', i, j);
                        distance++;
                    }
                }
            }
        }

        return distance;
    }

    /**
     * Method yang memeriksa apakah suatu koordinat telah dilewati atau tidak.
     * @param yCoor koordinat sumbu y
     * @param xCoor koordinat sumbu x
     * @param route array integer id unik koordinat rute (koordinat mana yang telah dilewati)
     * @param routelength panjang rute
     * @return boolean telah dilewati atau tidak
     */
    public boolean wasHere(int yCoor, int xCoor, int[] route, int routelength){
        for (int i=0; i<routelength; i++) {
            if (route[i]==xCoor*map.getWIDTH()+yCoor) return true;
        }
        return false;
    }

    /**
     * Method rekursif yang menentukan jalur terpendek yang mungkin.
     * Fungsi ini akan mencari semua rute yang mungkin, kemudian membandingkan panjang rute baru dengan rute lama.
     * Bila rute baru lebih pendek, rute akan disimpan di instance variabel shortestRoute.
     * @param yCurrent koordinat awal pada sumbu y
     * @param xCurrent koordinat awal pada sumbu x
     * @param yFinish koordinat akhir pada sumbu y
     * @param xFinish koordinat akhir pada sumbu x
     * @param route array integer id unik koordinat informasi rute (koordinat mana yang telah dilewati)
     * @param routelength panjang rute
     */
    public void findRoute(int yCurrent, int xCurrent, int yFinish, int xFinish, int[] route, int routelength){
        /*
           Base Case:
                1. Koordinat saat ini diluar dari jangkauan map.
                2. Koordinat saat ini berada selain di jalan.
                3. Koordinat saat ini telah dilalui sebelumnya.
                4. Koordinat saat ini berada pada koordinat lokasi tujuan.
         */

        if (map.get(yCurrent, xCurrent)=='#') return ;
        if (yCurrent < 0 || xCurrent < 0 || yCurrent >= map.getHEIGHT() || xCurrent >= map.getWIDTH()) return;
        if (wasHere(yCurrent, xCurrent, route, routelength)) return;

        int tempRoute[] = new int[routelength+1];
        System.arraycopy(route, 0, tempRoute, 0, routelength);
        tempRoute[routelength++] = xCurrent*map.getWIDTH()+yCurrent;

        if (yCurrent==yFinish && xCurrent==xFinish){
            if (routelength < getShortestLength()){
                setShortestLength(routelength);
                System.arraycopy(tempRoute, 0, shortestRoute, 0, routelength);
            }
            return;
        }

		/*Recursive Case*/
        findRoute(yCurrent, xCurrent+1, yFinish, xFinish, tempRoute, routelength);
        findRoute(yCurrent+1, xCurrent, yFinish, xFinish, tempRoute, routelength);
        findRoute(yCurrent-1, xCurrent, yFinish, xFinish, tempRoute, routelength);
        findRoute(yCurrent, xCurrent-1, yFinish, xFinish, tempRoute, routelength);
    }


    /**
     * Method yang berfungsi menampilkan map.
     */
    public void cmdShowMap() {
        map.initMap();
        getMap().print();
    }

    /**
     * Method yang menampilkan informasi mengenai biaya perjalanan.
     * @param distance jarak yang ditempuh dalam satuan kilometer
     */

    /**
     * Method yang menampilkan informasi mengenai biaya perjalanan.
     * @param distance jarak yang ditempuh dalam satuan kilometer
     * @return array angka bertipe double yang merupakan hasil perhitungan.
     */
    public double[] displayRideInfo(double distance) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        System.out.println("Terimakasih telah melakukan perjalanan dengan OW-JEK.");
        System.out.println("[Jarak] " + distance + " KM");
        System.out.println("[TipeO] " + getOwjek().toString());
        if (owjek instanceof Exclusive) {
            System.out.println("[Fixed] " + String.format("%s", kursIndonesia.format(getOwjek().getFixedCost())) + " (+)");
        }
        if (owjek instanceof Regular) {
            System.out.println("[2KMPe] " + String.format("%s", kursIndonesia.format(getOwjek().getFirst2KmCost())) + " (+)");
        }
        if (owjek instanceof Sporty) {
            System.out.println("[5KmPe] " + String.format("%s", kursIndonesia.format(getOwjek().getFirst5KmCost())) + " (+)");
        }
        System.out.println("[KMSel] " + String.format("%s", kursIndonesia.format(getOwjek().getKMSel(distance))) + " (+)");
        System.out.println("[Promo] " + String.format("%s", kursIndonesia.format(getOwjek().getFinalPromo(distance)))+ " (-)");
        if (owjek instanceof Sporty || owjek instanceof  Exclusive) {
            System.out.println("[Prtks] " + String.format("%s", kursIndonesia.format(getOwjek().getPrtks(distance))) + " (+)");
        }
        System.out.println("[TOTAL] " + String.format("%s", kursIndonesia.format(getOwjek().getTotal(distance))));

        double [] resultArr = new double[8];
        resultArr[0] = distance;
        resultArr[1] = getOwjek().getFixedCost();
        resultArr[2] = getOwjek().getFirst2KmCost();
        resultArr[3] = getOwjek().getFirst5KmCost();
        resultArr[4] = getOwjek().getKMSel(distance);
        resultArr[5] = getOwjek().getFinalPromo(distance);
        resultArr[6] = getOwjek().getPrtks(distance);
        resultArr[7] = getOwjek().getTotal(distance);
        return resultArr;

    }
}