Dominium
========

Eine Buchführung für private Haushalte.


Anforderungen
-------------

Loslegen
--------

Frontend

Das Frontend kann mit dem Befehl npm run start oder der Run-Konfiguration start
ausgeführt werden, um manuelle Tests auszuführen. Das Frontend ist dann im 
Browser unter der URL http://localhost:4300 erreichbar

Unittests

Zur Ausführung von Unittests mit Karma dient die Run-Konfiguration Karma Test.

End to end Tests



Test
----

Die Tests benötigen Docker. Für die Installation auf Debian siehe
 https://docs.docker.com/engine/installation/linux/debian/#debian-jessie-80-64-bit
http://blog.arungupta.me/wildfly-admin-console-docker-image-techtip66/

Haushaltsbuch
=============

Eine Buchhaltung für den Privathaushalt.

1. [Anleitung für das Schreiben von Funktionalitäten und Szenarien](
domaene/src/test/resources/de/therapeutenkiller/haushaltsbuch/domaene/Anleitung.md)
2. [Funktionalitäten](domaene/src/test/resources/README.md)
2. [Buchführungsregeln](domaene/src/test/resources/de/therapeutenkiller/haushaltsbuch/domaene/Buchführungsregeln.md)
3. [Coding-Style - nicht aktuell](Codestyle.md)
4. [Build-Prozess](buildprozess.md)

Getestet unter
--------------
* Wildfly 10.0.0.Final
* Maven 3.3.9
* Java 8 (OpenJDK 1.8.0_66)
* MariaDB Server Version 10.0.23, Client Version 1.3.7

