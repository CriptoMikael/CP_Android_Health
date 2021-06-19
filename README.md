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

При запуске приложения откроется окно ввода данных. Нажмите кнопку "Записать данные при помощи голоса". 
Продиктуйте предложение, в котором прозвучит примерно следующие фразы: 
либо "Давление 120 на 80. Пульс 64. Поднялся на пятый этаж", либо "Давление 120 на 70". "Пульс 71". "Вернулся с прогулки".