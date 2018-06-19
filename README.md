# Poll Service Manager

# O que é?
O Poll Service Manager é um Gerenciador de Enquetes que possam ser acessadas pelo grande público através da internet, com acesso restrito a alguns na criação de nova enquetes e visualização de suas parciais.

# Como funciona?
## Em nível de negócio, o Poll Service Manager possui as seguintes estórias:
```
Como pessoa autorizada, 
Quero criar uma enquete contendo um descritivo, data inicial, data final e status
Para que todos participem com votações pela Internet
```
```
Como pessoa autorizada (dono da enquete) 
Quero criar diversas opções para cada enquete minha criada de forma livre e quando eu quiser
Para adotar dinamismo a cada enquete, com novas opções surgindo no andamento da mesma.
```
```
Como participante de uma enquete, 
Quero participar de qualquer enquete e visualizar o resultado final
Para que  eu veja se todos pensam como eu
```
```
Como dono da enquete, 
Quero ser o único a saber das parciais
Para que seja divulgado somente ao final de cada enquete
```
```
Como dono da enquete, 
Quero que ao final de cada enquete que eu criei, seja enviado um e-mail com os resultados
Para que eu divulgue da melhor maneira possível
```
```
Como dono da enquete, 
Quero que ao final de cada enquete que eu criei, seja postado no Twitter oficial do Poll Service Manager os resultados
Para que se tenha um canal padrão de divulgação das enquetes
```

# Como foi feito?
O Poll Service Manager possui um API Restful, elaborada utilizando Java 8 e Spring Boot + Spring Data, além de um BD MySQL.
Além da API, também há um outro micro serviço, que utiliza as mesmas tecnologia, mas se faz uso de Bibliotecas de Scheduling, para de tempos em tempos, processar enquetes finalizadas.

### O que eu preciso para executar localmente em minha estação de trabalho?
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) Java JDK 
* [Spring](http://spring.io/) - Framework utilizado (Spring Boot + Spring Data)
* [Maven](https://maven.apache.org/) - Gereciados de Dependências
* [Postman](https://www.getpostman.com/) - Um client de chamadas Restful, por exemplo, o Postman
* [MySQL](https://www.mysql.com/downloads/) - Gerenciados de Banco de Dados MySQL

Além de um PC, é claro!

What things you need to install the software and how to install them


## Instruções para uso local:

1. Com o MySQL instalado, aplique o script que está em:
https://github.com/marcoscesarmelo/pollservicemanager/blob/master/poll/src/main/resources/poll-db.sql
em um banco de dados chamado polldb. Crie o mesmo com login root e senha 1234, ou altere estas propriedades no arquivo application.properties da aplicação.
Veja o Script:
```SQL
create database polldb;

use polldb;

create table owner (
	id int not null primary key auto_increment,
	username varchar(10) not null,
	password varchar(10) not null, 
	email varchar(50) not null
) ENGINE=INNODB;

create table poll (
	id int not null primary key auto_increment,
	description varchar(200) not null,
	initial_date datetime not null,
	final_date datetime not null,
	status int not null,
	owner_id int not null,
	foreign key (owner_id)
	references owner(id)
	on delete cascade
) ENGINE=INNODB;

create table alternative (
	id int not null primary key auto_increment,
	description varchar(50) not null,
	total int not null,
	poll_id int not null,
	foreign key (poll_id)
	references poll(id)
	on delete cascade
) ENGINE=INNODB;

insert into owner values(default, 'pollservicemanager', 'poll123', 'pollservicemanager@gmail.com');

commit;
```
Ao término desta etapa, o banco de dados já estará OK para uso!.

2. Utilize uma IDE de sua preferência, ou mesmo o maven para compilar os projeto 2 projetos
- Poll 
- Poll Batch

Para cada um dos dois projetos, navegue até a pasta raíz do mesmo e execute comando:

```DOS
java -jar poll-0.0.1-SNAPSHOT.jar 
```
```DOS
java -jar poll-batch-0.0.1-SNAPSHOT.jar
```
3. Feito isto, os serviços estarão rodando. Agora basta acessar ao Postman e criar as chamadas para a API

## O que a API me disponibiliza?
Segue a lista de Serviços e seus resultados:

## Quem é o autor?
[Marcos Cesar de Oliveira Melo](https://www.linkedin.com/in/marcoscesarmelo/)




