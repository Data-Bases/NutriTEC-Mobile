package com.example.nutritec_mobile;

public class Client {

    public static int ID_client = 0;
    public Client(String fname,
                  String flast,
                  String slast,
                  String provincia,
                  String canton,
                  String distrito,
                  String birthdate,
                  int weight,
                  int IMC,
                  String email,
                  int ID,
                  String password) {
        this.fname = fname;
        this.flast = flast;
        this.slast = slast;
        this.provincia = provincia;
        this.canton = canton;
        this.distrito = distrito;
        this.birthdate = birthdate;
        this.weight = weight;
        this.IMC = IMC;
        this.email = email;
        this.ID = ID;
        this.password = password;
    }

    private String fname;
    private String flast;
    private String slast;
    private String provincia;
    private String canton;
    private String distrito;
    private String birthdate;
    private int weight;
    private int IMC;
    private String email;
    private int ID;
    private String password;


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFlast() {
        return flast;
    }

    public void setFlast(String flast) {
        this.flast = flast;
    }

    public String getSlast() {
        return slast;
    }

    public void setSlast(String slast) {
        this.slast = slast;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIMC() {
        return IMC;
    }

    public void setIMC(int IMC) {
        this.IMC = IMC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
