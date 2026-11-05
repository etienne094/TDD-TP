package fr.greenmove.tarif;

import java.time.LocalDate;

public class TarifPass {

    private final Abonnements abonnements;
    private final CompteurTrajets compteur;
    private final Horloge horloge;

    private static final Montant DEVERROUILLAGE = Montant.euros(1.00);

    private static final Montant MINUTE_VELO = Montant.euros(0.20);
    private static final Montant MINUTE_TROTTINETTE = Montant.euros(0.25);

    private static final Montant PLAFOND = Montant.euros(15.00);
    private static final int MINUTES_INCLUSES_PASS = 20;
    private static final int MAX_TRAJETS_INCLUS_PAR_JOUR = 2;

    private static final int REMISE_ABONNE_POURCENT = 20;


    public TarifPass(Abonnements abonnements, CompteurTrajets compteur, Horloge horloge) {
        this.abonnements = abonnements;
        this.compteur = compteur;
        this.horloge = horloge;
    }

    public Montant prix(String usager, TypeVehicule type, int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("durée négative");
        }

        LocalDate jour = horloge.aujourdhui();

        Montant deverrouillage = DEVERROUILLAGE;
        int minutesFacturees = minutes;

        if (estUnTrajetInclus(usager, jour)) {
            compteur.enregistrerTrajetInclus(usager, jour);
            deverrouillage = Montant.ZERO;
            minutesFacturees = Math.max(0, minutes - MINUTES_INCLUSES_PASS);
        }

        Montant prix = deverrouillage.plus(tarifMinute(type).fois(minutesFacturees)).plafonneA(PLAFOND);

        if (abonnements.estAbonne(usager)) {
            prix = prix.moinsPourcent(REMISE_ABONNE_POURCENT);
        }
        return prix;
    }

    private boolean estUnTrajetInclus(String usager, LocalDate jour) {
        return abonnements.aUnPassActif(usager, jour)
                && compteur.trajetsInclus(usager, jour) < MAX_TRAJETS_INCLUS_PAR_JOUR;
    }

    private Montant tarifMinute(TypeVehicule type) {
        if (type == TypeVehicule.VELO) {
            return MINUTE_VELO;
        } else {
            return MINUTE_TROTTINETTE;
        }
    }

}
