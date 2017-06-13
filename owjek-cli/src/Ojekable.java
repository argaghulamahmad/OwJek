/**
 * Created by Arga Ghulam Ahmad on 4/21/2017.
 */

/**
 * Interface yang diterapkan untuk membantu mendapatkan nilai [KMSel], [Promo], [Prtks], dan total.
 */
public interface Ojekable {
    /**
     * Method untuk mendapatkan nilai [KMSel].
     * @param distance jarak antara posisi awal dan akhir
     * @return nilai [KMSel]
     */
    double getKMSel(double distance);

    /**
     * Method untuk mendapatkan nilai [Promo].
     * @param distance jarak antara posisi awal dan akhir
     * @return nilai [Promo]
     */
    double getFinalPromo(double distance);

    /**
     * Method untuk mendapatkan nilai [Prtks].
     * @param distance jarak antara posisi awal dan akhir
     * @return nilai [Prtks]
     */
    double getPrtks(double distance);

    /**
     * Method untuk mendapatkan jumlah total.
     * @param distance jarak antara posisi awal dan akhir
     * @return nilai total
     */
    double getTotal(double distance);
}
