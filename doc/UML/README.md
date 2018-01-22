# Lenguaje Unificado de Modelado

HearthStone Info está formado por varias clases las cuales cada una de ellas forman parte de una acción específica:

- Clases para el mapeo de JSON a Objetos Java
- Clases para el modelo de javaFX

## Mapeo de JSON a Objetos

En el atributo "cartas" que encontramos dentro de la clase "ListaDeCartas" almacenaremos todas las búsquedas que nos devuelvan varios valores como por ejemplo: búsquedas por clases, búsquedas por raza, etc.

Mediante los objetos de tipo "Carta" almacenaremos cada valor devuelto del JSON de una carta en concreto.



![Cartas Mapeo](https://github.com/Cristoto/hearthStoneInfo/blob/master/doc/UML/mapeoCartas.png)

Se sigue la misma tónica de mapeo que la clase "ListaDeCartas" y "Cartas" pero en esta situación con los "Dorsales".

![Dorsales Mapeo](https://github.com/Cristoto/hearthStoneInfo/blob/master/doc/UML/mapeoDorsales.png)

Por último para el mapeo, disponemos la clase "Info" en la cuál se almacenará toda la información sobre la API (versión, valores para las búsquedas, etc).

![Info Mapeo](https://github.com/Cristoto/hearthStoneInfo/blob/master/doc/UML/infoMapeo.png)

## Modelo para JavaFX