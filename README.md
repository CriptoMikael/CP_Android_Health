# Описание

Мобильное приложение для пациента, созданное с целью голосового ввода информации о давлении и пульсе.  
Данная система позволяет сохранять текущие результаты измеряемых значений для автоматического мониторинга.
Информация пациента сохраняется в базе данных и передаётся врачу. На её основе доктор делает медицинские
рекомендации.


<br/>

> Реализованная функциональность:

- Автономное мобильное приложение;
- Подключение к REST сервису

<br/>

> Особенность проекта:

- Голосовой ввод данных;
- Простота интеграции;
- Получение и отправка данных в разные источники;
- Рекомендательная система лекарств от врача;
- Рекомендательная система действий в приложении

<br/>

> Стек технологий

- DBMS: SQLite, PostgreSQL (production)
- Modular design with **Manifest**, simple codebase
- JavaCore

> Примечание: чтобы использовать приложение установите *.apk файл.

### Демо

Демоверсия личного кабинета расположена на AWS по адресу: http://ec2-3-15-18-180.us-east-2.compute.amazonaws.com:5000

Реквизиты тестовых пользователей:

1. Пациент

- логин: test
- пароль: test

2. Доктор

- логин: doctor
- пароль: doctor
