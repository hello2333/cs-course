package main

import "fmt"

func main() {
	s := []int{2, 3, 5, 7, 11, 13}
	printSlice(s)

	// Slice the slice to give it zero length.
	s = s[:0]
	printSlice(s)

	// Extend its length.
	s = s[:4]
	printSlice(s)
	
	// Extend its length beyond capacity.
	s = s[:8]
	printSlice(s)

	// Drop its first two values.
	s = s[2:]
	printSlice(s)
	
	// Extend length by append successfully
	b := [8] int {1,2,3,4,5,6,7,8}
	sb := b[0:]
	s = append(s, sb...)
	printSlice(s)
	
	var a []int
	a = append(a, 1)
	printSlice(a)
}

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

