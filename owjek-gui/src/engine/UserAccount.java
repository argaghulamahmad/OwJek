package engine;

/**
 * Created by Arga Ghulam Ahmad on 5/13/2017.
 */

/**
 * Kelas yang merepresentasikan pengguna program OwJek.
 */
public class UserAccount {
    private String username;
    private String email;
    private String phonenumber;
    private String fullname;
    private String birthdate;
    private String birthlocation;
    private Gender gender;
    private Status status;
    private String occupation;
    private String homeaddress;

    public UserAccount() {
    }

    /**
     * @param username username pengguna
     * @param email email pengguna
     * @param phonenumber nomor handphone pengguna
     * @param fullname nama lengkap pengguna
     * @param birthdate tanggal lahir pengguna
     * @param birthlocation tempat lahir pengguna
     * @param gender jenis kelamin pengguna
     * @param status status pengguna
     * @param occupation pekerjaan pengguna
     * @param homeaddress alamat rumah pengguna
     */
    public UserAccount(String username, String email, String phonenumber, String fullname, String birthdate, String birthlocation, Gender gender, Status status, String occupation, String homeaddress) {
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.fullname = fullname;
        this.birthdate = birthdate;
        this.birthlocation = birthlocation;
        this.gender = gender;
        this.status = status;
        this.occupation = occupation;
        this.homeaddress = homeaddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthlocation() {
        return birthlocation;
    }

    public void setBirthlocation(String birthlocation) {
        this.birthlocation = birthlocation;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    /**
     * Method untuk menetapkan representasi akun pengguna.
     * @param username username pengguna
     * @param email email pengguna
     * @param phonenumber nomor handphone pengguna
     * @param fullname nama lengkap pengguna
     * @param birthdate tanggal lahir pengguna
     * @param birthlocation tempat lahir pengguna
     * @param gender jenis kelamin pengguna
     * @param status status pengguna
     * @param occupation pekerjaan pengguna
     * @param homeaddress alamat rumah pengguna
     */
    public void setInfo (String username, String email, String phonenumber, String fullname, String birthdate, String birthlocation, String gender, String status, String occupation, String homeaddress) {
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.fullname = fullname;
        this.birthdate = birthdate;
        this.birthlocation = birthlocation;

        if (gender.equals("MALE")) {
            this.gender = Gender.MALE;
        } else if (gender.equals("FEMALE")) {
            this.gender = Gender.FEMALE;
        }

        if (status.equals("SINGLE")) {
            this.status = Status.SINGLE;
        } else if (gender.equals("MARRIED")) {
            this.status = Status.MARRIED;
        }

        this.occupation = occupation;
        this.homeaddress = homeaddress;
    }
}
