package animatedledstrip

import (
	"testing"
)

func TestAnimation_String(t *testing.T) {
	anim := COLOR
	str := anim.String()
	if str != "COLOR" {
		t.Fail()
	}

	anim = CUSTOMANIMATION
	str = anim.String()
	if str != "CUSTOMANIMATION" {
		t.Fail()
	}

	anim = CUSTOMREPETITIVEANIMATION
	str = anim.String()
	if str != "CUSTOMREPETITIVEANIMATION" {
		t.Fail()
	}

	anim = ALTERNATE
	str = anim.String()
	if str != "ALTERNATE" {
		t.Fail()
	}

	anim = BOUNCE
	str = anim.String()
	if str != "BOUNCE" {
		t.Fail()
	}

	anim = BOUNCETOCOLOR
	str = anim.String()
	if str != "BOUNCETOCOLOR" {
		t.Fail()
	}

	anim = CATTOY
	str = anim.String()
	if str != "CATTOY" {
		t.Fail()
	}

	anim = METEOR
	str = anim.String()
	if str != "METEOR" {
		t.Fail()
	}

	anim = MULTIPIXELRUN
	str = anim.String()
	if str != "MULTIPIXELRUN" {
		t.Fail()
	}

	anim = MULTIPIXELRUNTOCOLOR
	str = anim.String()
	if str != "MULTIPIXELRUNTOCOLOR" {
		t.Fail()
	}

	anim = RIPPLE
	str = anim.String()
	if str != "RIPPLE" {
		t.Fail()
	}

	anim = PIXELMARATHON
	str = anim.String()
	if str != "PIXELMARATHON" {
		t.Fail()
	}

	anim = PIXELRUN
	str = anim.String()
	if str != "PIXELRUN" {
		t.Fail()
	}

	anim = SMOOTHCHASE
	str = anim.String()
	if str != "SMOOTHCHASE" {
		t.Fail()
	}

	anim = SMOOTHFADE
	str = anim.String()
	if str != "SMOOTHFADE" {
		t.Fail()
	}

	anim = SPARKLE
	str = anim.String()
	if str != "SPARKLE" {
		t.Fail()
	}

	anim = SPARKLEFADE
	str = anim.String()
	if str != "SPARKLEFADE" {
		t.Fail()
	}

	anim = SPARKLETOCOLOR
	str = anim.String()
	if str != "SPARKLETOCOLOR" {
		t.Fail()
	}

	anim = SPLAT
	str = anim.String()
	if str != "SPLAT" {
		t.Fail()
	}

	anim = STACK
	str = anim.String()
	if str != "STACK" {
		t.Fail()
	}

	anim = STACKOVERFLOW
	str = anim.String()
	if str != "STACKOVERFLOW" {
		t.Fail()
	}

	anim = WIPE
	str = anim.String()
	if str != "WIPE" {
		t.Fail()
	}

	anim = ENDANIMATION
	str = anim.String()
	if str != "ENDANIMATION" {
		t.Fail()
	}

	anim = -1
	str = anim.String()
	if str != "COLOR" {
		t.Fail()
	}
}
