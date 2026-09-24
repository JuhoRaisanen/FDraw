echo Compile classes and create packages
javac -d . DrawArea.java
javac -d . TiedostonLukija.java
javac -d . Ikkuna.java
javac -d . Resolution.java
javac -d . FunktioKuva.java

echo Create jar
jar cvfe FunctionDrawer.jar main.FunktioKuva main/ utils/

cmd /k
