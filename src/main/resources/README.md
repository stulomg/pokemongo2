NOMBRE DEL PROYECTO: KZN STUARD LITTLE INC.

DESCRIPCIÓN:

Esta api fue desarrollada para hacer el seguimiento de la competencia entre Kusi y Stul por el jugador que hitee
el ELO más alto

Esta api para obtener la nformación necesaria usa la api pública para desarrolladores de Riot Games usando parametros
específicos definidos para el juego "League of Legends".

En el futuro está pensado optimizar el manejo de la implementación de las bases de datos en el código, junto a
actualizaciones constantes de nuevas excepciones o errores que nos vayamos topando en el camino. 

CÓMO CORRER EL PROYECTO:

Como requisito es necesario crear las bases de datos locales, para crear estas tablas en un archivo llamado crear tablas sql.txt

Esta api corre a través de endpoints que se especifican en la url, los endpoints disponibles son:
* /call-riot/{account}/{owner}:
    Este endpoint nos permite registrar las posibles cuentas para los "owners", en este caso "Kusi" y "Stul"
* /call-riot/league/{account}:
    Este endpoint permite tanto consultar la liga en la que se encuentra la cuenta que queremos buscar como registrarla
    en una base de datos para después ver el historico de las divisiones a través del tiempo.
* 

