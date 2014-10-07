* Los pasos seguidos para realizar la tarea: Assignment-esLODCloud fueron los siguientes:
1. Búsqueda de los links activos (de los datasets españoles generados anteriormente del Assignment1) desde el siguiente enlace: http://es.dbpedia.org/Wiki.jsp?page=EsLODcloud

2. Mirar uno por uno todos los links de todas las paginas, y sus respectivos enlaces, asi como sus datos estadísticos, para añadirlos al archivo turtle.

3. Comprobar que todos los enlaces, y la información de los datasets está realmente activa.

4. Generación del SVG con parte de herramienta que se encuentra en el siguiente enlace: http://lmatteis.github.io/void-graph/5. 

5. Por último hacer un pull request para los archivos, ttl, como el archivo png (que se genera con la herramienta del paso4).
 
Información técnica: 
He seleccionado algunas etiquetas del lenguaje, no he utilizado todas, solo las que considere importantes para definir el catalogo en cuestión, alguna de ellas son las siguientes:
* dcterms:title -> nombre del dataset a representar.

* foaf:homapage -> define la página principal donde se puede acceder al conjunto de datos.

* void:subset -> conjunto de otros datos con los que se relaciona, el dataset en cuestión.

* dcterms:contributor -> se indica tanto el author como el mainteiner del dataset.

* void:triples -> indica el tamaño de datos que contiene el dataset, es un dato importante dentro de las estadísticas del mismo.
