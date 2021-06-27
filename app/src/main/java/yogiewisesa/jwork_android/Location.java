/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class location
 * untuk menghandle objek location dari recruiter
 */
package yogiewisesa.jwork_android;

public class Location {
    private String province, description, city;

    /**
     * constructor objek location
     * @param province
     * @param city
     * @param description
     */
    public Location(String province, String city, String description){
        this.city = city;
        this.province = province;
        this.description = description;
    }

    /**
     * method getter provinsi
     * @return provinsi
     */
    public String getProvince(){
        return province;
    }

    /**
     * getter kota dari lokasi
     * @return city dari lokasi
     */
    public String getCity(){
        return city;
    }

    /**
     * getter dari deskripsi lokasi
     * @return description dari lokasi
     */
    public String getDescription(){
        return description;
    }

    /**
     * setter city dari lokasi
     * @param city dari lokasi
     */
    public void setCity(String city){
        this.city = city;
    }

    /**
     * setter provinsi dari lokasi
     * @param province dari lokasi
     */
    public void setProvince(String province){
        this.province = province;
    }

    /**
     * setter deskripsi dari lokasi
     * @param description dari lokasi
     */
    public void setDescription(String description){
        this.description = description;
    }
}
