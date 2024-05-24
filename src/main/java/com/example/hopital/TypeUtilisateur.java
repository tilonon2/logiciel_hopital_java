package com.example.hopital;

public class TypeUtilisateur {
    private int id;
    private String libelle;
    private boolean accesUtilisateur;
    private boolean accesTypeUtilisateur;
    private boolean accesConsultation;
    private boolean accesMedicament;
    private boolean accesRdv;
    private boolean accesDashboard;
    private boolean accesSalleAttente;
    private boolean accesTraitement;
    private boolean accesPatient;
    private boolean accesSpecialiste;

    // Constructeur
    public TypeUtilisateur(int id, String libelle, boolean accesUtilisateur, boolean accesTypeUtilisateur,
                           boolean accesConsultation, boolean accesMedicament, boolean accesRdv, boolean accesDashboard,
                           boolean accesSalleAttente, boolean accesTraitement, boolean accesPatient, boolean accesSpecialiste) {
        this.id = id;
        this.libelle = libelle;
        this.accesUtilisateur = accesUtilisateur;
        this.accesTypeUtilisateur = accesTypeUtilisateur;
        this.accesConsultation = accesConsultation;
        this.accesMedicament = accesMedicament;
        this.accesRdv = accesRdv;
        this.accesDashboard = accesDashboard;
        this.accesSalleAttente = accesSalleAttente;
        this.accesTraitement = accesTraitement;
        this.accesPatient = accesPatient;
        this.accesSpecialiste = accesSpecialiste;
    }

    public TypeUtilisateur(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public boolean isAccesUtilisateur() {
        return accesUtilisateur;
    }

    public boolean isAccesTypeUtilisateur() {
        return accesTypeUtilisateur;
    }

    public boolean isAccesConsultation() {
        return accesConsultation;
    }

    public boolean isAccesMedicament() {
        return accesMedicament;
    }

    public boolean isAccesRdv() {
        return accesRdv;
    }

    public boolean isAccesDashboard() {
        return accesDashboard;
    }

    public boolean isAccesSalleAttente() {
        return accesSalleAttente;
    }

    public boolean isAccesTraitement() {
        return accesTraitement;
    }

    public boolean isAccesPatient() {
        return accesPatient;
    }

    public boolean isAccesSpecialiste() {
        return accesSpecialiste;
    }

    @Override
    public String toString() {
        return libelle;  // Ceci permet à la ChoiceBox d'afficher le libelle
    }
}
