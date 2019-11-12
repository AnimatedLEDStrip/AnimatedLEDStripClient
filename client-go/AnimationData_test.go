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

func TestAnimationData_SetAnimation(t *testing.T) {
	data := AnimationData()
	data.SetAnimation(BOUNCE)

	if data.animation != BOUNCE {
		t.Fail()
	}
}

func TestAnimationData_AddColor(t *testing.T) {
	cc := ColorContainer{}
	cc.AddColor(0xFF)

	data := AnimationData()
	data.AddColor(&cc)

	if len(data.colors) != 1 {
		t.Fail()
	}
}

func TestAnimationData_SetCenter(t *testing.T) {
	data := AnimationData()
	data.SetCenter(50)

	if data.center != 50 {
		t.Fail()
	}
}

func TestAnimationData_SetContinuous(t *testing.T) {
	data := AnimationData()
	data.SetContinuous(CONTINUOUS)

	if data.continuous != CONTINUOUS {
		t.Fail()
	}
}

func TestAnimationData_SetDelay(t *testing.T) {
	data := AnimationData()
	data.SetDelay(200)

	if data.delay != 200 {
		t.Fail()
	}
}

func TestAnimationData_SetDelayMod(t *testing.T) {
	data := AnimationData()
	data.SetDelayMod(2.0)

	if data.delayMod != 2.0 {
		t.Fail()
	}
}

func TestAnimationData_SetDirection(t *testing.T) {
	data := AnimationData()
	data.SetDirection(BACKWARD)

	if data.direction != BACKWARD {
		t.Fail()
	}
}

func TestAnimationData_SetDistance(t *testing.T) {
	data := AnimationData()
	data.SetDistance(35)

	if data.distance != 35 {
		t.Fail()
	}
}

func TestAnimationData_SetEndPixel(t *testing.T) {
	data := AnimationData()
	data.SetEndPixel(25)

	if data.endPixel != 25 {
		t.Fail()
	}
}

func TestAnimationData_SetID(t *testing.T) {
	data := AnimationData()
	data.SetID("TEST")

	if data.id != "TEST" {
		t.Fail()
	}
}

func TestAnimationData_SetSpacing(t *testing.T) {
	data := AnimationData()
	data.SetSpacing(4)

	if data.spacing != 4 {
		t.Fail()
	}
}

func TestAnimationData_SetStartPixel(t *testing.T) {
	data := AnimationData()
	data.SetStartPixel(5)

	if data.startPixel != 5 {
		t.Fail()
	}
}

func TestAnimationData_Json(t *testing.T) {
	data := AnimationData()
	data.SetAnimation(METEOR)
	data.SetCenter(50)
	data.SetContinuous(NONCONTINUOUS)
	data.SetDelay(10)
	data.SetDirection(BACKWARD)
	data.SetDistance(45)
	data.SetEndPixel(200)
	data.SetID("TEST")
	data.SetSpacing(5)
	data.SetStartPixel(15)

	cc := ColorContainer{}
	cc.AddColor(0xFF).AddColor(0xFF00)
	cc2 := ColorContainer{}
	cc2.AddColor(0xFF0000)
	data.AddColor(&cc)
	data.AddColor(&cc2)

	json := data.Json()
	if json != "DATA:{\"animation\":\"METEOR\",\"colors\":[{\"colors\":[255,65280]},{\"colors\":[16711680]}],\"center\":50,\"continuous\":false,\"delay\":10,\"delayMod\":1.500000,\"direction\":\"BACKWARD\",\"distance\":45,\"endPixel\":200,\"id\":\"TEST\",\"spacing\":5,\"startPixel\":15}" {
		t.Fail()
	}
}
