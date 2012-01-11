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
Twitchess ist eine Konsolenanwendung, welche es ermÃ¶glicht, Ã¼ber Twitter gegen einen Schachmotor zu spielen.

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

* Spielen gegen KI (Anbindung einer Schachengine) (erfÃ¼llt)
  
* Anbindung an Twitter (Steuerung Ã¼ber Nachrichten) (erfÃ¼llt)
  
* Aktuelle Status-/Spielfeldanzeige durch Grafiken (Ã¼ber Twitter) (erfÃ¼llt)
  
* Speicherung laufender und vergangener Spiele/Spieler (erfÃ¼llt)
  

Zusatzanforderungen:

* Ã„nderung des Schwierigkeitsgrades (erfÃ¼llt -> Ã„nderung von Engine.TimePerMove in configuration.properties)
  
* Eigene KI (nicht erfÃ¼llt, sehr komplex)
  
* Spieler gegen Spieler (nicht erfÃ¼llt, aber einfach implementierbar)
  
* GUI (nicht erfÃ¼llt, bringt keinen Mehrwert)
  
* Andere Spiele wie TicTacToe oder Vier Gewinnt (nicht erfÃ¼llt, aber einfach hinzufÃ¼gbar durch Ã„nderungen in ManagerFactory und das Anlegen eines neuen Managers)
    

Erzeugung einer ausfÃ¼hrbaren Jar
================================
* "ant jar"

AusfÃ¼hren in der Konsole:
=========================

Zum AusfÃ¼hren von Twitchess bietet sich zwei Wege. Zum einen haben wir fÃ¼r Windows und Unix Systeme fertige 
Scripte angelegt, die das Starten vereinfachen. Falls ein solches System nicht zur VerfÃ¼gung steht oder es 
Probleme gibt, genÃ¼gt auch das AusfÃ¼hren folgenden Befehls:

* "java -jar jar/twitchess.jar"
(img/*, chessengines/* und configuration.properties mÃ¼ssen sich im gleichen Ordner befinden)

AusfÃ¼hrliche Dokumentation
==========================
Um nÃ¤heren Einblick zu erlangen bedienen Sie sich gerne unserer ausfÃ¼hrlichen Dokumentation.
Sehen Sie hierzu bitte in den Unterordner doc/Twitchess.pdf (https://github.com/Mateful/Twitchess/blob/master/doc/Twitchess.pdf)