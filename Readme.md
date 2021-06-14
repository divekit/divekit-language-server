# Divekit-Language-Server

## Beschreibung

Dieses Repository enthält den Sourcecode für den Divekit Language Server,
welcher zusammen mit den Repositorys [divekit-language-plugin-intellij](https://github.com/divekit/divekit-language-plugin-intellij)
und [divekit-language-plugin-vscode](https://github.com/divekit/divekit-language-plugin-vscode) die Codebasis für das Praxisprojekt
"**Entwicklung eines IDE Plugins zur Unterstützung bei der Erstellung individualisierter Praktika-und Klausuraufgaben**" bildet.

Eine ausführliche Dokumentation der verschiedenen Projektbestandteile befindet sich im [Wiki](https://github.com/divekit/divekit-language-server/wiki).

## Nutzung

Um den Divekit Language Server nutzen zu können, muss eine JAR-Datei aus dem Sourcecode des Projekts erstellt werden. 
Diese kann nach dem Kopieren des Repositorys auf das lokale Dateisystem unkompliziert mithilfe von ``mvn package`` erzeugt werden.
Hierfür wird lediglich [Maven](https://maven.apache.org/install.html) benötigt. 

## Weiterentwicklung & Anpassungen

 * Falls der Divekit Language Server weiterentwickelt werden soll, lohnt sich ein blick ins [Wiki](https://github.com/divekit/divekit-language-server/wiki), wo dieser 
genauer beschrieben ist.

* Fehler können zunächst bei den GitHub [Issues](https://github.com/divekit/divekit-language-server/issues) eingetragen werden.

* Vor dem Mergen empfiehlt sich in der Regel ein Pull-Request, da so die Code-Qualität hoch gehalten werden kann.