/**
 * Created by Arga Ghulam Ahmad on 4/16/2017.
 */
public class Exclusive extends OwJek {
    private static final int minYearAllowed = 2016;
    private static final int minTopSpeed = 0;
    private static final int minCC = 500;
    private static final int costPerKm = 5000;
    private static final int first2KmCost = 0;
    private static final int first5KmCost = 0;
    private static final int fixedCost = 10000;
    private static final double protectionCost = 0.05;
    private static final double promo = 0.5;
    private static final String jenis  = "Exclusive";

    public Exclusive() {
        super(minYearAllowed, minTopSpeed, minCC, costPerKm, first2KmCost, first5KmCost, fixedCost, protectionCost, promo, jenis);
    }

    @Override
    public double getKMSel(double distance) {
        return distance*super.getCostPerKm();
    }

    @Override
    public double getFinalPromo(double distance) {
        return (super.getPromo()*(getKMSel(distance) + getFixedCost()));
    }

    @Override
    public double getPrtks(double distance) {
        return super.getProtectionCost()*(getKMSel(distance) + super.getFixedCost() - getFinalPromo(distance));
    }

    @Override
    public double getTotal(double distance) {
        return getKMSel(distance) + super.getFixedCost() - getFinalPromo(distance) + getPrtks(distance);
    }
}
