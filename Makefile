all: run

clean:
	rm -f out/Main.jar out/DistanceCalculator.jar

out/Main.jar: out/parcs.jar src/Main.java src/Point.java
	@javac -cp out/parcs.jar src/Main.java src/Point.java
	@jar cf out/Main.jar -C src Main.class -C src Point.class
	@rm -f src/Main.class src/Point.class

out/DistanceCalculator.jar: out/parcs.jar src/DistanceCalculator.java src/Point.java
	@javac -cp out/parcs.jar src/DistanceCalculator.java src/Point.java
	@jar cf out/DistanceCalculator.jar -C src DistanceCalculator.class -C src Point.class
	@rm -f src/DistanceCalculator.class src/Point.class

build: out/Main.jar out/DistanceCalculator.jar

run: out/Main.jar out/DistanceCalculator.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main
