[![Codacy Badge](https://app.codacy.com/project/badge/Grade/bee16f3145654047a0505c62aeefd8a2)](https://www.codacy.com/gh/JavaWebinar/topjava/dashboard)

Java Enterprise Online Project
===============================

[Демо разрабатываемого приложения](https://jsft.ru/topjava/)

[Выпускной проект](https://github.com/sukhinlv/voteforlunch)

#### Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css, js), DataTables, jQuery + plugins, Java 8 Stream and Time API и сохранением в базах данных PostgreSQL и HSQLDB.

- Основное внимание будет уделяться способам решения многочисленных проблем разработки в Spring/JPA, а также структурному (красивому и надежному) java-кодированию и архитектуре приложения.
- Каждая итерация проекта закрепляется домашним заданием по реализации схожей функциональности. Следующее занятие начинается с разбора домашних заданий.
- Большое внимание уделяется тестированию кода: в проекте более 100 JUnit-тестов.
- Несмотря на относительно небольшой размер, приложение разрабатывается с нуля как большой проект: например, используем кэш 2-го уровня Hibernate, настраиваем Jackson для работы с ленивой загрузкой Hibernate, делаем конвертеры для типов LocalDateTime (Java 8 time API).
- Разбираются архитектурные паттерны: слои приложения и как правильно разбивать логику по слоям, когда нужно применять Data Transfer Object. То есть на выходе получается не учебный проект, а хорошо масштабируемый шаблон для большого проекта на всех пройденных технологиях.
- Большое внимание уделяется деталям: популяция базы данных, использование транзакционности, тесты сервисов и REST-контроллеров, настройка EntityManagerFactory, выбор реализации пула коннектов. Особое внимание уделяется работе с базой данных: через Spring JDBC, Spring ORM и Spring Data Jpa.
- Используются самые востребованные на сегодняшний момент фреймворки: Maven, Spring Security 4 вместе с Spring Security Test, наиболее удобный для работы с базой проекта Spring Data Jpa, библиотека логирования Logback, реализующая SLF4J, повсеместно используемый Bootstrap и jQuery.

### План проекта:
#### Архитектура проекта. Персистентность.
- Системы управления версиями
- Java 8: Lambda, Stream API
- Обзор используемых в проекте технологий и инструментов.
- Инструмент сборки Maven
- WAR. Веб-контейнер Tomcat. Сервлеты.
- Логирование.
- Обзор стандартных библиотек. Apache Commons, Guava
- Слои приложения. Создание каркаса приложения.
- Обзор Spring Framework. Spring Context.
- Тестирование через JUnit.
- Spring Test
- Базы данных. PostgreSQL. Обзор NoSQL и Java persistence solution без ORM.
- Настройка Database в IDEA.
- Скрипты инициализации базы. Spring Jdbc Template.
- Spring: инициализация и популирование БД
- ORM. Hibernate. JPA.
- Тестирование JPA-сервиса через AssertJ
- Поддержка HSQLDB
- Транзакции
- Профили Maven и Spring
- Пул коннектов
- Spring Data JPA
- Кэш Hibernate

#### Разработка WEB
- Spring кэш
- Spring Web
- JSP, JSTL, i18n
- Tomcat maven plugin. JNDI
- Spring Web MVC
- Spring Internationalization
- Тестирование Spring MVC
- REST-контроллеры
- Тестирование REST-контроллеров. Jackson.
- jackson-datatype-hibernate. Тестирование через матчеры.
- Тестирование через SoapUi. UTF-8
- WebJars.
- Bootstrap. jQuery datatables.
- AJAX. jQuery. Notifications.
- Spring Security
- Spring Binding/Validation
- Работа с DataTables через Ajax.
- Spring Security Test
- Кастомизация JSON (@JsonView) и валидации (groups)
- Encoding password
- CSRF (добавление в проект защиты от межсайтовой подделки запроса)
- form-login. Spring Security Taglib
- Handler interceptor
- Spring Exception Handling
- Смена локали
- Фильтрация JSON с помощью @JsonView
- Защита от XSS (Cross Site Scripting)
- Деплой на собственный выделенный сервер
- Локализация datatables, ошибок валидации
- Обработка ошибок 404 (NotFound)
- Доступ к AuthorizedUser


### План по веткам Git
####  HW0 - вступительное задание
####  HW1
- Обзор используемых в проекте технологий. Интеграция ПО
- Maven
- WAR. Веб-контейнер Tomcat. Сервлеты
- Логирование
- Уровни и зависимости логгирования. JMX

#### HW2
- Библиотека vs Фреймворк. Стандартные библиотеки Apache Commons, Guava
- Слои приложения. Создание каркаса приложения
- Обзор Spring Framework. Spring Context
- Пояснения к HW2. Обработка Autowired

#### HW3
- Жизненный цикл Spring контекста
- Тестирование через JUnit
- Spring Test
- Базы данных. Обзор NoSQL и Java persistence solution без ORM
- Установка PostgreSQL. Docker
- Настройка Database в IDEA
- Скрипты инициализации базы. Spring Jdbc Template
- Тестирование UserService через AssertJ
- Логирование тестов

#### HW4
- Методы улучшения качества кода
- Spring: инициализация и популирование DB
- Подмена контекста при тестировании
- ORM. Hibernate. JPA
- Поддержка HSQLDB

#### HW5
- Обзор JDK 9/17. Миграция Topjava с 1.8 на 17
- Разбор вопросов
- Разбор домашнего задания HW4 + Optional
- Транзакции
- Профили Maven и Spring
- Пул коннектов
- Spring Data JPA
- Spring кэш

#### HW6
- Кэш Hibernate
- Spring Web
- JSP, JSTL, internationalization
- Динамическое изменение профиля при запуске
- Конфигурирование Tomcat через maven plugin. Jndi-lookup
- Spring Web MVC
- Spring Internationalization

#### HW7
- Автогенерация DDL по модели
- Тестирование Spring MVC
- Миграция на JUnit 5
- Принципы REST. REST контроллеры
- Тестирование REST контроллеров. Jackson
- jackson-datatype-hibernate. Тестирование через матчеры
- Тестирование через SoapUi. UTF-8

#### HW8
- WebJars. jQuery и JavaScript frameworks
- Bootstrap
- AJAX. Datatables. jQuery
- jQuery notifications plugin
- Добавление Spring Security

#### HW9
- Spring Binding
- Spring Validation
- Перевод DataTables на Ajax
- Форма login / logout
- Реализация собственного провайдера авторизации
- Принцип работы Spring Security. Проксирование
- Spring Security Test
- Cookie. Session

#### HW10
- Кастомизация JSON (@JsonView) и валидации (groups)
- Рефакторинг: jQuery конверторы и группы валидации по умолчанию
- Spring Security Taglib. Method Security Expressions
- Интерцепторы. Редактирование профиля. JSP tag files
- Форма регистрации
- Обработка исключений в Spring
- Encoding password
- Миграция на Spring 5
- Защита от межсайтовой подделки запросов (CSRF)

#### HW11
- Локализация datatables, ошибок валидации
- Защита от XSS (Cross Site Scripting)
- Обработка ошибок 404 (NotFound)
- Доступ к AuthorizedUser
- Ограничение модификации пользователей
- Деплой 
