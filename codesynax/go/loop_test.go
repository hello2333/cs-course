package main

import (
	"fmt"
	"math"
)

const (
	Small = 0.00000000000001
	MaxLoop = 20
)

func Sqrt(x float64) float64 {
	// init z
	z := 1.0
	// z := x / 2
	
	// repeat expression until dert dosen't change or dert is small enough
	
	for i := 0; i < MaxLoop; i ++ {
		// calculate the dert
		dert := (z * z - x) / (2 * z)
		// judge whether the program need to continue
		if math.Abs(dert) <= Small || z == (z - dert) {
			fmt.Printf("lib result: %v, self result: %v. diff: %v\n", math.Sqrt(x), z, math.Abs(z - math.Sqrt(x)))
			break
		}
		// print the dert
		fmt.Printf("how close get to the answer %v: z is %v, x is %v, dert is %v\n", i, z, x, dert)
		// update z by the dert
		z -= dert
	}
	
	return z
}

func main() {
	fmt.Println(Sqrt(92750183))
}

