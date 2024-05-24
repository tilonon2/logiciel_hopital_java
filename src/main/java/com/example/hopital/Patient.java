package com.example.hopital;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private LocalDate naissance;
    private String nationalite;
    private String sexe;

    public Patient(int id, String nom, String prenom, String adresse, String email, String telephone, LocalDate naissance, String nationalite, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.naissance = naissance;
        this.nationalite = nationalite;
        this.sexe = sexe;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public LocalDate getNaissance() { return naissance; }
    public String getNationalite() { return nationalite; }
    public String getSexe() { return sexe; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setEmail(String email) { this.email = email; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setNaissance(LocalDate naissance) { this.naissance = naissance; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    public void setSexe(String sexe) { this.sexe = sexe; }
}
