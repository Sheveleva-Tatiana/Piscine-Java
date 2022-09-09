Консольная игра.

Установка:

Из директории TerminalGame
```
mvn install
```
Запуск игры:

Из директории TerminalGame:
```
java -jar target/game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production
```
Для изменения режима игры:
```
--enemiesCount=<> - количество врагов
--wallsCount=<> - количество стен
--size=<> - размер карты (30*30);
--profile=production - режим игры (production - обычный режим, dev - режим разработки, подразумевает подверждение хода противника)
```
Для изменения цветой гаммы и символа элементов отредактируйте соответсвующий properties файл в папке resources
<img width="145" alt="Снимок экрана 2022-08-26 в 4 43 03 PM" src="https://user-images.githubusercontent.com/94602550/186917303-abb04349-dad3-4b77-8375-81446b5b253a.png">
