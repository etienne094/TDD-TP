package fr.greenmove.tarif;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

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
    void un_trajet_velo_ABONNE_et_sansPass_de_12_minutes_coute_2_euros_72() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return true;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return false;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        TarifPass tarifPass = new TarifPass(abonnements, mock(CompteurTrajets.class), horloge);

        Montant prix = tarifPass.prix("usager7", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(2.72), prix);
    }

    @Test
    void un_trajet_velo_ABONNE_et_sansPass_de_90_minutes_coute_12_euros() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return true;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return false;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        TarifPass tarifPass = new TarifPass(abonnements, mock(CompteurTrajets.class), horloge);

        Montant prix = tarifPass.prix("usager8", TypeVehicule.VELO, 90);

        // Then
        assertEquals(Montant.euros(12), prix);
    }

    @Test
    void un_trajet_velo_non_abonne_et_avecPass_de_12_minutes_coute_0_euro() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return false;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return true;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        CompteurTrajets compteurTrajets = mock(CompteurTrajets.class);
        when(compteurTrajets.trajetsInclus("usager9", LocalDate.of(2027, 9, 1))).thenReturn(0);

        TarifPass tarifPass = new TarifPass(abonnements, compteurTrajets, horloge);

        // When
        Montant prix = tarifPass.prix("usager9", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(0), prix);
        verify(compteurTrajets).enregistrerTrajetInclus("usager9", LocalDate.of(2027, 9, 1));
    }

    @Test
    void un_trajet_velo_non_abonne_et_avecPass_de_21_minutes_coute_0_euro_et_20() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return false;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return true;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        CompteurTrajets compteurTrajets = mock(CompteurTrajets.class);
        when(compteurTrajets.trajetsInclus("usager10", LocalDate.of(2027, 9, 1))).thenReturn(0);

        TarifPass tarifPass = new TarifPass(abonnements, compteurTrajets, horloge);

        // When
        Montant prix = tarifPass.prix("usager10", TypeVehicule.VELO, 21);

        // Then
        assertEquals(Montant.euros(0.20), prix);
        verify(compteurTrajets).enregistrerTrajetInclus("usager10", LocalDate.of(2027, 9, 1));
    }

    @Test
    void un_2e_trajet_velo_non_abonne_et_avecPass_de_30_minutes_coute_2_euros() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return false;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return true;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        CompteurTrajets compteurTrajets = mock(CompteurTrajets.class);
        when(compteurTrajets.trajetsInclus("usager11", LocalDate.of(2027, 9, 1))).thenReturn(1);

        TarifPass tarifPass = new TarifPass(abonnements, compteurTrajets, horloge);

        // When
        Montant prix = tarifPass.prix("usager11", TypeVehicule.VELO, 30);

        // Then
        assertEquals(Montant.euros(2), prix);
        verify(compteurTrajets).enregistrerTrajetInclus("usager11", LocalDate.of(2027, 9, 1));
    }

    @Test
    void un_1er_trajet_trottinette_non_abonne_et_avecPass_de_25_minutes_coute_1_euro_et_25_centimes() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return false;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return true;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        CompteurTrajets compteurTrajets = mock(CompteurTrajets.class);
        when(compteurTrajets.trajetsInclus("usager12", LocalDate.of(2027, 9, 1))).thenReturn(0);

        TarifPass tarifPass = new TarifPass(abonnements, compteurTrajets, horloge);

        // When
        Montant prix = tarifPass.prix("usager12", TypeVehicule.TROTTINETTE, 25);

        // Then
        assertEquals(Montant.euros(1.25), prix);
        verify(compteurTrajets).enregistrerTrajetInclus("usager12", LocalDate.of(2027, 9, 1));
    }

    @Test
    void un_3e_trajet_velo_non_abonne_et_avecPass_de_12_minutes_coute_3_euro_et_40_centimes() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return false;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return true;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        CompteurTrajets compteurTrajets = mock(CompteurTrajets.class);
        when(compteurTrajets.trajetsInclus("usager13", LocalDate.of(2027, 9, 1))).thenReturn(2);

        TarifPass tarifPass = new TarifPass(abonnements, compteurTrajets, horloge);

        // When
        Montant prix = tarifPass.prix("usager13", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(3.40), prix);
        verify(compteurTrajets, never()).enregistrerTrajetInclus(any(), any());

    }

    @Test
    void un_abonne_et_avecPass_paie_son_1er_trajet_en_velo_de_30_minutes_coute_1_euro_et_60_centimes() {
        // Given
        Abonnements abonnements = new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return true;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return true;
            }
        };
        Horloge horloge = () -> LocalDate.of(2027, 9, 1);

        CompteurTrajets compteurTrajets = mock(CompteurTrajets.class);
        when(compteurTrajets.trajetsInclus("usager14", LocalDate.of(2027, 9, 1))).thenReturn(0);

        TarifPass tarifPass = new TarifPass(abonnements, compteurTrajets, horloge);

        // When
        Montant prix = tarifPass.prix("usager14", TypeVehicule.VELO, 30);

        // Then
        assertEquals(Montant.euros(1.60), prix);
        verify(compteurTrajets).enregistrerTrajetInclus("usager14", LocalDate.of(2027, 9, 1));
    }

}
