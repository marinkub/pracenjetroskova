package com.example.pracenjetroskova;

public class Troškovi {
    public String trosak;
    public String naziv;
    public String opis;
    public String datum;

    public Troškovi(){

    }

    public Troškovi(String trosak, String naziv, String opis, String datum)
    {
        this.trosak = trosak;
        this.naziv = naziv;
        this.opis = opis;
        this.datum = datum;
    }

    public String DajDatum()
    {
        return this.datum;
    }

    public String DajNaziv()
    {
        return this.naziv;
    }

    public String DajOpis()
    {
        return this.opis;
    }

    public String DajTrosak()
    {
        return this.trosak;
    }
}
