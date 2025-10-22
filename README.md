# MicroJava Compiler

This project involves the development of a compiler for the **MicroJava language**. The realization of the project consisted of four main stages: Lexical analysis, Syntax analysis, Semantic analysis, and Code generation. The main idea behind the project was to gain familiarity with the basic functionalities of the language and, finally, similar to other standard languages (specifically Java), generate **MicroJava bytecode** that would later be executed on the **MicroJava Virtual Machine**.

## Command Descriptions

The following sections explain the steps for generating code to assist with the lexical, parser, and semantic analysis, along with instructions for generating and executing the bytecode.

### 1. Generating Lexer Components (LexerGenerator)

The Lexer components are generated using the JFlex tool.

**Command:**
```bash
java JFlex.Main -d src\rs\ac\bg\etf\pp1 spec\mjlexer.flex
```
Action: This command runs the JFlex main class with the specified output directory and lexer specification file.


### 2. Generating Parser Components (ParserGenerator)
The Parser components are generated using the Java CUP tool.

***Command:***

```bash
java java_cup.Main -destdir rs\ac\bg\etf\pp1 -dump_states -parser MJParser -ast rs.ac.bg.etf.pp1.ast -buildtree ..\spec\mjparser.cup
```
Action: This command runs the Java CUP main class with arguments specifying the destination directory, parser class name (MJParser), AST package (rs.ac.bg.etf.pp1.ast), and the parser specification file.


Note: The two commands above are typically executed using the Ant build tool. Running the appropriate Ant target will perform the respective action. The compile target will execute both of the above steps.

### 3. Executing Semantic Analysis (Compile MJ Source)
This step involves compiling the MicroJava source file, which includes lexical, syntactic, and semantic checks, and finally generates the object file.

Class: rs.ac.bg.etf.pp1.Compiler

***Command:***

```bash
java rs.ac.bg.etf.pp1.Compiler test\program.mj test\program.obj
```

test\program.mj: Name of the input MicroJava source file.
test\program.obj: Name of the output object file (MicroJava bytecode).

### 4. Disassembling Bytecode (Disasm)
This step disassembles the generated MicroJava object file into a human-readable format.

Class: rs.etf.pp1.mj.runtime.disasm

***Command:***

```bash
java rs.etf.pp1.mj.runtime.disasm test\program.obj
```
test\program.obj: The generated object file of the program.

### 5. Running Bytecode (Run)
This step executes the generated MicroJava bytecode on the MicroJava Virtual Machine.

Class: rs.etf.pp1.mj.runtime.Run

***Command:***

```bash
java rs.etf.pp1.mj.runtime.Run -debug test\program.obj
```
-debug: Option to specify execution in debug mode.

test\program.obj: The generated object file of the program.
