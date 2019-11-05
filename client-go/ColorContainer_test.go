package animatedledstrip

import "testing"

func TestColorContainer_AddColor(t *testing.T) {
	c := ColorContainer{}
	c.AddColor(0xFF)
	if c.colors[0] != 0xFF {
		t.Fail()
	}
	c.AddColor(0xFFFF)
	if c.colors[0] != 0xFF || c.colors[1] != 0xFFFF {
		t.Fail()
	}
}

func TestColorContainer_Json(t *testing.T) {
	c := ColorContainer{}
	c.AddColor(0xFF00FF)
	c.AddColor(0xFFFF)
	if c.Json() != `{"colors":[16711935,65535]}` {
		t.Fail()
	}
}
