package com.example.hopital;

public class PayeeDetails {

	public PayeeDetails(){

	}

	private String name;
	private int age;
	private int taille;
	private int poids;
	private String mob;
	private String prenom;
	private String temperature_corporelle;
	private String pression_arterielle;
	private String frequence_cardiaque;
	private String frequence_respiratoire;
	private String saturation_oxygene;
	// private String email;
	// private String card;
	// private String payment;

	//Initialisation des constructions, setter et getter

	//Nom

	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name.isEmpty() || (name.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre nom.");

		} else {
			this.name = name;
		}


	}

	//prenom

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		if (prenom.isEmpty() || (prenom.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre prenom.");

		} else {
			this.prenom = prenom;
		}


	}

	//taille

	public int getTaille() {
		return taille;
	}
	public void setTaille(int taille) {
		if (taille == 0) {

			throw new NullPointerException("Vous devez entrer votre taille.");

		} else {
			this.taille = taille;
		}

		this.taille = taille;
	}

	//age

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		if (age == 0) {

			throw new NullPointerException("Vous devez entrer votre âge.");

		} else {
			this.age = age;
		}

		this.age = age;
	}


	//poids

	public int getPoids() {
		return poids;
	}
	public void setPoids(int poids) {
		if (poids == 0) {

			throw new NullPointerException("Vous devez entrer votre poids.");

		} else {
			this.poids = poids;
		}

		this.poids = poids;
	}

	//numéro de téléphone


	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		if (mob.isEmpty() || (mob.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre numéro de téléphone.");

		} else {
			if(mob.matches("[0-9]{10}"))
			{
				this.mob = mob;
			}
			else
			{
				throw new IllegalArgumentException("Le numéro de téléphone doit être composé de 10 chiffres.");
			}
		}


	}

	//température corporelle

	public String getTemperatureCorporelle() {
		return temperature_corporelle;
	}
	public void setTemperatureCorporelle(String temperature_corporelle) {
		if (temperature_corporelle.isEmpty() || (temperature_corporelle.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre temperature corporelle.");

		} else {
			this.temperature_corporelle = temperature_corporelle;
		}


	}


	//fréquence cardiaque


	public String getFrequenceCardiaque() {
		return frequence_cardiaque;
	}
	public void setFrequenceCardiaque(String frequence_cardiaque) {
		if (frequence_cardiaque.isEmpty() || (frequence_cardiaque.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre frequence cardiaque.");

		} else {
			this.frequence_cardiaque = frequence_cardiaque;
		}


	}

	//pression artérielle

	public String getPressionArtérielle() {
		return pression_arterielle;
	}
	public void setPressionArtérielle(String pression_arterielle) {
		if (pression_arterielle.isEmpty() || (pression_arterielle.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre pression arterielle.");

		} else {
			this.pression_arterielle = pression_arterielle;
		}


	}


	//fréquence respiratoire

	public String getFrequenceRespiratoire() {
		return frequence_respiratoire;
	}
	public void setFrequenceRespiratoire(String frequence_respiratoire) {
		if (frequence_respiratoire.isEmpty() || (frequence_respiratoire.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre fréquence respiratoire.");

		} else {
			this.frequence_respiratoire = frequence_respiratoire;
		}


	}


	//saturation en oxygène

	public String getSaturationOxygene() {
		return saturation_oxygene;
	}
	public void setSaturationOxygene(String saturation_oxygene) {
		if (saturation_oxygene.isEmpty() || (saturation_oxygene.trim()).isEmpty()) {

			throw new NullPointerException("Vous devez entrer votre Saturation en Oxygène.");

		} else {
			this.saturation_oxygene = saturation_oxygene;
		}


	}

	//Mail
	


	// public String getEmail() {
	// 	return email;
	// }
	// public void setEmail(String email) {
	// 	if (email.isEmpty() || (email.trim()).isEmpty()) {

	// 		throw new NullPointerException("Vous devez entrer votre email.");

	// 	} else {
	// 		this.email = email;
	// 	}

	// }

	//card


	// public String getCard() {
	// 	return card;
	// }
	// public void setCard(String card) {
	// 	if (card.isEmpty() || (card.trim()).isEmpty()) {

	// 		throw new NullPointerException("You must enter an your card number to proceed.");

	// 	} else {
	// 		if(card.matches("[0-9]{4}[-]{1}[0-9]{4}[-]{1}[0-9]{4}[-]{1}[0-9]{4}") || card.matches("[0-9]{4}[0-9]{4}[0-9]{4}[0-9]{4}")){
	// 			this.card = card;
	// 		}else{
	// 			throw new IllegalArgumentException("Card number must be of 16 digits.");
	// 		}

	// 	}

	// }


	//paiement

	// public String getPayment() {
	// 	return payment;
	// }
	// public void setPayment(String payment) {

	// 		this.payment = payment;

	// }



}
