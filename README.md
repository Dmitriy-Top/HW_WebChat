# HW_WebChat
<p>Требования:</p>
<ul>
<li>sdk 8</li>
<li>jdk 8</li>
<li>wildfly 10</li>
<li>postgres 9.6</li>
</ul>
<br>
<p>Порядок развертывания:</p>
<ol>
<li>Установить postgresSQL 9.6</li>
<li>Создать, либо использовать скрипт создания (src/main/start/create script.txt) базу данных "chat" </li>
<li>Ипортировать содержимое БД из дампа БД src/main/start/db</li>
<li>Установить wildfly 10</li>
<li>Настроить DataSource в wildfly, jndi - "java:/PostgresDS"</li>
<li>Клонировать репозиторий https://github.com/Dmitriy-Top/HW_WebChat</li>
<li>Настроить переменную Source Folder HW_WebChat/src</li>
<li>Скомплировать и собрать проект из исходников. Версия компилятора 1.8, версия maven 3</li>
<li>Запустить wildfly и задеплоить полученный war-файл</li>
</ol>
