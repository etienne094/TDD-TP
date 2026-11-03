package fr.greenmove.tarif;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TarifPassTest {

    @Test
    void un_trajet_nonAbonne_et_sansPass_de_12_minutes_coute_3_euros_40() {

        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return false;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return false;
            }
        };

        CompteurTrajets compteur = new CompteurTrajetsMemoire();
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        TarifPass tarifPass = new TarifPass(abonnements, compteur, horloge);

        // WHEN
        Montant prix = tarifPass.prix("usager1", TypeVehicule.VELO, 12);

        // THEN
        assertEquals(Montant.euros(3.40), prix);

    }
}
