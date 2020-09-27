# KotlinCli

This is a base command line interpreter written on Kotlin.
The project is training, but I tried to do my best in architectures and best practices)  

## Build & Run
```sh
./gradlew run --console=plain
```

## Usage example

You can use it as bash shell:
```sh
> echo "Hello, world!"
Hello, world!
> FILE=example.txt
> cat $FILE
Some example text
> cat example.txt | wc
1 3 18
> echo 123 | wc
1 1 3
> x=exit
> $x
```

## Syntax
### Supported commands

* cat [FILE] — print file content
* echo — display arguments
* wc [FILE] — print number of lines, words and byte
* pwd — display current working directory
* exit — exit cli
* Note: if unknown command entered then external command will be called through java Process 

### Supported operators
* Assigment operator "="
* Dollar operator "$"
* Pipeline "|"  
* 'Full' and "weak" quoting

## For Developers
### Architecture
Something close to class diagram: <https://drive.google.com/file/d/1Z90_NrCvBziL9iP62ih5rWQS3n3i_l6o/view?usp=sharing>

There is 3 part: WordParser, CommandParser and Shell.
* Word parser converts String to Words, and has similar to Bash parser architecture. 
* CommandParser is just comfort interface for working with Pipeline. So there is an adapter for WordParser.
* Shell executes commands and connects stdin with stdout in pipelines.

### Style guide
Using default Kotlin Coding Conventions
https://kotlinlang.org/docs/reference/coding-conventions.html

