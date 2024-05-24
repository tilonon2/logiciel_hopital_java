package com.example.hopital;

import java.time.LocalDate;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String sexe;
    private LocalDate dateNaissance;
    private String fonction;
    private String mdp; // Mot de passe, si nécessaire dans votre logique
    private int typeUser; // Clé étrangère pour TypeUtilisateur

    public Utilisateur(int id, String nom, String prenom, String sexe, LocalDate dateNaissance, String fonction, String mdp, int typeUser) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.fonction = fonction;
        this.mdp = mdp;
        this.typeUser = typeUser;
    }

    // Getters et Setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getSexe() { return sexe; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getFonction() { return fonction; }
    public String getMdp() { return mdp; }
    public int getTypeUser() { return typeUser; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public void setFonction(String fonction) { this.fonction = fonction; }
    public void setMdp(String mdp) { this.mdp = mdp; }
    public void setTypeUser(int typeUser) { this.typeUser = typeUser; }
}
