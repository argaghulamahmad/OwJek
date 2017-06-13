package engine;

/**
 * Created by Arga Ghulam Ahmad on 4/16/2017.
 */
public class Sporty extends OwJek {
    private static final int minYearAllowed = 2015;
    private static final int minTopSpeed = 140;
    private static final int minCC = 0;
    private static final int costPerKm = 3000;
    private static final int first2KmCost = 0;
    private static final int first5KmCost = 20000;
    private static final int fixedCost = 0;
    private static final double protectionCost = 0.1;
    private static final double promo = 0.6;
    private static final String jenis  = "Sporty";

    public Sporty() {
        super(minYearAllowed, minTopSpeed, minCC, costPerKm, first2KmCost, first5KmCost, fixedCost, protectionCost, promo, jenis);
    }

    @Override
    public double getKMSel(double distance) {
        return (distance-5)*super.getCostPerKm();
    }

    @Override
    public double getFinalPromo(double distance) {
        return (super.getPromo()*(8*super.getCostPerKm()));
    }

    @Override
    public double getPrtks(double distance) {
        return super.getProtectionCost()*(getKMSel(distance) + getFirst5KmCost() - getFinalPromo(distance));
    }

    @Override
    public double getTotal(double distance) {
        return getKMSel(distance) + getFirst5KmCost() - getFinalPromo(distance) + getPrtks(distance);
    }
}
