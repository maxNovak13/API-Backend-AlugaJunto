create table vaga(
                     id serial not null primary key,
                     uuid UUID DEFAULT gen_random_uuid(),
                     titulo varchar(80) not null,
                     descricao varchar(200) not null,
                     area int not null,
                     dormitorio int not null,
                     banheiro int not null,
                     garagem int not null,
                     imagem varchar(1000) not null,
                     genero varchar(20) not null,
                     idade varchar(10) not null,
                     pet boolean not null,
                     fumante boolean not null,
                     texto varchar(500) not null,
                     rua varchar(80) not null,
                     bairro varchar(80),
                     cep varchar(9) not null,
                     estado char(2) not null,
                     cidade varchar(100) not null
)