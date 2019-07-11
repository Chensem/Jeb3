# Jeb3
Jeb3 Demo (include mips &amp;&amp; arm64 &amp;&amp; elf &amp;&amp; pe &amp;&amp; wasm decompiler) without limit 
Patch the file use javasist.

## Raw Limits
* Decompilation is limited to a subset of code
* Saving or loading projects is disabled
* Usage of the clipboard is disallowed
* Running time of a session is limited
* Requires the Internet connection
* Usage of third-party back-end plugins is disabled
* Usage of third-party clients is disabled
       
       
## Raw Features
* Wasm Decompiler (which one attracts me most)
* ARM (32, Thumb) Disassembler			
* ARM64 (Aarch64) Disassembler
* MIPS Disassembler			
* Atmel AVR Disassembler	
* Ethereum contracts (evm) Disassembler
* Intel x86 Disassembler			
* Intel x86-64 (AMD64) Disassembler
* all kinds of debugger

## Features
> the limits has became 0xDEADBEEF
* move the limitation of (subset of code) 
* can save project and load project
* clipboard can use Ctrl-X
* Don't need internet connection
* other features are available

# Usage

## Directory Tree
* src (the source code)
* jeb.jar the jeb3_demo core api 
* clean.sh ...

## Full Usage
1. copy the origin jeb.jar at $PATH_JEB_DEMO$/bin/app to the root path of the project
2. Run the project (PathJar.java)
3. patch the bytecode of the jeb.jar (you can patch the build_type with 254 value)
4. delete the INF/..RSA that store in jeb.jar
5. copy the fucked jar to the origin path
6. bingo !
