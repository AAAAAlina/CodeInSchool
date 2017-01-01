///////////////////////////////////////////////////
// Main File:		verify_magic.c
// This File:		generate_magic.c
// Other Files:		verify_magic.c
// Semester:		CS 354 Fall 2016
//
// Author:		Qiannan GUo
// Email:		qguo43@wisc.edu
// CS Login:		qiannan
//
//////////////////////////////////////////////////

#include <stdio.h>
#include <stdlib.h>

// Structure representing Square
// size: dimension(number of rows/columns) of the square
// array: 2D array of integers
typedef struct _Square {
	int size;
	int **array;
} Square;

int get_square_size();
Square * generate_magic(int size);
void write_to_file(Square * square, char *filename);

/* Main method for the whole organization about how to 
 * generate a magic square.
 *
 * @param argc The counts of input
 * @param *argv[] the content of every input string
 *
 * @return 0
 */
int main(int argc, char *argv[])
{
	// Check input arguments to get filename
	if (argc != 2) {
		fprintf(stderr, "Usage: ./generate_magic <filename>\n");
		exit(1);
	}
	
	// Get size from user
	int size = get_square_size();
	
	// Generate the magic square
	Square *magic = generate_magic(size);
	
	// Write the square to the output file
	write_to_file(magic, argv[1]);
	
	// Free the allocated memory on the heap
	int i;
	for (i = 0; i < size; i++) {
		free(*((magic->array) + i));
	}
	free((*magic).array);
	free(magic);
	
	return 0;
}

/* get_square_size prompts the user for the magic square size
 * checks if it is an odd number >= 3 and returns the number
 *
 * @return size of the square
 */
int get_square_size()
{
	printf("Enter size of magic square, must be odd\n");
	char str[20];

	int reValue = scanf("%s", str);
	if (reValue < 0) {
		printf("Fail to scan the input.\n");
		exit(1);
	}

	int size = atoi(str);
	if (size == 0) {
		printf("Cannot convert the char to int.\n");
		exit(1);
	}

	if (size < 3 || size % 2 == 0) {
		fprintf(stderr, "Size must be an odd number >= 3.\n");
		exit(1);
	}
	return size;
}

/* generate_magic constructs a magic square of size n
 * using the Siamese algorithm and returns the Square struct
 *
 * @param n the size of the square we want to genetate
 *
 * @return a square structure with a magic square
 */
Square * generate_magic(int n)
{	
	Square * magic = malloc(sizeof(Square));
	if (magic == NULL) {
		printf("Cannot dynamically allocated the memory on the heap.\n");
		exit(1);
	}

	(*magic).size = n;
	(*magic).array = malloc(n * sizeof(int*));
	if ((*magic).array == NULL) {
		printf("Cannot dynamically allocate the memory on the heap.\n");
		exit(1);
	}

	int i;
	for (i = 0; i < n; i++) {
		*((magic->array) + i) = malloc(n * sizeof(int));
		if (*((magic->array) + i) == NULL) {
			printf("Cannot dynamically allocate the memory on the heap.\n");
			exit(1);
		}
	}

	int a = (n - 1) / 2;
	int b = n - 1;
	int pa;
	int pb;
	*(*(magic->array + a) + b) = 1;
	int j;
	for (j = 1; j < n*n; j++) {
		if (a == (n - 1)) {
			pa = a;
			a = 0;
		}
		else {
			pa = a;
			a = a + 1;
		}
		if (b == (n - 1)) {
			pb = b;
			b = 0;
		}
		else {
			pb = b;
			b = b + 1;
		}
		if (*(*(magic->array + a) + b) != 0){
			a = pa;
			b = pb - 1;
			*(*(magic->array + a) + b) = j + 1;
		}
		else {
			*(*(magic->array + a) + b) = j + 1;
		}
	}
	return magic;
}

/* write_to_file opens up a new file(or overwrites the existing file)
 * and writes out the square in the format expected by verify_magic.c
 *
 * @param square The magic square that we generate.
 * @param *filename A string filename which we want to write the square
 *	  in.
 */
void write_to_file(Square * square, char *filename)
{
	FILE *ofp = fopen(filename, "w+");
	if (ofp ==NULL) {
		printf("Cannot open the file.\n");
		exit(1);
	}
	int size = (*square).size;
	int reValue2 = fprintf(ofp, "%d\n", size);
	if (reValue2 < 0) {
		printf("Cannot write to the file.\n");
		exit(1);
	}

	int l;
	int r;
	for (l = 0; l < size; l++) {
		for (r = 0; r < size; r++) {
			int reValue3 = fprintf(ofp, "%d", *(*(square->array + l) + r));
			if (reValue3 < 0) {
				printf("Cannot write to the file.\n");
				exit(1);
			}

			if (r != size-1) {
				int reValue4 = fprintf(ofp, ",");
				if (reValue4 < 0) {
					printf("Cannot write to the file.\n");
					exit(1);
				}
			}
		}
		int reValue5 = fprintf(ofp, "\n");
		if (reValue5 < 0) {
			printf("Cannot write to the file.\n");
			exit(1);
		}
	}

	int reValue6 = fclose(ofp);
	if (reValue6 == EOF) {
		printf("Cannot close the file.\n");
		exit(1);
	}
}
