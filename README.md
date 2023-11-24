# Testes automatizados na prática com Spring Boot

> Curso da Udemy: Testes automatizados na prática com Spring Boot

> por Giuliana Silva Bezerra

> [link curso na Udemy](https://www.udemy.com/course/testes-automatizados-na-pratica-com-spring-boot/)

## Tecnologias

- [Java](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Testing](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testing-introduction)
- [Mysql](https://dev.mysql.com/downloads/mysql/)
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org)
- [AssertJ](https://github.com/assertj/assertj)
- [Hamcrest](http://hamcrest.org/JavaHamcrest/)
- [Jacoco](https://github.com/jacoco/jacoco)
- [Pitest](https://pitest.org)

## Tags

- Java 17 / 21
- Spring Boot
- Testes de unidade
- Testes de integração
- Testes subcutâneos
- Teste e2e
- JUnit
- AssertJ
- Dummy / Fake / Stub / Spy / Mock
- SpringBootTest
- Mockito
- DataJpaTest
- WebMvcTest / MockMvc
- Hamcrest
- TestRestTemplate
- WebTestClient
- Jacoco
- Pitest

## Material

- [Tipos de testes](https://whimsical.com/tipos-de-teste-automatizado-XkSqgpqWZwnPaF1Kgz1maB)

- [Cenário de teste criação de planeta](files/Cenarios-de-Teste-Cadastro-de-Planeta.png)

- [Dublês de teste](https://whimsical.com/dubles-de-teste-BnVqRZNUeHWpCV3FKzXsgt)

- [Cenário de teste consulta de planeta](files/Cenários-de-Teste-Consulta-de-Planeta.png)

- [Cenário de teste consulta de planeta completo](files/Cenarios-de-Teste-Consulta-de-Planeta-Completo.png)

- [Cenário de teste listagem de planetas](files/Cenarios-de-Teste-Listagem-de-Planetas.png)

- [Cenário de teste remoção de planeta](files/Cenarios-de-Teste-Remocao-de-Planeta.png)

## Configuração

O projeto requer um banco de dados Mysql, então é necessário criar uma base de dados com os seguintes comandos:

```
$ sudo mysql

CREATE USER 'user'@'%' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON *.* TO 'user'@'%' WITH GRANT OPTION;

exit

$ mysql -u user -p

CREATE DATABASE starwars;

exit
```

Durante os testes, as tabelas de banco já serão criadas automaticamente no banco de dados.

## Construir e Executar

Para construir e testar (todos os testes), execute o comando:

```sh
$ ./mvnw clean verify
```

Testar somente testes de integração

```sh
$ ./mvnw clean verify -Dsurefire.skip=true
```

Testar somente testes de unidade

```sh
$ ./mvnw clean verify -DskipITs=true
```

ou

```sh
$ ./mvnw clean test
```

Cobertura de testes

```sh
$ ./mvnw clean test jacoco:report
```

Pitest Mutation Coverage

```sh
$ ./mvnw test-compile org.pitest:pitest-maven:mutationCoverage
```
