package fr.greenmove.tarif;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class TarifPassTest {

    @Test
    void une_trottinette_de_12_min_sans_pass_ni_abonnement_coute_4_euros()
    {
        Abonnements sansRien = new Abonnements()
        {
            public boolean estAbonne(String usager)
            {
                return false;
            }
            public boolean aUnPassActif(String usager, LocalDate jour)
            {
                return false;
            }
        };

        Horloge horloge = () -> LocalDate.of(2027, 9, 1);
        TarifPass tarif = new TarifPass(sansRien, mock(CompteurTrajets.class), horloge);

        Montant prix = tarif.prix("Etienne", TypeVehicule.TROTTINETTE, 12);

        assertEquals(Montant.euros(4.00), prix);
    }
}