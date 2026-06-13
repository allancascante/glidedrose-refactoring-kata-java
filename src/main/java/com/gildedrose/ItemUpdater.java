package com.gildedrose;

/**
 * Define la estrategia de actualización para un tipo de ítem.
 */
interface ItemUpdater {
    /**
     * Aplica la regla de negocio diaria sobre un ítem.
     *
     * @param item ítem a actualizar
     */
    void update(Item item);
}
