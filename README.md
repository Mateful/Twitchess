Projekt Twitchess
========================

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
===============
Twitchess ist eine Konsolenanwendung, welche es ermöglicht, über Twitter gegen einen Schachmotor zu spielen.

Verwendete APIs
---------------
* Schachmotor

	https://github.com/mcostalba/Stockfish
	
* SQLite-DB

	http://www.zentus.com/sqlitejdbc
	
* Twitter4j

	http://twitter4j.org
		


Anforderungen
----------------

Mindestanforderungen des Projekts:

* Spielen gegen KI (Anbindung einer Schachengine) (erfüllt)
  
* Anbindung an Twitter (Steuerung über Nachrichten) (erfüllt)
  
* Aktuelle Status-/Spielfeldanzeige durch Grafiken (über Twitter) (erfüllt)
  
* Speicherung laufender und vergangener Spiele/Spieler (erfüllt)
  

Zusatzanforderungen:

* Änderung des Schwierigkeitsgrades (erfüllt -> Änderung von Engine.TimePerMove in configuration.properties)
  
* Eigene KI (nicht erfüllt, sehr komplex)
  
* Spieler gegen Spieler (nicht erfüllt, aber einfach implementierbar)
  
* GUI (nicht erfüllt, bringt keinen Mehrwert)
  
* Andere Spiele wie TicTacToe oder Vier Gewinnt (nicht erfüllt, aber einfach hinzufügbar durch Änderungen in ManagerFactory und das Anlegen eines neuen Managers)
    

Erzeugung einer ausführbaren Jar
================================
* "ant jar"

Ausführen in der Konsole:
=========================

Zum Ausf�hren von Twitchess bietet sich zwei Wege. Zum einen haben wir f�r Windows und Unix Systeme fertige 
Scripte angelegt, die das Starten vereinfachen. Falls ein solches System nicht zur Verf�gung steht oder es 
Probleme gibt, gen�gt auch das Ausf�hren folgenden Befehls:

* "java -jar jar/twitchess.jar"
(img/*, chessengines/* und configuration.properties müssen sich im gleichen Ordner befinden)

Ausführliche Dokumentation
==========================
Um n�heren Einblick zu erlangen bedienen Sie sich gerne unserer ausf�hrlichen Dokumentation.
Sehen Sie hierzu bitte in den Unterordner doc/Twitchess.pdf (https://github.com/Mateful/Twitchess/blob/master/doc/Twitchess.pdf)