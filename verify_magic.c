//////////////////////////////////////////////
// Main File:		verify_magic.c
// This File:		verify_magic.c
// Other Files:		generate_magic.c
// Semester:		CS 354 Fall 2016
//
// Author:		Qiannan Guo
// Email:		qguo43@wisc.edu
// CS Login:		qiannan
//
//////////////////////////////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure representing Square
// size: dimension(number of rows/columns) of the square
// array: 2D array of integers
typedef struct _Square {
	int size;
	int **array;
} Square;

Square * construct_square(char *filename);
int verify_magic(Square * square);

/* Main method for all organization, how the program do the tasks
 * people want in a well-designed order.
 *
 * @param argc An integer for how many arguments the input has.
 * @param *argv[] The characters of every input string.
 *
 * @return 0
*/
int main(int argc, char *argv[])
{
	// Check input arguments to get filename
	if (argc != 2) {
		fprintf(stderr, "Usage: ./verify_magic <filename>\n");
		exit(1);
	}

	// Construct square
	Square *sqr = construct_square(argv[1]);
	
	// Verify if it's a magic square and print true or false
	int verify = verify_magic(sqr);
	if (verify == 0) {
		printf("false\n");
	}
	else if (verify == 1) {
		printf("true\n");
	}

	//Free the dynamically allocated memory
	int n;
	for (n = 0; n < (*sqr).size; n++) {
		free(*((sqr->array) + n));
	}
	free((*sqr).array);
	free(sqr);
	
	return 0;
}
 
/* construct_square reads the input file to initialize a square struct
 * from the contents of the file and returns the square.
 *
 * @param *filename character pointer for the name of the file we need to
 *        read.
 *
 * @return a square with a magic square from the file
 */
Square * construct_square(char *filename)
{

	// Open and read the file
	FILE *ifp = fopen(filename, "r");
	if (ifp == NULL) {
		fprintf(stderr, "Cannot open file for reading.\n");
		exit(1);
	}
        
	// Read the first line to get the square size
	int getSize;
	int reValue = fscanf(ifp, "%d", &getSize);
 	if (reValue == 0) {
		printf("Cannot scan the file.\n");
		exit(1);
	}

	// Initialize a new Square struct of that size
    	Square *new = malloc(sizeof(Square));
	if (new == NULL) {
		printf("Cannot allocate memory on the heap.\n");
		exit(1);
	}
	new->size = getSize;
 	
	// Read the rest of the file to fill up the square
	(*new).array = malloc(getSize * sizeof(int*));
	if ((*new).array == NULL) {
		printf("Cannot allocate memory on the heap.\n");
		exit(1);
	}

	int n;
	int m;
	for (n = 0; n < getSize; n++) {
		*((new->array) + n) = malloc(sizeof(int) * getSize);
		if (*((new->array) + n) ==NULL) {
			printf("Cannot allocate memory on the heap.\n");
			exit(1);
		}
		for (m = 0; m < getSize-1; m++) {
			int reValue2 = fscanf(ifp, "%d,", (*(new->array + n) +m));
			if (reValue2 == 0) {
				printf("Cannot scan the file.\n");
				exit(1);
			}
		}
		if (m == getSize-1) {
			int reValue3 = fscanf(ifp, "%d", (*(new->array + n) + m));
			if (reValue3 == 0) {
				printf("Cannot scan the file.\n");
				exit(1);
			}
		}
	}

	int reValue4 = fclose(ifp);
	if (reValue4 == EOF) {
		printf("Fail to close.\n");
		exit(1);
	}

	return new;
}

/* verify_magic verifies if the square is a magic square.
 *
 * @param a square which wanted to be verified.
 *
 * @return 1(true) or 0(false)
 */
int verify_magic(Square * square)
{
	int check = 1;
	
	// Check all rows sum to same number
	int n;
	int m;
	int rowTotal[(*square).size];
	for (n = 0; n < (*square).size; n++) {
		rowTotal[n] = 0;
		for (m = 0; m < (*square).size; m++) {
			rowTotal[n] = rowTotal[n] + *(*(square->array + n) + m);
		}
	}
	int i;
	for (i = 1; i < (*square).size; i++) {
		if (rowTotal[i-1] != rowTotal[i]) {
			check = 0;
		}
	}
	
	// Check all cols sum to same number
	int nn;
	int mm;
	int colTotal[(*square).size];
	for (nn = 0; nn < (*square).size; nn++) {
		colTotal[nn] = 0;
		for (mm = 0; mm < (*square).size; mm++) {
			colTotal[nn] = colTotal[nn] + *(*(square->array + mm) + nn);
		}
	}
	int ii;
	for (ii = 1; ii < (*square).size; ii++) {
		if (colTotal[ii-1] != colTotal[ii]) {
			check = 0;
		}
	}
	if (rowTotal[0] != colTotal[0]) {
		check = 0;
	}
	
	// Check main diagonal
	int n3;
	int m3;
	int diagonal1 = 0;
	for (n3 = 0; n3 < (*square).size; n3++) {
		for (m3 = 0; m3 < (*square).size; m3++) {
			if (n3 == m3) {
				diagonal1 = diagonal1 + *(*(square->array + n3) + m3);
			}
		}
	}
	if (diagonal1 != colTotal[0]) {
		check = 0;
	}
	
	// Check secondary diagonal
	int n4;
	int m4;
	int diagonal2 = 0;
	for (n4 = 0; n4 < (*square).size; n4++) {
		for (m4 = 0; m4 < (*square).size; m4++) {
			if ((n4 + m4) == ((*square).size - 1)) {
				diagonal2 = diagonal2 + *(*(square->array + n4) + m4);
			}
		}
	}
	if (diagonal2 != diagonal1) {
		check = 0;
	}
	
	return check;
}
