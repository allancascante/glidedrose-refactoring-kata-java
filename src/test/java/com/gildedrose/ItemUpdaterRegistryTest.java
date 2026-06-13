package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Pruebas unitarias para proteger la resolución del registro de updaters.
 */
class ItemUpdaterRegistryTest {

    private final ItemUpdaterRegistry registry = new ItemUpdaterRegistry();

    /**
     * Verifica que "Aged Brie" se resuelva al updater correcto.
     */
    @Test
    void resolve_agedBrie_usesAgedBrieUpdater() {
        ItemUpdater updater = registry.resolve(new Item(ItemNames.AGED_BRIE, 1, 1));

        assertInstanceOf(AgedBrieUpdater.class, updater);
    }

    /**
     * Verifica que "Backstage passes" se resuelva al updater correcto.
     */
    @Test
    void resolve_backstagePasses_usesBackstagePassUpdater() {
        ItemUpdater updater = registry.resolve(new Item(ItemNames.BACKSTAGE_PASSES, 1, 1));

        assertInstanceOf(BackstagePassUpdater.class, updater);
    }

    /**
     * Verifica que "Sulfuras" se resuelva al updater correcto.
     */
    @Test
    void resolve_sulfuras_usesSulfurasUpdater() {
        ItemUpdater updater = registry.resolve(new Item(ItemNames.SULFURAS, 0, 80));

        assertInstanceOf(SulfurasUpdater.class, updater);
    }

    /**
     * Verifica que cualquier nombre con prefijo "Conjured" use su updater.
     */
    @Test
    void resolve_conjuredPrefix_usesConjuredItemUpdater() {
        ItemUpdater updater = registry.resolve(new Item(ItemNames.CONJURED_PREFIX + " Mana Cake", 3, 6));

        assertInstanceOf(ConjuredItemUpdater.class, updater);
    }

    /**
     * Verifica que un nombre no registrado use el updater por defecto.
     */
    @Test
    void resolve_unknownName_usesNormalItemUpdater() {
        ItemUpdater updater = registry.resolve(new Item("Unknown Item", 3, 6));

        assertInstanceOf(NormalItemUpdater.class, updater);
    }

    /**
     * Verifica que un nombre nulo use el updater por defecto.
     */
    @Test
    void resolve_nullName_usesNormalItemUpdater() {
        ItemUpdater updater = registry.resolve(new Item(null, 3, 6));

        assertInstanceOf(NormalItemUpdater.class, updater);
    }

    /**
     * Verifica reutilización de instancia para updaters registrados.
     */
    @Test
    void resolve_reusesSameRegisteredUpdaterInstance() {
        ItemUpdater first = registry.resolve(new Item(ItemNames.AGED_BRIE, 2, 10));
        ItemUpdater second = registry.resolve(new Item(ItemNames.AGED_BRIE, 0, 20));

        assertSame(first, second);
    }

    /**
     * Verifica reutilización de instancia para el updater por defecto.
     */
    @Test
    void resolve_reusesSameDefaultUpdaterInstance() {
        ItemUpdater first = registry.resolve(new Item("foo", 2, 10));
        ItemUpdater second = registry.resolve(new Item("bar", 0, 20));

        assertSame(first, second);
    }
}
