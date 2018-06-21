# Poll Service Manager

# O que é?
O Poll Service Manager é um Gerenciador de Enquetes que possam ser acessadas pelo grande público através da internet, com acesso restrito a alguns na criação de nova enquetes e visualização de suas parciais.

# Como funciona?
## Em nível de negócio, o Poll Service Manager possui as seguintes estórias:
```
Como pessoa autorizada
Quero criar uma enquete contendo um descritivo, data inicial, data final e status
Para que todos participem com votações pela Internet
```
```
Como pessoa autorizada (dono da enquete) 
Quero criar diversas opções para cada enquete minha criada de forma livre e quando eu quiser
Para adotar dinamismo a cada enquete, com novas opções surgindo no andamento da mesma.
```
```
Como participante de uma enquete
Quero participar de qualquer enquete e visualizar o resultado final
Para que  eu veja se todos pensam como eu
```
```
Como dono da enquete
Quero ser o único a saber das parciais
Para que seja divulgado somente ao final de cada enquete
```
```
Como dono da enquete
Quero que ao final de cada enquete que eu criei, seja enviado um e-mail com os resultados
Para que eu divulgue da melhor maneira possível
```
```
Como dono da enquete
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
* [MySQL](https://www.mysql.com/downloads/) - Gerenciador de Banco de Dados MySQL

E um PC compatível para utilizar tais ferramentas.

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

insert into owner values(default, 'teste', 'poll123', 'pollservicemanager@gmail.com');

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

### O que a API me disponibiliza?
Segue a lista dos principais Serviços e exemplos de resultados:

## Criar uma Enquete:
(*) POST na URL http://localhost:8080/poll \
Exemplo de Body:
```json
{
  "description": "Copa do Mundo. Quem sera o Campeao?",
  "finalDate": "2018-07-16T23:50:29.000+0000"
}
```
Parâmetros de Header: 
Content-Type: application/json \
ownerId: Id que tenha acesso \
username: username do usuario \
password: senha do usuario 

Exemplo de Resposta: 
```json
{
    "id": 2,
    "description": "Copa do Mundo. Quem sera o Campeao?",
    "initialDate": "2018-06-19T18:45:22.094+0000",
    "finalDate": "2018-07-16T23:50:29.000+0000",
    "owner": {
        "id": 2,
        "username": "danielab"
    },
    "status": 1
}
```
-Obs.: na carga inicial, um usuario é criado e somente ele pode criar novas enquetes. Para outros usuários ter privilégios, basta adicioná-los na API de Owners (donos).

## Criar um Owner:
(*) POST na URL http://localhost:8080/owner \
Exemplo de Body:
```json
{
 "username": "pmanager",
 "password": "tst123",
 "email": "pollservicemanager@gmail.com"
 }
```
Parâmetros de Header: 
Content-Type: application/json \
ownerId: Id que tenha acesso \
username: username do usuario \
password: senha do usuario 

Exemplo de Resposta: 
```json
{
    "id": 3,
    "username": "pmanager",
    "password": "tst123",
    "email": "pollservicemanager@gmail.com"
}
```
-Obs.: deve-se respeitar ao tamanho dos campos, conforme modelo de dados, onde username = 10, password = 10 e email = 50. O id é gerado automaticamente.

Após criar uma enquete, deve-se criar alternativas para a mesma. Lembrando que apenas o dono da enquete pode criar alternativas para a enquete em questão. 
## Criar uma Alternativa:
(*) POST na URL http://localhost:8080/alternative \
Exemplo de Body:
```json
{
	"description": "CBF"
}
```
Parâmetros de Header: 
Content-Type: application/json \
ownerId: Id que tenha acesso \
username: username do usuario \
password: senha do usuario 
pollId: id da enquete que receberá a alternativa

Exemplo de Resposta: 
```json
{
    "id": 2,
    "description": "CBF",
    "poll": {
        "id": 1,
        "description": "Copa do Mundo. Quem sera o Campeao?",
        "initialDate": "2018-06-18T02:50:56.000+0000",
        "finalDate": "2018-07-16T23:50:29.000+0000",
        "status": 1
    }
}
```
Conforme sugerido no inicio, uma vez criada a enquete, diversas alternativas podem ser criadas para a mesma.

Qualquer pessoa pode votar em uma enquete via API. Para tal...
## Votar em uma enquete:
(*) PUT na URL http://localhost:8080/alternative/vote/2 \
onde 2, é a opção desejada
Exemplo de Resposta: 
```json
{
    "id": 2,
    "description": "CBF",
    "poll": {
        "id": 1,
        "description": "Copa do Mundo. Quem sera o Campeao?",
        "initialDate": "2018-06-18T02:50:56.000+0000",
        "finalDate": "2018-07-16T23:50:29.000+0000",
        "status": 1
    }
}
```
Qualquer pessoa pode ver as enquetes, mas apenas o dono da mesma pode enxergar o resultado durante as votações. 

Para ver os dados de uma enquete:
## Ver dados de uma enquete:
(*) GET na URL http://localhost:8080/summary/1 \
Onde 1 é o Id da enquete

Exemplo de Resposta: 
```json
{
    "poll": {
        "id": 1,
        "description": "Copa do Mundo. Quem sera o Campeao?",
        "initialDate": "2018-06-18T02:50:56.000+0000",
        "finalDate": "2018-06-18T03:00:00.000+0000",
        "status": 2
    },
    "alternatives": [
        {
            "id": 1,
            "description": "Brasil"
        },
        {
            "id": 2,
            "description": "CBF"
        }
    ]
}
```
Mas Conforme critérios de aceite, apenas o dono da enquete enxergará suas parciais. Para ver como anda a enquete antes do fim, ele deve fazer o seguinte:
## Ver parciais de uma enquete:
(*) GET na URL http://localhost:8080/summary/results/1 \
Onde 1 é o Id da enquete
Parâmetros de Header: 
Content-Type: application/json \
ownerId: Id que tenha acesso \
username: username do usuario \
password: senha do usuario 
Exemplo de Resposta: 
```json
{
    "poll": {
        "id": 1,
        "description": "Copa do Mundo. Quem sera o Campeao?",
        "initialDate": "2018-06-18T02:50:56.000+0000",
        "finalDate": "2018-06-18T03:00:00.000+0000",
        "status": 2
    },
    "alternatives": [
        {
            "id": 1,
            "description": "Brasil",
            "total": 0
        },
        {
            "id": 2,
            "description": "CBF",
            "total": 3
        }
    ]
}
```
O resultado é parecido com o método /summary, mas contém o campo "total", as parciais.

# E o Poll Batch nesta história?
O Poll Batch é um Micro Serviço isolado que fica verificando de tempos em tempos (inicialmente o autor deixou 5 segundos), se alguma enquete ja expirou, ou seja, se o valor da data final é menor que hoje.
Uma vez feito isto, ele envia um e-mail para o dono da enquete de acordo com o e-mail cadastrado. Além disto, o resultado é divulgado via Twitter através desta [conta](https://twitter.com/ManagerPoll).

## Por isto, além da API, se fez tão importante um módulo batch e um cadastro de owner!

## Contate o Autor:
[Marcos Cesar de Oliveira Melo](https://www.linkedin.com/in/marcoscesarmelo/)

## "Simplicidade: a arte de maximizar a quantidade de trabalho que não precisou ser feito.” (Agile Manifest)
