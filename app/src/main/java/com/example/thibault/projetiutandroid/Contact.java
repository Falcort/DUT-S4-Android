package com.example.thibault.projetiutandroid;

import java.io.Serializable;

public class Contact implements Serializable
{
    private String name;
    private String email;
    private String phone;
    private String sexe;
    private String img;

    public Contact(String name, String email, String phone, String sexe, String img)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sexe = sexe;
        this.img = img;
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

    public String getImg()
    {
        return img;
    }

    @Override
    public String toString()
    {
        return "Contact [name=" + name + ", email=" + email + ", phone=" + phone + ", sexe=" + sexe + ", img=" + img + "]";
    }
}
