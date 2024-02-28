### Actividad 3.1 Hipotecas

En esta actividad vamos a crear una aplicación sencilla que permite realizar simulaciones de hipotecas.

Con funcionalidad similar (aunque ampliada) a la de la página web:

[Simulador de hipotecas](https://www.abanfin.com/?tit=simulador-de-hipotecas-cuota-y-cuadro-de-amortizacion-&name=Simuladores&fid=cf0bcar)

#### Requerimientos funcionales:
- La aplicación debe permitir calcular la cuota que se pagará mensualmente a partir de un capital, un interés y un plazo dados.
- La aplicación permitirá visualizar el cuadro de amortización si el usuario así lo solicita.
- La aplicación debe permitir a un usuario registrarse en la aplicación y obtener así un usuario y una contraseña asociados a él que le permitirán estar identificado en la aplicación.
- Un usuario identificado debe poder ver las simulaciones de hipotecas que se han realizado en la aplicación junto con la fecha y hora en que las hizo. Estas se almacenarán en una tabla de la base de datos de la aplicación.
- Si un usuario identificado lo desea, debe poder volver a simular una hipoteca sin necesidad de introducir los datos, simplemente seleccionando la simulación entre las que ya ha realizado.
- Se deben registrar los errores como errores en un archivo llamado AppHipoteques.txt.
- Se debe registrar como debug en un archivo llamado AppHipoteques.txt todas las simulaciones que se realicen tanto de un usuario identificado como si no. Debe incluir la fecha, hora, IP, nombre de usuario y los datos de la simulación de la hipoteca (importe, interés y meses).

#### Requerimientos técnicos:
- La aplicación debe estar desarrollada en Java y Servlets.
- Puede haber archivos HTML, CSS, imágenes, vídeos, sonidos y JavaScript de apoyo, pero no para resolver completamente la funcionalidad solicitada.
- El código debe estar comentado.
- Las clases, propiedades y métodos utilizados deben tener su comentario javadoc adecuado.
- El código debe estar organizado y debe ser fácil de entender.
- Se valorará negativamente el uso de escritura en consola (System.out...) y las importaciones no utilizadas.
- Debe usarse un patrón MVC.
- Se debe realizar un registro adecuado, no se puede emplear System.out...
- Todo el código debe estar codificado en UTF-8.
- Se valorará negativamente que haya código comentado en cualquier parte de la aplicación.
- Se deben eliminar del servidor Tomcat las aplicaciones desplegadas por defecto.
- Debes desplegar la aplicación creada en la raíz de las URL del servidor Tomcat.
- El servidor Tomcat debe estar configurado con SSL.

Página 1 de 3

#### Cálculo de la cuota mensual:

Cuota = \( \frac{P}{1 - (1+i)^{-n}} \)

Donde:
- \( P \): Importe del préstamo hipotecario.
- \( i \): Tipo de interés anual/12. Se divide entre doce porque se paga la cuota de la hipoteca mensualmente.
- \( n \): Número de meses durante los cuales se paga el préstamo hipotecario.

Puedes ayudarte de la hoja de cálculo adjunta para entender cómo hacer los cálculos y el cuadro de amortización.

Se valorará:
1. (4 puntos) La funcionalidad de la aplicación.
2. (2 puntos) El código de la aplicación.
3. (2 puntos) El diseño de la aplicación.
4. (2 puntos) La configuración del servidor Tomcat y el despliegue de la aplicación.

La nota de cada apartado se establecerá según la siguiente tabla:

| Apartado | No realizado | Pobre | Suficiente | Adecuado | Excelente |
|----------|---------------|-------|------------|----------|-----------|
| 1        | 0 puntos      | 1.25  | 2.5        | 3.75     | 5         |
| 2        | 0 puntos      | 0.75  | 1          | 1.5      | 2         |
| 3        | 0 puntos      | 0.5   | 1          | 1.5      | 2         |
| 4        | 0 puntos      | 0.5   | 1          | 1.5      | 2         |

La valoración de la puntuación irá desde un 0 si no se realiza hasta la nota máxima del apartado según la interpretación que haga el profesor del resultado presentado.

Si el script de creación de la base de datos no funciona y no se puede probar la aplicación, esta constará como no realizada. Si la aplicación no compila constará como no realizada.
