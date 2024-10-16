# UD2 - Manejo de conectores BLOQUE2
EJERCICIOS BLOQUE 2 (SQL – DML)

1.	Inserta datos en las diferentes tablas, todos los datos deben ir por parámetros y debe utilizarse el PreparedStatement para evitar SQLInjection
* Tabla departamento (el id se debe de autogenerar)
  * 1, contabilidad, PB0
  * 2, marketing, PB0,
  * 3, RRHH, PB1
* Tabla Empleado (el id se debe de autogenerar)
  *	1, Alvaro, 25/11/1979, 1, 1
  *	2, Juan, 07,07,2001,1,2
  *	3, Marta, 25/12/1997,0,3
  *	4, Silvia, 01/04/1992,0,2
2.	Recupera los datos de los empleados junto con el nombre del departamento al cual pertenecen
3.	Recupera todos los usuarios ordenados por fecha de nacimiento, de mayor a menor
4.	Edita la fecha de nacimiento del empleado con nombre Marta y establece “04/03/2002
5.	Elimina el usuario con el id 1
