package main

import (
	"strconv"
	"strings"
)

type ColorContainer struct {
	colors []int
}

func (c *ColorContainer) AddColor(color int) *ColorContainer {
	c.colors = append(c.colors, color)
	return c
}

func (c ColorContainer) Json() string {
	var stringParts []string
	stringParts = append(stringParts, `{"colors":[`)
	for _, c := range c.colors {
		stringParts = append(stringParts, strconv.Itoa(c))
		stringParts = append(stringParts, ",")
	}
	stringParts = stringParts[:len(stringParts)-1]
	stringParts = append(stringParts, "]}")
	return strings.Join(stringParts, "")
}
