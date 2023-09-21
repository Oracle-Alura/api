ALTER TABLE medicos ADD activo tinyint;
UPDATE medicos SET activo = 1; -- Para actualizar los datos anteriores de la BD