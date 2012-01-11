Projekt Twitchess
=================

Version
-------
* Version 1.0

Autoren
------- 
* Benjamin
* Curtis
* Marco
* Sebastian

Kurzbeschreibung
================
Twitchess ist eine Konsolenanwendung, welche es ermöglicht, über Twitter gegen einen Schachmotor zu spielen.

Verwendete APIs
---------------
* [Schachmotor](https://github.com/mcostalba/Stockfish)
* [SQLite-DB](http://www.zentus.com/sqlitejdbc)
* [Twitter4j](http://twitter4j.org)

Anforderungen
-------------

Mindestanforderungen des Projekts:

* Spielen gegen KI (Anbindung einer Schachengine) `erfüllt`
  
* Anbindung an Twitter (Steuerung über Nachrichten) `erfüllt`
  
* Aktuelle Status-/Spielfeldanzeige durch Grafiken (über Twitter) `erfüllt`
  
* Speicherung laufender und vergangener Spiele/Spieler `erfüllt`
  

Zusatzanforderungen:

* Änderung des Schwierigkeitsgrades (`erfüllt` -> Änderung von Engine.TimePerMove in configuration.properties)
  
* Eigene KI (`nicht erfüllt`, sehr komplex)
  
* Spieler gegen Spieler (`nicht erfüllt`, aber einfach implementierbar)
  
* GUI (`nicht erfüllt`, bringt keinen Mehrwert)
  
* Andere Spiele wie TicTacToe oder Vier Gewinnt (`nicht erfüllt`, aber einfach hinzufügbar durch Änderungen in 
ManagerFactory und das Anlegen eines neuen Managers)
    

Erzeugung einer ausführbaren Jar
--------------------------------
Nutzen Sie dazu das Ant Build mit folgendem Befehl:

    ant jar
    
### Ausführen in der Konsole:

Zum Ausführen von Twitchess bietet sich zwei Wege an. Zum einen haben wir für Windows und Unix Systeme fertige 
Scripte bereits angelegt, die das Starten vereinfachen. Falls ein solches System nicht zur Verfügung steht oder es 
Probleme gibt, genügt auch das Ausführen folgenden Befehls:

    java -jar jar/twitchess.jar
    
("img/*", "chessengines/*", "configuration.properties" sowie die alternativen Scripte müssen sich im gleichen Ordner befinden)

Ausführliche Dokumentation
==========================
Um näheren Einblick zu erlangen bedienen Sie sich gerne unserer ausführlichen Dokumentation.
Sehen Sie hierzu bitte in den Unterordner `doc/Twitchess.pdf` (https://github.com/Mateful/Twitchess/blob/master/doc/Twitchess.pdf)