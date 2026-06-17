# Practica guiada - refactorizacion GlidedRose 
# Universidad cenfotec 

## Earl Alvarado Cabrera
## Allan Cascante Valverde
## Fabián Montero Madrigal
## Silvia Rodríguez Fernández
## Isaac Rojas Hernández

# Curso: PSWE-03 - Construcción y Mantenimiento de Software 


## Notas de refactorización: DRY, KISS, cohesión y bajo acoplamiento

Esta sección documenta los principales problemas de mantenibilidad en `GildedRose` y `Item`, y una dirección práctica para refactorizar de forma segura.

### Malas prácticas encontradas en `GildedRose`

1. **Método largo con condicionales anidados**
    - `updateQuality()` mezcla todas las reglas de los ítems en un solo método, creando anidaciones profundas de `if`.
    - Viola KISS y perjudica la legibilidad y la testabilidad.

2. **Validaciones de tipo basadas en strings (magic values)**
    - El comportamiento se selecciona con nombres hardcodeados (`"Aged Brie"`, `"Sulfuras..."`, `"Backstage..."`).
    - Esto es frágil, propenso a errores tipográficos y acopla fuertemente la lógica a valores de texto.

3. **Lógica repetida (viola DRY)**
    - El aumento o disminución de calidad y las validaciones de límites (`> 0`, `< 50`) se repiten muchas veces.
    - La lógica de casos especiales para cada ítem aparece en múltiples lugares.

4. **Baja cohesión**
    - Una sola clase maneja la orquestación, la selección de reglas y la ejecución de reglas para todos los tipos de ítems.
    - Un cambio en una regla puede generar efectos secundarios en reglas no relacionadas.

5. **Acoplamiento fuerte a los internos mutables de `Item`**
    - Acceso directo a campos (`items[i].quality`, `items[i].sellIn`, `items[i].name`) desde una clase externa.
    - No existe un límite de encapsulación que proteja invariantes.

6. **Números mágicos y reglas de negocio implícitas**
    - Valores como `50`, `11`, `6`, `0` aparecen en línea repetidamente.
    - Las reglas son más difíciles de descubrir y verificar.

### Malas prácticas encontradas en `Item`

1. **Campos públicos y mutables**
    - `name`, `sellIn` y `quality` son públicos y mutables.
    - Cualquier clase puede mutar el estado sin validación.

2. **Sin protección de invariantes**
    - `Item` no hace cumplir restricciones como límites de calidad.
    - Las reglas de dominio están dispersas fuera de la entidad.

### Dirección de refactorización (segura e incremental)

1. **Primero pruebas de caracterización**
    - Congelar el comportamiento actual con pruebas antes de cambiar el diseño. Utilizar un LLM para generar los unit tests basado en los requerimientos definidos en https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md.
    - Revisar los requerimientos por que los items Conjured no funcionan actualmente https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md Se agregan los tests para el nuevo requerimiento pero disabled hasta que el refactor este listo no se agregara la logica.
    - Utilizar estas pruebas para hacer TDD con cada cambio validando que la logica sigue intacta. 

2. **Modificar Item para exponer methods getter y setters**
    - Cambiar Item para que los atributos sean privados.
    - Agregar getter y setters segun corresponda
    - Actualizar classes dependientes para utilizar nuevos metodos
    - No es posible usar un record de java 22 pues se require modificar los valores en el proceso existente 

2. **Extraer operaciones reutilizables**
    - Introducir métodos auxiliares como `increaseQuality()`, `decreaseQuality()`, `decreaseSellIn()`.
    - Centralizar invariantes (por ejemplo, límites de calidad).
    - Nombres 'strings' como constantes
    - Constantes para increment - decrease remover metodos no requeridos

3. **Reemplazar la complejidad condicional con polimorfismo (Strategy)**
    - Crear un actualizador por tipo de ítem (por ejemplo `AgedBrieUpdater`, `BackstagePassUpdater`, `SulfurasUpdater`, `NormalItemUpdater`).
    - Crear un util para los metodos comunes de los updaters `decreaseQualityBy`, `increaseQuality`, `decreaseQuality`, `resetQuality`
    - `GildedRose` solo debe coordinar la iteración y delegar actualizaciones.

4. **Reducir el acoplamiento por strings**
    - Mapear el nombre del ítem a un objeto de regla en un solo lugar usando el patron registry y hacer los updaters static para evitar la prolifelacion de objectos cuando realmente el estado de estos no es requerido.
    - Mantener nombres desconocidos bajo una regla por defecto.
    - Encapsular los nombres canónicos para evitar strings repetidos.
    - Actualizar las pruebas para que usen los nombres registrados solamente.
    - Crear pruebas para proteger la implementacion de la clase con el registry. 

5. **Introducir jerarquía de clases con herencia**
    - Se reemplaza el patrón Strategy (`ItemUpdater` + `ItemUpdaterRegistry`) por herencia sobre `Item`.
    - `Item` se convierte en clase abstracta con método `update()` abstracto y métodos protegidos de dominio: `increaseQuality`, `decreaseQuality`, `decreaseQualityBy`, `decreaseSellIn`, `resetQuality`.
    - Los campos `name`, `sellIn` y `quality` pasan de `public` a `private`, expuestos mediante getters y setters generados por Lombok (`@Getter` `@Setter`). Las subclases acceden a ellos a través de los métodos heredados.
    - Se crean cinco subclases concretas, cada una con su lógica encapsulada:
        - `NormalItem` — quality baja 1 por día, 2 al vencer.
        - `AgedBrie` — quality sube con el tiempo, el doble al vencer.
        - `BackstagePass` — quality sube según proximidad al evento, cae a 0 al pasar.
        - `Sulfuras` — ítem legendario, no cambia con el tiempo.
        - `ConjuredItem` — quality baja al doble de velocidad que un ítem normal.
    - Los nombres canónicos de ítems especiales se mueven a la subclase correspondiente (`AgedBrie.NAME`, `BackstagePass.NAME`, `Sulfuras.NAME`) para mejorar cohesión y eliminar la clase utilitaria `ItemNames`.
    - `GildedRose.updateQuality()` se simplifica a un loop que llama `item.update()` sin necesidad de resolver un updater externo.
    - Se eliminan: `ItemUpdater`, `ItemUpdaterRegistry`, `NormalItemUpdater`, `AgedBrieUpdater`, `BackstagePassUpdater`, `SulfurasUpdater`, `ConjuredItemUpdater` y `UpdaterSupport`.
    - Los tests se actualizan para construir instancias con las subclases correspondientes en lugar de `new Item(...)`.

Diagrama de jerarquía:
```
Item (abstract)
├── NormalItem
├── AgedBrie
├── BackstagePass
├── Sulfuras
└── ConjuredItem
```
### Referencias
Tomado de https://github.com/emilybache/GildedRose-Refactoring-Kata/tree/main/Java
