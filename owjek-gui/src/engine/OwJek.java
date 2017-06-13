package engine; /**
 * Created by Arga Ghulam Ahmad on 4/16/2017.
 */

/**
 * Kelas OwJek merupakan parentclass bagi Regular, Sporty, dan Exclusive.
 */
public class OwJek implements Ojekable {
    private int minYearAllowed;
    private int minTopSpeed;
    private int minCC;
    private int costPerKm;
    private double first2KmCost;
    private double first5KmCost;
    private double fixedCost;
    private double protectionCost;
    private double promo;
    private String jenis;

    public int getMinYearAllowed() {
        return minYearAllowed;
    }

    public void setMinYearAllowed(int minYearAllowed) {
        this.minYearAllowed = minYearAllowed;
    }

    public int getMinTopSpeed() {
        return minTopSpeed;
    }

    public void setMinTopSpeed(int minTopSpeed) {
        this.minTopSpeed = minTopSpeed;
    }

    public int getMinCC() {
        return minCC;
    }

    public void setMinCC(int minCC) {
        this.minCC = minCC;
    }

    public int getCostPerKm() {
        return costPerKm;
    }

    public void setCostPerKm(int costPerKm) {
        this.costPerKm = costPerKm;
    }

    public double getFirst2KmCost() {
        return first2KmCost;
    }

    public void setFirst2KmCost(int first2KmCost) {
        this.first2KmCost = first2KmCost;
    }

    public double getFirst5KmCost() {
        return first5KmCost;
    }

    public void setFirst5KmCost(int first5KmCost) {
        this.first5KmCost = first5KmCost;
    }

    public double getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(int fixedCost) {
        this.fixedCost = fixedCost;
    }

    public double getProtectionCost() {
        return protectionCost;
    }

    public void setProtectionCost(double protectionCost) {
        this.protectionCost = protectionCost;
    }

    public double getPromo() {
        return promo;
    }

    public void setPromo(double promo) {
        this.promo = promo;
    }

    public void setFirst2KmCost(double first2KmCost) {
        this.first2KmCost = first2KmCost;
    }

    public void setFirst5KmCost(double first5KmCost) {
        this.first5KmCost = first5KmCost;
    }

    public void setFixedCost(double fixedCost) {
        this.fixedCost = fixedCost;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public OwJek() {

    }

    public OwJek(int minYearAllowed, int minTopSpeed, int minCC, int costPerKm, int first2KmCost, int first5KmCost, int fixedCost, double protectionCost, double promo, String jenis) {
        this.minYearAllowed = minYearAllowed;
        this.minTopSpeed = minTopSpeed;
        this.minCC = minCC;
        this.costPerKm = costPerKm;
        this.first2KmCost = first2KmCost;
        this.first5KmCost = first5KmCost;
        this.fixedCost = fixedCost;
        this.protectionCost = protectionCost;
        this.promo = promo;
        this.jenis = jenis;
    }

    public String toString() {
        return getJenis();
    }

    public double getKMSel(double distance) {
        return 0;
    }

    public double getFinalPromo(double distance) {
        return 0;
    }

    public double getPrtks(double distance) {
        return 0;
    }

    public double getTotal(double distance) {
        return 0;
    }
}
