package animatedledstrip

import (
	"testing"
)

func TestAnimation_String(t *testing.T) {
	anim := COLOR
	if anim.String() != "COLOR" {
		t.Fail()
	}

	anim = CUSTOMANIMATION
	if anim.String() != "CUSTOMANIMATION" {
		t.Fail()
	}

	anim = CUSTOMREPETITIVEANIMATION
	if anim.String() != "CUSTOMREPETITIVEANIMATION" {
		t.Fail()
	}

	anim = ALTERNATE
	if anim.String() != "ALTERNATE" {
		t.Fail()
	}

	anim = BOUNCE
	if anim.String() != "BOUNCE" {
		t.Fail()
	}

	anim = BOUNCETOCOLOR
	if anim.String() != "BOUNCETOCOLOR" {
		t.Fail()
	}

	anim = CATTOY
	if anim.String() != "CATTOY" {
		t.Fail()
	}

	anim = METEOR
	if anim.String() != "METEOR" {
		t.Fail()
	}

	anim = MULTIPIXELRUN
	if anim.String() != "MULTIPIXELRUN" {
		t.Fail()
	}

	anim = MULTIPIXELRUNTOCOLOR
	if anim.String() != "MULTIPIXELRUNTOCOLOR" {
		t.Fail()
	}

	anim = RIPPLE
	if anim.String() != "RIPPLE" {
		t.Fail()
	}

	anim = PIXELMARATHON
	if anim.String() != "PIXELMARATHON" {
		t.Fail()
	}

	anim = PIXELRUN
	if anim.String() != "PIXELRUN" {
		t.Fail()
	}

	anim = SMOOTHCHASE
	if anim.String() != "SMOOTHCHASE" {
		t.Fail()
	}

	anim = SMOOTHFADE
	if anim.String() != "SMOOTHFADE" {
		t.Fail()
	}

	anim = SPARKLE
	if anim.String() != "SPARKLE" {
		t.Fail()
	}

	anim = SPARKLEFADE
	if anim.String() != "SPARKLEFADE" {
		t.Fail()
	}

	anim = SPARKLETOCOLOR
	if anim.String() != "SPARKLETOCOLOR" {
		t.Fail()
	}

	anim = SPLAT
	if anim.String() != "SPLAT" {
		t.Fail()
	}

	anim = STACK
	if anim.String() != "STACK" {
		t.Fail()
	}

	anim = STACKOVERFLOW
	if anim.String() != "STACKOVERFLOW" {
		t.Fail()
	}

	anim = WIPE
	if anim.String() != "WIPE" {
		t.Fail()
	}

	anim = ENDANIMATION
	if anim.String() != "ENDANIMATION" {
		t.Fail()
	}

	anim = -1
	if anim.String() != "COLOR" {
		t.Fail()
	}
}
