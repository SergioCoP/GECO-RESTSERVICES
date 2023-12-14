insert into rol(name,description) values('ROLE_GERENTE','Gerente'),('ROLE_SUPERVISOR','Recepcionista'),('ROLE_PERSONAL','Personal de limpieza');
insert into person(name,surname,lastname) values('Sergio','Cortes','Popoca');
insert into user(id_person,email,password) values(1,'sergiocortes518@gmail.com','$2a$10$DsLoOlL0q4Swr9eNz20HeuaDTbraVfe90OKsGjJir/t7Dpuh55sZ2');#password: #Serch1234
insert into user_rol(id_rol,id_user) values(1,1);