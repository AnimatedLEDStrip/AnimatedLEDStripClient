package animatedledstrip

/*
 *  Copyright (c) 2019 AnimatedLEDStrip
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

import "testing"

func TestAnimationData(t *testing.T) {
	data := AnimationData()

	if data.Animation != COLOR {
		t.Fail()
	} else if len(data.Colors) != 0 {
		t.Fail()
	} else if data.Center != -1 {
		t.Fail()
	} else if data.Continuous != DEFAULT {
		t.Fail()
	} else if data.Delay != -1 {
		t.Fail()
	} else if data.DelayMod != 1.0 {
		t.Fail()
	} else if data.Direction != FORWARD {
		t.Fail()
	} else if data.Distance != -1 {
		t.Fail()
	} else if data.EndPixel != -1 {
		t.Fail()
	} else if data.Id != "" {
		t.Fail()
	} else if data.Spacing != -1 {
		t.Fail()
	} else if data.StartPixel != 0 {
		t.Fail()
	}
}

func TestAnimationData_SetAnimation(t *testing.T) {
	data := AnimationData()
	data.SetAnimation(BOUNCE)

	if data.Animation != BOUNCE {
		t.Fail()
	}
}

func TestAnimationData_AddColor(t *testing.T) {
	cc := ColorContainer{}
	cc.AddColor(0xFF)

	data := AnimationData()
	data.AddColor(&cc)

	if len(data.Colors) != 1 {
		t.Fail()
	}
}

func TestAnimationData_SetCenter(t *testing.T) {
	data := AnimationData()
	data.SetCenter(50)

	if data.Center != 50 {
		t.Fail()
	}
}

func TestAnimationData_SetContinuous(t *testing.T) {
	data := AnimationData()
	data.SetContinuous(CONTINUOUS)

	if data.Continuous != CONTINUOUS {
		t.Fail()
	}
}

func TestAnimationData_SetDelay(t *testing.T) {
	data := AnimationData()
	data.SetDelay(200)

	if data.Delay != 200 {
		t.Fail()
	}
}

func TestAnimationData_SetDelayMod(t *testing.T) {
	data := AnimationData()
	data.SetDelayMod(2.0)

	if data.DelayMod != 2.0 {
		t.Fail()
	}
}

func TestAnimationData_SetDirection(t *testing.T) {
	data := AnimationData()
	data.SetDirection(BACKWARD)

	if data.Direction != BACKWARD {
		t.Fail()
	}
}

func TestAnimationData_SetDistance(t *testing.T) {
	data := AnimationData()
	data.SetDistance(35)

	if data.Distance != 35 {
		t.Fail()
	}
}

func TestAnimationData_SetEndPixel(t *testing.T) {
	data := AnimationData()
	data.SetEndPixel(25)

	if data.EndPixel != 25 {
		t.Fail()
	}
}

func TestAnimationData_SetID(t *testing.T) {
	data := AnimationData()
	data.SetID("TEST")

	if data.Id != "TEST" {
		t.Fail()
	}
}

func TestAnimationData_SetSpacing(t *testing.T) {
	data := AnimationData()
	data.SetSpacing(4)

	if data.Spacing != 4 {
		t.Fail()
	}
}

func TestAnimationData_SetStartPixel(t *testing.T) {
	data := AnimationData()
	data.SetStartPixel(5)

	if data.StartPixel != 5 {
		t.Fail()
	}
}

func TestAnimationData_Json(t *testing.T) {
	data := AnimationData()
	data.SetAnimation(METEOR)
	data.SetCenter(50)
	data.SetContinuous(NONCONTINUOUS)
	data.SetDelay(10)
	data.SetDelayMod(1.5)
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

func TestAnimationData_FromGoodJson(t *testing.T) {
	// Good JSON test

	jsonStr := "DATA:{\"animation\":\"METEOR\",\"colors\":[{\"colors\":[255,65280]},{\"colors\":[16711680]}],\"center\":50,\"continuous\":false,\"delay\":10,\"delayMod\":1.500000,\"direction\":\"BACKWARD\",\"distance\":45,\"endPixel\":200,\"id\":\"TEST\",\"spacing\":5,\"startPixel\":15}"

	data := FromJson(jsonStr)

	if data.Animation != METEOR {
		t.Fail()
	} else if len(data.Colors) != 2 {
		t.Fail()
	} else if len(data.Colors[0].Colors) != 2 {
		t.Fail()
	} else if len(data.Colors[1].Colors) != 1 {
		t.Fail()
	} else if data.Colors[0].Colors[0] != 0xFF {
		t.Fail()
	} else if data.Colors[0].Colors[1] != 0xFF00 {
		t.Fail()
	} else if data.Colors[1].Colors[0] != 0xFF0000 {
		t.Fail()
	} else if data.Center != 50 {
		t.Fail()
	} else if data.Continuous != NONCONTINUOUS {
		t.Fail()
	} else if data.Delay != 10 {
		t.Fail()
	} else if data.DelayMod != 1.5 {
		t.Fail()
	} else if data.Direction != BACKWARD {
		t.Fail()
	} else if data.Distance != 45 {
		t.Fail()
	} else if data.EndPixel != 200 {
		t.Fail()
	} else if data.Id != "TEST" {
		t.Fail()
	} else if data.Spacing != 5 {
		t.Fail()
	} else if data.StartPixel != 15 {
		t.Fail()
	}
}

func TestAnimationData_FromBadJson(t *testing.T) {
	// Bad JSON test

	jsonStr := "{}"

	data := FromJson(jsonStr)

	if data.Animation != COLOR {
		t.Fail()
	} else if len(data.Colors) != 0 {
		t.Fail()
	} else if data.Center != -1 {
		t.Fail()
	} else if data.Continuous != DEFAULT {
		t.Fail()
	} else if data.Delay != -1 {
		t.Fail()
	} else if data.DelayMod != 1.0 {
		t.Fail()
	} else if data.Direction != FORWARD {
		t.Fail()
	} else if data.Distance != -1 {
		t.Fail()
	} else if data.EndPixel != -1 {
		t.Fail()
	} else if data.Id != "" {
		t.Fail()
	} else if data.Spacing != -1 {
		t.Fail()
	} else if data.StartPixel != 0 {
		t.Fail()
	}

}

func TestAnimationData_ContinuousFromJson(t *testing.T) {
	// Tests for other continuous values

	jsonStr := "{\"continuous\":null}"

	data := FromJson(jsonStr)

	if data.Continuous != DEFAULT {
		t.Fail()
	}

	jsonStr = "{\"continuous\":true}"

	data = FromJson(jsonStr)

	if data.Continuous != CONTINUOUS {
		t.Fail()
	}

	jsonStr = "{\"continuous\":false}"

	data = FromJson(jsonStr)

	if data.Continuous != NONCONTINUOUS {
		t.Fail()
	}

	jsonStr = "{\"continuous\":-1}"

	data = FromJson(jsonStr)

	if data.Continuous != DEFAULT {
		t.Fail()
	}
}
