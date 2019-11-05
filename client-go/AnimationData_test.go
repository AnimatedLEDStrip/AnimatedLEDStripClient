package animatedledstrip

import "testing"

func TestAnimationData(t *testing.T) {
	data := AnimationData()

	if data.animation != COLOR {
		t.Fail()
	} else if len(data.colors) != 0 {
		t.Fail()
	} else if data.center != -1 {
		t.Fail()
	} else if data.continuous != DEFAULT {
		t.Fail()
	} else if data.delay != -1 {
		t.Fail()
	} else if data.delayMod != 1.0 {
		t.Fail()
	} else if data.direction != FORWARD {
		t.Fail()
	} else if data.distance != -1 {
		t.Fail()
	} else if data.endPixel != -1 {
		t.Fail()
	} else if data.id != "" {
		t.Fail()
	} else if data.spacing != -1 {
		t.Fail()
	} else if data.startPixel != 0 {
		t.Fail()
	}
}
