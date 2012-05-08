all: myecho exploit shellcode-test

myecho: myecho.c
	gcc -fno-stack-protector -z execstack -ggdb -o myecho myecho.c

exploit: exploit.c
	gcc -o exploit exploit.c		

shellcode-test: shellcode-test.c
	gcc -o shellcode-test shellcode-test.c

clean:
	rm -rf myecho exploit shellcode-test

