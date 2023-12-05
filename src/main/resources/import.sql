insert into rol(name,description) values('ROLE_GERENTE','Gerente'),('ROLE_SUPERVISOR','Recepcionista'),('ROLE_PERSONAL','Personal de limpieza');
insert into person(name,surname,lastname) values('Sergio','Cortes','Popoca');
insert into user(id_person,email,password) values(1,'sergiocortes518@gmail.com','admin');#password: $2a$10$KvvsiLOWOYwZQjvXZCsXU.uYkDDRS7CnPU11w.rfqjGaVOiaTmJHa
insert into user_rol(id_rol,id_user) values(1,1);

-- CREATE PROCEDURE registerUser(
--     IN Uname VARCHAR(100),
--     IN Usurname VARCHAR(100),
--     IN Ulastname VARCHAR(100),
--     IN Uturn VARCHAR(100),
--     IN Uemail VARCHAR(20),
--     IN Upassword VARCHAR(255)
-- )
-- BEGIN
--     DECLARE idPerson INT;
-- INSERT INTO person(name, surname, lastname, turn) VALUES (Uname, Usurname, Ulastname, Uturn);
-- SET idPerson = LAST_INSERT_ID();
-- INSERT INTO user(email, password, id_person) VALUES (Uemail, Upassword, idPerson);
-- END;