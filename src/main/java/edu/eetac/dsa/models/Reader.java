package edu.eetac.dsa.models;

import java.util.ArrayList;
import java.util.List;
public class Reader {
    private String id;
    private String name;
    private String surname;
    private String dni;
    private String birthDate;
    private String birthPlace;
    private String address;
    private List<Loan> loans;

    public Reader(String id, String name, String surname, String dni, String birthDate, String birthPlace, String address) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
        loans = new ArrayList<>();
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getDni(){
        return dni;
    }
    public String getBirthDate(){
        return birthDate;
    }
    public String getBirthPlace(){
        return birthPlace;
    }
    public String getAddress(){
        return address;
    }
    public List<Loan> getLoans(){
        return loans;
    }
    public void  addLoan(Loan loan){
        this.loans.add(loan);
    }
    public void updateData(String name, String surname,  String dni, String birthDate, String birthPlace, String address){
        this.name=name;
        this.surname=surname;
        this.dni=dni;
        this.birthDate=birthDate;
        this.birthPlace=birthPlace;
        this.address=address;
    }
}
