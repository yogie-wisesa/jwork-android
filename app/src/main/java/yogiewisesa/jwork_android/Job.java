package yogiewisesa.jwork_android;

public class Job {
    private int id, fee;
    private String name, category;
    private Recruiter recruiter;

    public Job(int id, String name, Recruiter recruiter, int fee, String category){
        this.id = id;
        this.name = name;
        this.recruiter = recruiter;
        this.fee = fee;
        this.category = category;
    }

    /**
     * getter id pekerjaan
     * @return id pekerjaan
     */
    public int getId (){
        return id;
    }

    /**
     * getter nama pekerjaan
     * @return nama pekerjaan
     */
    public String getName (){
        return name;
    }

    /**
     * getter gaji pekerjaan
     * @return fee gaji pekerjaan
     */
    public int getFee (){
        return fee;
    }
    /**
     * getter kategori pekerjaan
     * @return category kategori pekerjaan
     */
    public String getCategory (){
        return category;
    }

    /**
     * getter identitas perekrut
     * @return recruiter identitas perekrut
     */
    public Recruiter getRecruiter(){
        return recruiter;
    }

    /**
     * setter id pekerjaan
     * @param id integer id dari pekerjaan
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * setter nama pekerjaan
     * @param name dari pekerjaan
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * setter identitas perekrut
     * @param recruiter identitas perekrut dari class recruiter
     */
    public void setRecruiter(Recruiter recruiter){
        this.recruiter = recruiter;
    }

    /**
     * setter gaji pekerjaan
     * @param fee integer gaji
     */
    public void setFee(int fee){
        this.fee = fee;
    }

    /**
     * setter kategori pekerjaan
     * @param category string
     */
    public void setCategory(String category){
        this.category = category;
    }
}
