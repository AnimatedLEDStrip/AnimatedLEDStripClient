package animatedledstrip

import "testing"

func TestContinuous_String(t *testing.T) {
	c := DEFAULT
	if c.String() != "null" {
		t.Fail()
	}

	c = CONTINUOUS
	if c.String() != "true" {
		t.Fail()
	}

	c = NONCONTINUOUS
	if c.String() != "false" {
		t.Fail()
	}
}
