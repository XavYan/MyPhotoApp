# MyPhotoApp

Aplicación de edición de imágenes, hecha en Java con NetBeans, que permite realizar lo siguiente:

- Convertir una imagen a color a escala de grises.
- Crear una imagen a partir de un fragmento de otra.
- Aplicar especificación de histograma a una imagen.
- Observar histograma de colores, así como el histograma acumulado.
- Observar el tipo de imagen (tiff, jpeg, png, etc), el brillo y contraste y la entropía.

**NOTA:** Estas aplicaciones, salvo convertir a escala de grises, también se pueden aplicar a imágenes en blanco y negro.

Esta aplicación también permite las siguientes operaciones, pero solo a imágenes en blanco y negro:
- Observar el valor mínimo observable en la imagen y el valor máximo en la escala de grises.
- Observar histograma e histograma acumulado de la distribución de grises en la imagen.
- Ajustar brillo y contraste.
- Equalizar histograma.
- "Restar" dos imágenes.
- Realizar un mapa de cambios.
- Realizar un efecto espejo tanto horizontal como vertical.
- Rotar 90, 180 y 270 grados.
- Rotar a cualquier ángulo, usando la interpolación de los vecinos más próximos y la interpolación bilineal.
- Escalar la imagen, usando la interpolación de los vecinos más próximos y la interpolación bilineal.
- Obtener la traspuesta de la imagen.
- Realizar ajuste lineal por tramos.
- Aplicar corrección gamma.
