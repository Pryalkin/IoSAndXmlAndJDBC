# IoSAndXmlAndJDBC

В ветке IoS с помощью рефлексии создаются классы. Это отдаленно передает смысл IoS. Папка factory. Ссылки на классы хранятся в файле app.properties. 
Архитектура Controller(класс main)<->Service<->Reposytory. Данные хранятся в Map. 
В этой ветке приложение можно запустить в командной строке командой java RunnerClassName.

В ветке XML таже архитектура. Но данные уже хранятся в XML файлах. В файле app.properties переменная productCreation может иметь два значения: create и update.
create пересоздает XML файлы. update работает с прежнеми. Даже если вы перекампилируете приложение, то данные не измется при переменной update. 
Приложение в консоли можно запустить командой java -jar IoSAndXmlAndJDBC-1.0-SNAPSHOT.jar. jar файл находится по пути IoSAndXmlAndJDBC/build/libs/.

В ветке JDBC модель Receipt рабита на модели ShopReceipt и ShopReceiptProductWarehouses. Так как модель Receipt содержала в себе список ProductWarehouse, 
а это не третья нормальная форма. В папке repository созданы подключения к БД. Подключения находятся в файле app.properties. 
Также созданы .xml файлы для стартового удаления или создания или заполнения таблиц. 
В файле app.properties переменная ddl-auto может иметь три аргумента: create drop complete. Приложение не доработано, так как пишу диплом.

Тестов нет, так как их еще никогда не писал.

P.S. По всем вопросам пишите в телегу @pryalkin или в linkedIn https://www.linkedin.com/in/pavel-pryalkin-330a92225/ 


