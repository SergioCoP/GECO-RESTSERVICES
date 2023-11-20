insert into rol(name,description) values('ROLE_GERENTE','Gerente'),('ROLE_SUPERVISOR','Supervisor'),('ROLE_PERSONAL','Personal de limpieza');
insert into person(name,surname,lastname) values('Sergio','Cortes','Popoca');
<<<<<<< Updated upstream
insert into user(id_person,email,password) values(1,'sergiocortes518@gmail.com','$2a$10$KvvsiLOWOYwZQjvXZCsXU.uYkDDRS7CnPU11w.rfqjGaVOiaTmJHa');#password: admin
=======
insert into user(id_person,email,password) values(1,'sergiocortes518@gmail.com','admin');#password: $2a$10$KvvsiLOWOYwZQjvXZCsXU.uYkDDRS7CnPU11w.rfqjGaVOiaTmJHa
>>>>>>> Stashed changes
insert into user_rol(id_rol,id_user) values(1,1);