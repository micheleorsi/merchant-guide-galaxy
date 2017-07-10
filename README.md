# Merchant's Guide to the Galaxy

## Usage

In order to run the application interactively, you should use the following command:

```bash
mvn clean package exec:java -DskipTests=true
```

In order to run the application from a file

```bash
mvn clean package exec:java -Dexec.args="input.txt" -DskipTests=true
```

## Test

Launch all the tests:

```bash
mvn clean verify 
```

## Solution explanation

### Overview

I solved this problem applying hexagonal architecture. In this way I isolated the domain of the application under 'application' package: there are no dependencies from the domain to external code.
I left the delivery mechanism outside from the application in a package called 'console'. Some classes of this package depend on the domain.
I also extracted the in-memory repository classes, probably it's a little bit over engineered for the moment, but I want to make sure that all the different parts are in the right place since the beginning!

I use the TDD approach and I tried to limit the size of classes and methods. I created unit tests for everything, apart from 'Display' classes, because they just print out some messages and for the moment they are covered from the E2E ones. 

I know that dependency upon abstraction is always the best approach, but I extracted interfaces from classes only when there is more than one implementation available. Having an interface for each dependency probably at the moment is a little bit too over engineered.

### Specific assumptions

* I am expecting a specific format for the parsing of all the lines
* I assume that all the names of metal are known, I defined in the enum 'Metal'
* every command is separated from a '\n' (newline)