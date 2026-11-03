package fr.greenmove.tarif;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_70_minutes_coute_15_euros() {

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
        Montant prix = tarifPass.prix("usager4", TypeVehicule.VELO, 70);

        // THEN
        assertEquals(Montant.euros(15), prix);

    }

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_90_minutes_plafonne_a_15_euros() {

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
        Montant prix = tarifPass.prix("usager5", TypeVehicule.VELO, 90);

        // THEN
        assertEquals(Montant.euros(15), prix);

    }

    @Test
    void une_duree_negative_est_refusee() {

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

        // WHEN / THEN
        IllegalArgumentException erreur = assertThrows(IllegalArgumentException.class,
                () -> tarifPass.prix("usager6", TypeVehicule.VELO, -1));

        assertEquals("durée négative", erreur.getMessage());

    }

    @Test
    void un_trajet_velo_ABONNE_et_sansPass_de_12_minutes_coute_2_euros_72(){
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

        Montant prix = tarifPass.prix("usager7", TypeVehicule.VELO, 12);

        //Then
        assertEquals(Montant.euros(2.72), prix);
    }

}
