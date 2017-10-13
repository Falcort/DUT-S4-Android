package com.example.thibault.projetiutandroid;

/**
 * Created by Thibault on 13/10/2017.
 */

public class Contact
{
    private String name;
    private String email;
    private String phone;
    private String sexe;

    public Contact(String name, String email, String phone, String sexe)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sexe = phone;
    }

    public String getName()
    {
        return this.name;
    }

    public String getEmail()
    {
        return  this.email;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public String getSexe()
    {
        return this.sexe;
    }
}
