package main

import "golang.org/x/tour/pic"
import "fmt"

func pointValue1(x, y int) uint8 {
	return uint8((x + y) / 2)
}

func pointValue2(x, y int) uint8 {
	return uint8(x * y)
}

func pointValue3(x, y int) uint8 {
	return uint8(x ^ y)
}

func Pic(dx, dy int) [][]uint8 {
	// initialize slice rows with dy length
	var matrix [][]uint8
	
	// initialize the i'th element of rows with dx length slice
	for i := 0; i < dy; i ++ {
		// var array_rows [dx]uint8
		var array_rows []uint8
		// update the value of each point
		for j := 0; j < dx; j ++ {
			// array_rows[j] = pointValue1(i, j)
			array_rows = append(array_rows, pointValue1(i, j))
		}
		// matrix = append(matrix, array_rows[0:])
		matrix = append(matrix, array_rows)
	}
	
	fmt.Println(matrix)
	return matrix
}

func main() {
	pic.Show(Pic)
}

