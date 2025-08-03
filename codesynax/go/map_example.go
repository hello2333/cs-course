package main

import (
	"golang.org/x/tour/wc"
	"strings"
)

func WordCount(s string) map[string]int {
	// split s into slice
	var word_list []string = strings.Fields(s)
	
	// initialize a map
	var word_count = make(map[string]int)
	
	// iterate the slice and update the count of words
	for _, v := range word_list {
		_, ok := word_count[v]
		if ok {
			word_count[v] += 1
		} else {
			word_count[v] = 1
		}
	}

	return word_count
}

func main() {
	wc.Test(WordCount)
}

