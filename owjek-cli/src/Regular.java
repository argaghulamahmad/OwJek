/**
 * Created by Arga Ghulam Ahmad on 4/16/2017.
 */
public class Regular extends OwJek {
    private static final int minYearAllowed = 2012;
    private static final int minTopSpeed = 0;
    private static final int minCC = 0;
    private static final int costPerKm = 1000;
    private static final int first2KmCost = 3000;
    private static final int first5KmCost = 0;
    private static final int fixedCost = 0;
    private static final double protectionCost = 0;
    private static final double promo = 0.4;
    private static final String jenis = "Regular";

    public Regular() {
        super(minYearAllowed, minTopSpeed, minCC, costPerKm, first2KmCost, first5KmCost, fixedCost, protectionCost, promo, jenis);
    }

    @Override
    public double getKMSel(double distance) {
        return (distance-2)*super.getCostPerKm();
    }

    @Override
    public double getFinalPromo(double distance) {
        return (super.getPromo()*(6*super.getCostPerKm()));
    }

    @Override
    public double getPrtks(double distance) {
        return 0;
    }

    @Override
    public double getTotal(double distance) {
        return getKMSel(distance) + super.getFirst2KmCost() - getFinalPromo(distance);
    }
}
