package fr.greenmove.tarif;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TarifPassTest {

    private static final LocalDate JOUR = LocalDate.of(2027, 9, 1);
    private static final Horloge HORLOGE = () -> JOUR;

    private CompteurTrajets compteur;

    @BeforeEach
    void setUp() {
        compteur = mock(CompteurTrajets.class);
    }

    private Abonnements abonnements(boolean abonne, boolean passActif) {
        return new Abonnements() {
            @Override
            public boolean estAbonne(String usager) {
                return abonne;
            }

            @Override
            public boolean aUnPassActif(String usager, LocalDate jour) {
                return passActif;
            }
        };
    }

    private TarifPass tarifPour(Abonnements abonnements) {
        return new TarifPass(abonnements, compteur, HORLOGE);
    }

    // ---------- Sans Pass, non abonné ----------

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_12_minutes_coute_3_euros_40() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(false, false));

        // When
        Montant prix = tarifPass.prix("usager1", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(3.40), prix);
    }

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_0_minutes_coute_1_euro() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(false, false));

        // When
        Montant prix = tarifPass.prix("usager2", TypeVehicule.VELO, 0);

        // Then
        assertEquals(Montant.euros(1), prix);
    }

    @Test
    void un_trajet_trottinette_nonAbonne_et_sansPass_de_12_minutes_coute_4_euros() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(false, false));

        // When
        Montant prix = tarifPass.prix("usager3", TypeVehicule.TROTTINETTE, 12);

        // Then
        assertEquals(Montant.euros(4.00), prix);
    }

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_70_minutes_coute_15_euros() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(false, false));

        // When
        Montant prix = tarifPass.prix("usager4", TypeVehicule.VELO, 70);

        // Then
        assertEquals(Montant.euros(15), prix);
    }

    @Test
    void un_trajet_velo_nonAbonne_et_sansPass_de_90_minutes_plafonne_a_15_euros() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(false, false));

        // When
        Montant prix = tarifPass.prix("usager5", TypeVehicule.VELO, 90);

        // Then
        assertEquals(Montant.euros(15), prix);
    }

    // ---------- Erreur ----------

    @Test
    void une_duree_negative_est_refusee() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(false, false));

        // When / Then
        IllegalArgumentException erreur = assertThrows(IllegalArgumentException.class,
                () -> tarifPass.prix("usager6", TypeVehicule.VELO, -1));

        assertEquals("durée négative", erreur.getMessage());
    }

    // ---------- Abonné GreenMove ----------

    @Test
    void un_trajet_velo_ABONNE_et_sansPass_de_12_minutes_coute_2_euros_72() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(true, false));

        // When
        Montant prix = tarifPass.prix("usager7", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(2.72), prix);
    }

    @Test
    void un_trajet_velo_ABONNE_et_sansPass_de_90_minutes_coute_12_euros() {
        // Given
        TarifPass tarifPass = tarifPour(abonnements(true, false));

        // When
        Montant prix = tarifPass.prix("usager8", TypeVehicule.VELO, 90);

        // Then
        assertEquals(Montant.euros(12), prix);
    }

    // ---------- Pass, trajet inclus ----------

    @Test
    void un_trajet_velo_non_abonne_et_avecPass_de_12_minutes_coute_0_euro() {
        // Given
        when(compteur.trajetsInclus("usager9", JOUR)).thenReturn(0);
        TarifPass tarifPass = tarifPour(abonnements(false, true));

        // When
        Montant prix = tarifPass.prix("usager9", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(0), prix);
        verify(compteur).enregistrerTrajetInclus("usager9", JOUR);
    }

    @Test
    void un_trajet_velo_non_abonne_et_avecPass_de_21_minutes_coute_0_euro_et_20() {
        // Given
        when(compteur.trajetsInclus("usager10", JOUR)).thenReturn(0);
        TarifPass tarifPass = tarifPour(abonnements(false, true));

        // When
        Montant prix = tarifPass.prix("usager10", TypeVehicule.VELO, 21);

        // Then
        assertEquals(Montant.euros(0.20), prix);
        verify(compteur).enregistrerTrajetInclus("usager10", JOUR);
    }

    @Test
    void un_2e_trajet_velo_non_abonne_et_avecPass_de_30_minutes_coute_2_euros() {
        // Given
        when(compteur.trajetsInclus("usager11", JOUR)).thenReturn(1);
        TarifPass tarifPass = tarifPour(abonnements(false, true));

        // When
        Montant prix = tarifPass.prix("usager11", TypeVehicule.VELO, 30);

        // Then
        assertEquals(Montant.euros(2), prix);
        verify(compteur).enregistrerTrajetInclus("usager11", JOUR);
    }

    @Test
    void un_1er_trajet_trottinette_non_abonne_et_avecPass_de_25_minutes_coute_1_euro_et_25_centimes() {
        // Given
        when(compteur.trajetsInclus("usager12", JOUR)).thenReturn(0);
        TarifPass tarifPass = tarifPour(abonnements(false, true));

        // When
        Montant prix = tarifPass.prix("usager12", TypeVehicule.TROTTINETTE, 25);

        // Then
        assertEquals(Montant.euros(1.25), prix);
        verify(compteur).enregistrerTrajetInclus("usager12", JOUR);
    }


    // ---------- Pass, trajet non inclus ----------

    @Test
    void un_3e_trajet_velo_non_abonne_et_avecPass_de_12_minutes_coute_3_euro_et_40_centimes() {
        // Given
        when(compteur.trajetsInclus("usager13", JOUR)).thenReturn(2);
        TarifPass tarifPass = tarifPour(abonnements(false, true));

        // When
        Montant prix = tarifPass.prix("usager13", TypeVehicule.VELO, 12);

        // Then
        assertEquals(Montant.euros(3.40), prix);
        verify(compteur, never()).enregistrerTrajetInclus(any(), any());
    }

    // ---------- Combinaisons ----------

    @Test
    void un_abonne_et_avecPass_paie_son_1er_trajet_en_velo_de_30_minutes_coute_1_euro_et_60_centimes() {
        // Given
        when(compteur.trajetsInclus("usager14", JOUR)).thenReturn(0);
        TarifPass tarifPass = tarifPour(abonnements(true, true));

        // When
        Montant prix = tarifPass.prix("usager14", TypeVehicule.VELO, 30);

        // Then
        assertEquals(Montant.euros(1.60), prix);
        verify(compteur).enregistrerTrajetInclus("usager14", JOUR);
    }

    @Test
    void un_1er_trajet_velo_non_abonne_et_avecPass_de_120_minutes_plafonne_a_15_euros() {
        // Given
        when(compteur.trajetsInclus("usager15", JOUR)).thenReturn(0);
        TarifPass tarifPass = tarifPour(abonnements(false, true));

        // When
        Montant prix = tarifPass.prix("usager15", TypeVehicule.VELO, 120);

        // Then
        assertEquals(Montant.euros(15), prix);
        verify(compteur).enregistrerTrajetInclus("usager15", JOUR);
    }
}