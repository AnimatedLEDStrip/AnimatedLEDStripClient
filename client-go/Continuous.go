package main

type Continuous int

const (
	DEFAULT Continuous = iota
	CONTINUOUS
	NONCONTINUOUS
)

func (c Continuous) String() string {
	switch c {
	case DEFAULT:
		return "null"
	case CONTINUOUS:
		return "true"
	case NONCONTINUOUS:
		return "false"
	default:
		return "null"
	}
}
