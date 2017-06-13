import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;

/**
 * Created by Arga Ghulam Ahmad on 4/16/2017.
 */


/**
 * Kelas ProgramOwJek merupakan main program pengolah utama input perintah dari pengguna.
 */
public class ProgramOwJek {
    private Map map;
    private boolean exit = false;

    private OwJek owjek;
    private int shortestLength;
    private int[] shortestRoute;

    public ProgramOwJek() {

    }

    public ProgramOwJek(Map map) {
        this.map = map;
        shortestRoute = new int[getMap().getHEIGHT()*getMap().getWIDTH()];
    }


    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
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
     * Method splitting input dan memanggil fungsi yang relevan.
     * @param input string input masukan
     */
    public void processInput(String input) {
        String[] arrInput = input.split(" ");
        if (input.equals("show map")) {
            cmdShowMap();
        } else if (input.equals("exit")) {
            setExit(true);
        } else if (arrInput[0].equals("go")) {
            cmdGo(input);
        } else {
            System.out.println("input tidak valid");
        }
    }

    /**
     * Method pengolah perintah 'go'.
     * @param input string perintah
     */
    public void cmdGo(String input) {
        map.initMap();

        String[] arrInput = input.split(" ");
        String start = arrInput[2];
        String finish = arrInput[4];

        int yStart = (((int) start.charAt(0)) - 65)*10 + Integer.parseInt(start.substring(1,2));
        int xStart = (((int) start.charAt(2)) - 81)*10 + Integer.parseInt(start.substring(3,4));

        int yFinish = (((int) finish.charAt(0)) - 65)*10 + Integer.parseInt(finish.substring(1,2));
        int xFinish = (((int) finish.charAt(2)) - 81)*10 + Integer.parseInt(finish.substring(3,4));

        if ((map.get(yStart, xStart)==' ') && (map.get(yFinish, xFinish)==' ')) {
            int distance = simulateRide(yStart, xStart, yFinish, xFinish);
            map.print();

            String tipeOjek = arrInput[7];
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

            displayRideInfo((double) distance/10);
        } else if ((map.get(yStart, xStart)=='#' && map.get(yFinish, xFinish)=='#')) {
            System.out.println("input tidak valid: " + start + " dan " + finish + " bukan jalan");
        } else {
            if (map.get(yStart, xStart)=='#') {
                System.out.println("input tidak valid: " + start + " bukan jalan");
            } else if (map.get(yFinish, xFinish)=='#') {
                System.out.println("input tidak valid: " + finish + " bukan jalan");
            }
        }
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
    public void displayRideInfo(double distance) {
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
    }

    public static void main(String[] args) {
        Map map = new Map();
        ProgramOwJek program = new ProgramOwJek(map);
        Scanner scan = new Scanner(System.in);
        while(!program.isExit()) {
            String input = scan.nextLine();
            program.processInput(input);
        }
        scan.close();
    }
}