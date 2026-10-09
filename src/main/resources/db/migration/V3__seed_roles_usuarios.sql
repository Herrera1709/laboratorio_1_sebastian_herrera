INSERT INTO rol (nombre) VALUES ('SUPER-ADMIN-ROLE');
INSERT INTO rol (nombre) VALUES ('USER');

INSERT INTO usuario (username, password) VALUES
    ('admin', '$2a$10$1km4Ey8Na2XyagBgSPspOO6R.vVv7JFKg42XDcPCUzjOmgioqRHHS'),
    ('user',  '$2a$10$q9aQBrx.mMbSKJX1dz7atu/Y6DFX/zaXfeoH46j/WYt0rwVskLXwa');

INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuario u
CROSS JOIN rol r
WHERE u.username = 'admin' AND r.nombre = 'SUPER-ADMIN-ROLE';

INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuario u
CROSS JOIN rol r
WHERE u.username = 'user' AND r.nombre = 'USER';
