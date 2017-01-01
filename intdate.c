////////////////////////////////////////////////////////////////////////////////
// This File:        intdate.c
// Other Files:      division.c
// Semester:         CS 354 Fall 2016
//
// Author:           Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
/////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <string.h>

int num = 3;
int end = 5;

/* This is a handler for the exit signal in the user mode. We change the default
 * handler to this one, and make the handler count five ^C before a real terminate.
 * There is no parameter or return value for this function.
 */
void exitHandler() {

	//count for how many times left to end
	end--;

	//if not end, print how many times still needed to end
        while(end > 0) {
                printf("\nControl-c caught. %d more before program ends.\n", end);
        	return;
	}

	//if end, print the progam is end
        if(end == 0) {
		printf("\nFinal control-c caught. Exiting.\n");
                exit(0);
        }
}

/* This is a handler for the alarm signal in the user mode. We change the default
 * one to this one, and make the handler print the current time and restart a new
 * three second alarm. Therefore, the current time will be printed every three 
 * second.
 * There is no parameter or return value for this function.
 */
void sahandler() {
	
	//get current time and print it
	time_t now = time(0);
	printf("Current time is %s", ctime(&now));

	//set the time between every print
	alarm(num);
	//change the default signal handler to what we write
	struct sigaction act;
	memset(&act, 0, sizeof(act));
	act.sa_handler = exitHandler;
	act.sa_flags = 0;
	//check return value 
	int check = sigaction(SIGINT, &act, NULL);
	if(check != 0) {
		printf("ERROR SETTING SIGINT HANDLER.\n");
	}
	
	return;
}

/* Main function for printing current time every three seconds, 
 * and require five ^C to exit the program instead of one.
 * There is no parameter or return value.
 */
int main(void) {

	printf("Date will be printed every 3 seconds\n"
		"Enter ^C 5 times to end the program:\n");
	
	//change the default signal handler to what we write
	struct sigaction sa;
	memset(&sa, 0, sizeof(sa));
	sa.sa_handler = sahandler;
	sa.sa_flags = 0;
	//check return value
	int check2 = sigaction(SIGALRM, &sa, NULL);
	if(check2 != 0) {
		printf("ERROR SETTING SIGALRM HANDLER.\n");
	}
	
	//set time between print
	alarm(num);

	while(1);
	return(0);
}

