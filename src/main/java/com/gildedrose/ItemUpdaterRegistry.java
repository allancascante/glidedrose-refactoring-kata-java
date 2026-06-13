package com.gildedrose;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Registro central que resuelve el updater apropiado para cada ítem.
 */
final class ItemUpdaterRegistry {
    private static final ItemUpdater NORMAL_UPDATER = new NormalItemUpdater();
    private static final ItemUpdater CONJURED_UPDATER = new ConjuredItemUpdater();
    private static final Map<String, ItemUpdater> UPDATERS_BY_NAME = createUpdatersByName();

    /**
     * Resuelve la estrategia de actualización según el nombre del ítem.
     *
     * @param item ítem para el que se necesita updater
     * @return updater correspondiente o el updater por defecto
     */
    ItemUpdater resolve(Item item) {
        if (item.name == null) {
            return NORMAL_UPDATER;
        }

        if (item.name.startsWith(ItemNames.CONJURED_PREFIX)) {
            return CONJURED_UPDATER;
        }

        ItemUpdater updater = UPDATERS_BY_NAME.get(item.name);
        if (updater != null) {
            return updater;
        }

        return NORMAL_UPDATER;
    }

    /**
     * Crea el mapa inmutable de nombres registrados a sus updaters.
     *
     * @return mapa inmutable de resolución de updaters
     */
    private static Map<String, ItemUpdater> createUpdatersByName() {
        Map<String, ItemUpdater> updaters = new HashMap<>();
        updaters.put(ItemNames.AGED_BRIE, new AgedBrieUpdater());
        updaters.put(ItemNames.BACKSTAGE_PASSES, new BackstagePassUpdater());
        updaters.put(ItemNames.SULFURAS, new SulfurasUpdater());
        return Collections.unmodifiableMap(updaters);
    }
}
