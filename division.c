////////////////////////////////////////////////////////////////////////////////
// This File:        division.c
// Other Files:      intdate.c
// Semester:         CS 354 Fall 2016
//
// Author:           Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
/////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

//how many successful divisions done
int count = 0;

/* This is a handler for the zero-divide signal in the user mode. We change 
 * the defaut handler to this one, and make the handler to print the number
 * of successful divisions and the reason of error before a real terminate.
 * There is no parameter or return value for this function.
 */

void sahandler() {
	//print how many successful divisions and end the program
	printf("Error: a division by 0 operation was attempted.\n");
	printf("Total number of operations completed: %d\n", count);
	printf("The program will be stopped.\n");
	exit(0);
}

/* This is a handler for the exit signal in the user mode. We change the defaut
 * handler to this one, and make the handler to print the number of successful 
 * divisions before a real terminate.
 * There is no parameter or return value for this function.
 */
void sighandler() {
	//print how many successful divisions and end the program
	printf("\nTotal number of operations successfully completed: %d\n", count);
	printf("The program will be halted.\n");
	exit(0);
}

/* Main method for get input from stdin, and do division for two input.
 * When dividing by zero or tapping ctrl+c, print how many operations 
 * done successful, and end the program.
 * There is no parameter or return value.
 */
int main() {
	//division variables
	int a;
	int b;
	int result;
	int remainder;

	//change the default signal handler to what we write
	struct sigaction sa;
	memset(&sa, 0, sizeof(sa));
	sa.sa_handler = sahandler;
	sa.sa_flags = 0;
	//check return value
	int check = sigaction(SIGFPE, &sa, NULL);
	if(check != 0) {
		printf("ERROR SETTING SIGFPE HANDLER.\n");
	}
	
	//change the default signal handler to what we write
	struct sigaction sig;
	memset(&sig, 0, sizeof(sig));
	sig.sa_handler = sighandler;
	sig.sa_flags = 0;
	//check return value
	int check2 = sigaction(SIGINT, &sig, NULL);
	if(check2 != 0) {
		printf("ERROR SETTING SIGFPE HANDLER.\n");
	}	
	
	//infinite loop for dividing operation
	while(1) {

		//ask for first divider
		printf("Enter first integer: ");
		
		const int bufsize = 100;
		char buffer[bufsize];
		if(fgets(buffer, bufsize, stdin) != NULL) {
			a = atoi(buffer);
			
			//ask for second divider
			printf("Enter second integer: ");
			if(fgets(buffer, bufsize, stdin) != NULL) {
			b = atoi(buffer);
			}
			else {
				printf("ERRPR");
				exit(0);
			}
		}
		else {
			printf("ERROR");
			exit(0);
		}

		//operating the division and print the result
		result = a/b;
		remainder = a%b;
		count++;
		printf("%d / %d is %d with a remainder of %d\n", a, b, result, remainder);
	}
	return(0);
}
