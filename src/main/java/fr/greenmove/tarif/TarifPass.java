package fr.greenmove.tarif;

public class TarifPass {

    private final Abonnements abonnements;
    private final CompteurTrajets compteur;
    private final Horloge horloge;

    public TarifPass(Abonnements abonnements, CompteurTrajets compteur, Horloge horloge) {
        this.abonnements = abonnements;
        this.compteur = compteur;
        this.horloge = horloge;
    }

    public Montant prix(String usager, TypeVehicule type, int minutes) {
        if (abonnements.estAbonne(usager) || abonnements.aUnPassActif(usager, horloge.aujourdhui())) {
            return Montant.ZERO;
        }

        if(type == TypeVehicule.TROTTINETTE){
            return Montant.euros(4.00);
        }

        if (minutes == 0) {
            return Montant.euros(1.00);
        }

        return Montant.euros(3.40);
    }

}
