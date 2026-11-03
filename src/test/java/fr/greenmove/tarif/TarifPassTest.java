package fr.greenmove.tarif;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TarifPassTest {

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_12_minutes_coute_3_euros_40() {

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

        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        TarifPass tarifPass = new TarifPass(abonnements, mock(CompteurTrajets.class), horloge);

        // WHEN
        Montant prix = tarifPass.prix("usager1", TypeVehicule.VELO, 12);

        // THEN
        assertEquals(Montant.euros(3.40), prix);

    }

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_0_minutes_coute_1_euro() {

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

        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        TarifPass tarifPass = new TarifPass(abonnements, mock(CompteurTrajets.class), horloge);

        // WHEN
        Montant prix = tarifPass.prix("usager2", TypeVehicule.VELO, 0);

        // THEN
        assertEquals(Montant.euros(1), prix);

    }

    @Test
    void un_trajet_trottinette_nonAbonne_et_sansPass_de_12_minutes_coute_4_euros() {

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

        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        TarifPass tarifPass = new TarifPass(abonnements, mock(CompteurTrajets.class), horloge);

        // WHEN
        Montant prix = tarifPass.prix("usager3", TypeVehicule.TROTTINETTE, 12);

        // THEN
        assertEquals(Montant.euros(4.00), prix);

    }

}
