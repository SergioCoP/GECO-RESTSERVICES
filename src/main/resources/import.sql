insert into rol(name,description) values('ROLE_GERENTE','Gerente'),('ROLE_SUPERVISOR','Supervisor'),('ROLE_PERSONAL','Personal de limpieza');
insert into person(name,surname,lastname) values('Sergio','Cortes','Popoca');
insert into user(id_person,email,password) values(1,'sergiocortes518@gmail.com','admin');#password: $2a$10$KvvsiLOWOYwZQjvXZCsXU.uYkDDRS7CnPU11w.rfqjGaVOiaTmJHa
insert into user_rol(id_rol,id_user) values(1,1);
delimiter //
create procedure registerUser(
    in Uname varchar(100),
    in Usurname varchar(100),
    in Ulastname varchar(100),
    in Uturn varchar(100),
    in Uemail varchar(20),
    in Upassword varchar(255)
)
begin
    declare idPerson int;
insert into person(name,surname , lastname, turn) values(Uname,Usurname,Ulastname,Uturn);
set idPerson = LAST_INSERT_ID();
insert into user(email,password,id_person) values(Uemail,Upassword,idPerson);
end //
delimiter