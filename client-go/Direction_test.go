package animatedledstrip

import "testing"

func TestDirection_String(t *testing.T) {
	d := FORWARD
	if d.String() != "FORWARD" {
		t.Fail()
	}

	d = BACKWARD
	if d.String() != "BACKWARD" {
		t.Fail()
	}

	d = -1
	if d.String() != "FORWARD" {
		t.Fail()
	}
}
