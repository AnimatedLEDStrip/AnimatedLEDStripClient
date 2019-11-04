package animatedledstrip

type Direction int

const (
	FORWARD Direction = iota
	BACKWARD
)

func (d Direction) String() string {
	switch d {
	case FORWARD:
		return "FORWARD"
	case BACKWARD:
		return "BACKWARD"
	default:
		return "FORWARD"
	}
}
