ALTER TABLE pacientes ADD activo tinyint;
UPDATE pacientes SET activo = 1; -- Para actualizar los datos anteriores de la BD