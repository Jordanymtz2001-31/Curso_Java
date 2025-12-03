--SENTENCIA CASE: USA UNA LISTA DE EXPRESIONES PARA MODIFICAR LA SALIDA DE UNA EXPRESION
/*
SINTAXTIS
CASE
WHEN CONDICION_1 THEN RESULTADO_1
WHEN CONDICION_2 THEN RESULTADO_2
....
ELSE 'VALOR POR DEFECTO'
*/

--CREAMOS UNA TABLA
CREATE TABLE LIBRO(
    ID_LIBRO NUMBER,
    NOMBRE NVARCHAR2(50),
    AUTOR NVARCHAR2(50),
    GENERO NUMBER, --SI ES 1:DRAMA, 2:TERROR, 3:CIENCIA FICCION, 4:NOVELA, 5:SUSPENSO
    NO_PAGINAS NUMBER,
    PRECIO NUMBER,
    EDITORIAL NVARCHAR2(100),
    CONSTRAINT LIBRO_PK PRIMARY KEY(ID_LIBRO)
); 

SELECT * FROM LIBRO;

INSERT INTO LIBRO VALUES (1, 'Cien años de soledad', 'Gabriel García Márquez', 4, 471, 350, 'Sudamericana');
INSERT INTO LIBRO VALUES (2, '1984', 'George Orwell', 3, 328, 0, NULL);
INSERT INTO LIBRO VALUES (3, 'It', 'Stephen King', 2, 1504, 450, 'Viking Press');
INSERT INTO LIBRO VALUES (4, 'Crimen y castigo', NULL, 1, 551, 320, 'Editorial Porrúa');
INSERT INTO LIBRO VALUES (5, 'La sombra del viento', 'Carlos Ruiz Zafón', 5, 487, 380, 'Planeta');
INSERT INTO LIBRO VALUES (6, 'Fahrenheit 451', 'Ray Bradbury', 3, 256, 0, 'Ballantine Books');
INSERT INTO LIBRO VALUES (7, 'Dracula', 'Bram Stoker', 2, 488, 310, 'Archibald Constable');
INSERT INTO LIBRO VALUES (8, 'El principito', 'Antoine de Saint-Exupéry', 4, 96, 200, 'Reynal and Hitchcock');
INSERT INTO LIBRO VALUES (9, 'Los pilares de la tierra', NULL, 4, 1076, 0, NULL);
INSERT INTO LIBRO VALUES (10, 'La metamorfosis', 'Franz Kafka', 1, 80, 180, 'Kurt Wolff Verlag');
INSERT INTO LIBRO VALUES (11, 'Dune', 'Frank Herbert', 3, 896, 420, 'Chilton Books');
INSERT INTO LIBRO VALUES (12, 'El resplandor', 'Stephen King', 2, 688, 390, 'Doubleday');
INSERT INTO LIBRO VALUES (13, 'Orgullo y prejuicio', 'Jane Austen', 4, 432, 0, 'T. Egerton');
INSERT INTO LIBRO VALUES (14, 'El código Da Vinci', 'Dan Brown', 5, 689, 410, 'Doubleday');
INSERT INTO LIBRO VALUES (15, 'La carretera', 'Cormac McCarthy', 3, 241, 330, 'Alfred A. Knopf');
INSERT INTO LIBRO VALUES (16, 'Hamlet', 'William Shakespeare', 1, 0, 220, NULL);
INSERT INTO LIBRO VALUES (17, 'Juego de tronos', 'George R. R. Martin', 0, 694, 480, 'Bantam Spectra');
INSERT INTO LIBRO VALUES (18, 'Misery', NULL, 2, 342, 360, 'Viking Press');
INSERT INTO LIBRO VALUES (19, 'La isla misteriosa', 'Julio Verne', 3, 622, 340, 'Pierre-Jules Hetzel');
INSERT INTO LIBRO VALUES (20, 'El nombre del viento', 'Patrick Rothfuss', 4, 662, 0, 'DAW Books');
INSERT INTO LIBRO VALUES (21, 'El hobbit', 'J.R.R. Tolkien', 4, 310, 0, NULL);
INSERT INTO LIBRO VALUES (22, 'Fundación', 'Isaac Asimov', 3, 255, 295, 'Gnome Press');
INSERT INTO LIBRO VALUES (23, 'Rebelión en la granja', NULL, 3, 140, 0, 'Secker and Warburg');
INSERT INTO LIBRO VALUES (24, 'El viejo y el mar', 'Ernest Hemingway', 1, 127, 240, 'Charles Scribner''s Sons');
INSERT INTO LIBRO VALUES (25, 'Cumbres borrascosas', 'Emily Brontë', 0, 416, 0, 'Thomas Cautley Newby');

--CONSULTAS CON CLAUSULAS JOIN
SELECT ID_LIBRO, NOMBRE, AUTOR,
( CASE GENERO
WHEN 1 THEN 'DRAMA'
WHEN 2 THEN 'TERROR'
WHEN 3 THEN 'CIENCIA FICCION'
WHEN 4 THEN 'NOVELA'
WHEN 5 THEN 'SUSPENSO'
ELSE 'SIN GENERO'
END)
AS GENERO, NO_PAGINAS, PRECIO, EDITORIAL FROM LIBRO;

--AGREGAR CONDICIONES PARA CLASIFICAR
SELECT ID_LIBRO, NOMBRE, AUTOR, GENERO, NO_PAGINAS,
(CASE
WHEN PRECIO = 0 THEN 'SIN PRECIO'
WHEN PRECIO > 400 THEN 'ES CARO: ' ||TO_CHAR(PRECIO, '9,999.0') || ' PESOS '
WHEN PRECIO <= 400 THEN 'ES BARATO: ' ||TO_CHAR(PRECIO, '9,999.0') || ' PESOS '
ELSE 'VALOR DESCONOCIDO'
END) AS PRECIO, EDITORIAL FROM LIBRO;
