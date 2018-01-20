# Diagramas de flujos de datos

En ámbito global la aplicación consta de tres diagramas de flujo de datos principales que cubren las siguientes acciones:

- Inicio de sesión.
- Registro de usuarios.
- Búsqueda de cartas.
- Guardado de datos.



## Inicio de sesión (DFD)

Al inciar la aplicación se conecta a la API y se descarga la info para una posterior comprobación.

Si es la primera vez que se inicia el programa se descargan las imágenes en local (caché), si no es la primera vez, se comprueba la info que tenemos en caché con la que descargamos de la API al iniciar el programa. Si el patch de la info  es diferente al local, se descargan las imágenes de la API y se pasa al inicio de sesión, si el patch es igual, se pasa al inicio de sesión directamente.

Se le solicita los datos al usuario, se comprueban en la base de datos. Si los datos son correctos se cargan los datos del usuario y entra en la aplicación, si no son correctos, se vuelve a solicitar el usuario y la contraseña.



![inicioSesion](https://github.com/Cristoto/hearthStoneInfo/blob/master/doc/DFD/inicioSesion.png)



## Registro de usuarios (DFD)

Se le solicitan los datos al usuario, una vez introducidos se comprueba dicho usuario en la base de datos. Si el usuario existe se le vuelve a solicitar el usuario y contraseña, si no existe, se almacenan los datos en la base de datos y se pasa a la ventana de inicio de sesión.



![registroUsuario]()

## 

## Búsqueda de cartas (DFD)

 Se recogen los filtros que el usuario ha especificado, la aplicación se conecta a la API y recoge los datos de cuyo datos coincidan con los filtros. Una vez obtenidos los datos, se obtiene de la caché la imagen que coincide con el id que nos devuelte la API y se finaliza el proceso de búsqueda.

![busquedaDeCartas](https://github.com/Cristoto/hearthStoneInfo/blob/master/doc/DFD/B%C3%BAsquedaDeCartas.png)



## Guardado de datos (DFD)

Cuando se realizan cambios en la sección de mazo o de cartas favoritas, se recoge dichas modificaciones (el id de las cartas o del mazo) y se almacenan en la base de datos.

Una vez realizado el almacenamiento, se cierra la conexión y finaliza el guardado de datos.



![guardadoDeDatos](https://github.com/Cristoto/hearthStoneInfo/blob/master/doc/DFD/guardarDatos.png)