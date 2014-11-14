# Pitch AWESOME
__Projektteam__
Adrian Schmidt, Matthias Ridder, Dominic von Zielinski, Fabian Schneider

__Titel__
AWESOME

__Einzeiler__
TicTacToe mit Würfeln

__Beschreibung__
„AWESOME“ wird von zwei awesomen Personen gespielt. Jeder Spieler bekommt eine Farbe. Das Spielfeld hat eine Größe von 3x3 Kästchen mit je 3x3 Nummernfeldern. Um ein Kästchen für sich zu gewinnen, muss darin ein eigenes kleines TicTacToe mittels würfeln zweier Würfel gelöst werden. Ziel ist es, wie beim originalen TicTacToe drei Kästchen auf horizontaler, vertikaler oder diagonaler Linie in seinen Besitz zu bringen. Das Spiel besitzt eine hohe Dynamik, da jedes Kästchen einzeln erkämpft werden muss und im letzten Augenblick vom Gegner übernommen werden kann. AWESOME!

__Details, technische Herausforderungen, etc.__
Das Spiel soll in der Konsole umgesetzt werden (TUI). Die Plätze in den Feldern werden per Zufall mit den Zahlen von 3 bis 11 belegt, wobei die 7 immer das Mittelfeld markiert. Ein zufällig gewählter Spieler beginnt das Spiel und würfelt mit zwei Würfeln (2 … 12) eine Zahl. Diese kann er auf einem Feld belegen, welche diese Zahl beinhaltet. Weiterhin kann ein beliebiges Feld, ausgenommen der Mittleren (7), in dem Kästchen welches der Augenzahl entspricht, belegt werden. Wenn eine 12 gewürfelt wird, darf ein beliebiges, freies Feld in einem beliebigen Kästchen belegt werden. Bei einer 7 darf jedes Mittelfeld, sowie jedes Feld des Kästchens 7 belegt werden. Das Würfeln einer 2 erlaubt das Entfernen eines gegnerisch belegten Feldes, mit Ausnahme eines vollständigen Kästchens. Anschließend darf nochmals gewürfelt werden.

__Erweiteungen__
Erweiterungsmöglichkeiten sind beispielsweise eine grafische Oberfläche und ein dazugehöriger Server, welcher zwei angemeldete Spieler zufällig zuweist. Weiterhin kann (für Spieler mit eingeschränkten sozialen Fähigkeiten) eine KI implementiert werden.