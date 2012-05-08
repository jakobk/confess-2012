#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define BUFFER_SIZE 512

void doSomething(char *string);

int main(void)
{
    unsigned char buffer[BUFFER_SIZE]; 
    size_t n = 0;
    
    // display memory address of buffer
    // printf("%x\n", (unsigned int)(&buffer));
    
    n = fread(buffer, sizeof(unsigned char), BUFFER_SIZE - 1, stdin);
    buffer[n] = '\0';
    
    doSomething(buffer);

    printf("\nThe input was:\n");
    printf("%s\n", buffer);

    return 0;
}

void doSomething(char *string)
{
    char copy[400];

    // create a copy of the string
    strcpy(copy, string);
    
    // do something with the copy
}
