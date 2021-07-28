insert into users (username, password , enabled )
    values ( 'user',
            'pass',
            true
           );

insert into users (username, password , enabled )
values ( 'admin',
         'admin',
         true
       );

insert into authorities (username, authority)
values ('user' , 'USER_ROLE') ;

insert into authorities (username, authority)
values ('admin' , 'ADMIN_ROLE') ;

select * from users;
select * from authorities;
select * from user;
select * from company;

select * from login_user;

insert  into login_user (user_name , password , roles)
    values ("user" , "user" ,"USER");

insert  into login_user (user_name , password , roles)
values ("admin" , "admin" ,"ADMIN");

UPDATE login_user
SET roles = "ROLE_ADMIN"
    WHERE user_name = "admin";
