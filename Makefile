all: run

clean:
	rm -f out/Main.jar out/DistanceCalculator.jar

out/Main.jar: out/parcs.jar src/Main.java
	@javac -cp out/parcs.jar src/Main.java
	@jar cf out/Main.jar -C src Main.class -C src
	@rm -f src/Main.class

out/DistanceCalculator.jar: out/parcs.jar src/DistanceCalculator.java
	@javac -cp out/parcs.jar src/DistanceCalculator.java
	@jar cf out/DistanceCalculator.jar -C src DistanceCalculator.class -C src
	@rm -f src/DistanceCalculator.class

build: out/Main.jar out/DistanceCalculator.jar

run: out/Main.jar out/DistanceCalculator.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main
