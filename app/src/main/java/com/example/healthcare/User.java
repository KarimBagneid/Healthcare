package com.example.healthcare;

public class User {

    public String FullName, Phone, Email, Address, Speciality, Title, BirthDate;

    public User(){

    }
    public User (String FullName, String Phone, String Email, String Address, String Speciality,String title, String BirthDate){

        this.FullName = FullName;
        this.Address = Address;
        this.Email = Email;
        this.Phone = Phone;
        this.Speciality = Speciality;
        this.Title = title;
        this.BirthDate = BirthDate;

    }
}
