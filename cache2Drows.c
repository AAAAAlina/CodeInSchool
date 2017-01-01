///////////////////////////////////////////////////////////////////////////////
// File Name:        cache2Drows.c
// Semester:         CS 354 Fall 2016
//
// Author:           Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
////////////////////////////////////////////////////////////////////////////////


#include <stdio.h>


int arr[3000][5000];

int main() {
	int i;
	for (i = 0; i < 3000; i++) {
		int j;
		for (j = 0; j < 5000; j++) {
			arr[i][j] = i + j;
		}
	}
	return 0;
}
