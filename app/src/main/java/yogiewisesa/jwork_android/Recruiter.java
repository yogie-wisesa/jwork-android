/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class recruiter
 * untuk menghandle objek recruiter
 */
package yogiewisesa.jwork_android;

public class Recruiter {
    private int id;
    private String name, email, phoneNumber;
    private Location location;

    /**
     * constructor objek recruiter
     * @param id
     * @param name
     * @param email
     * @param phoneNumber
     * @param location
     */
    public Recruiter(int id, String name, String email, String phoneNumber, Location location){
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * method getter id recruiter
     * @return
     */
    public int getId(){
        return id;
    }

    /**
     * getter nama perekrut
     * @return name nama dari perekrut
     */
    public String getName (){
        return name;
    }

    /**
     * getter email perekrut
     * @return email dari perekrut
     */
    public String getEmail (){
        return email;
    }

    /**
     * getter nomor telepon perekrut
     * @return phoneNumber dari perekrut
     */
    public String getPhoneNumber (){
        return phoneNumber;
    }

    /**
     * getter lokasi dari perekrut melalui class location
     * @return location dari perekrut
     */
    public Location getLocation(){
        return location;
    }

    /**
     * setter id perekrut
     * @param id integer id dari perekrut
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * setter email perekrut
     * @param email dari perekrut
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * setter nama perekrut
     * @param name dari perekrut
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * setter nomor telepon perekrut
     * @param phoneNumber string nomor telepon dari perekrut
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * setter lokasi perekrut
     * @param location perekrut dari class location (provinsi, kota, deskripsi)
     */
    public void setLocation(Location location){
        this.location = location;
    }

}
