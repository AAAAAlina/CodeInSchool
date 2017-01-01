///////////////////////////////////////////////////////////////////////////////
// File Name:        cache1D.c
// Semester:         CS 354 Fall 2016
//
// Author:           Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>

int arr[100000];

int main() {
	int i;
	for (i = 0; i < 100000; i++) {
		arr[i] = i;
	}
	return 0;
}
