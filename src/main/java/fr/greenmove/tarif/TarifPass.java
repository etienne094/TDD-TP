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

    public TarifPass(Abonnements abonnements, CompteurTrajets compteur, Horloge horloge) {
        this.abonnements = abonnements;
        this.compteur = compteur;
        this.horloge = horloge;
    }

    public Montant prix(String usager, TypeVehicule type, int minutes) {
        LocalDate jour = horloge.aujourdhui();
        if (abonnements.aUnPassActif(usager, jour) && minutes <= 20) {
            compteur.enregistrerTrajetInclus(usager, jour);
            return Montant.ZERO;
        } else if (!abonnements.estAbonne(usager) && abonnements.aUnPassActif(usager, jour) && minutes > 20) {
            compteur.enregistrerTrajetInclus(usager, jour);
            return Montant.euros(0.20);
        }
        if (minutes < 0) {
            throw new IllegalArgumentException("durée négative");
        }

        Montant prix = DEVERROUILLAGE.plus(tarifMinute(type).fois(minutes)).plafonneA(PLAFOND);

        if (abonnements.estAbonne(usager)) {
            prix = prix.moinsPourcent(20);
        }
        return prix;
    }

    private Montant tarifMinute(TypeVehicule type) {
        if (type == TypeVehicule.VELO) {
            return MINUTE_VELO;
        } else {
            return MINUTE_TROTTINETTE;
        }
    }

}
