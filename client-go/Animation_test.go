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

func TestAnimation_AnimationFromString(t *testing.T) {
	anim := "COLOR"
	if AnimationFromString(anim) != COLOR {
		t.Fail()
	}

	anim = "CUSTOMANIMATION"
	if AnimationFromString(anim) != CUSTOMANIMATION {
		t.Fail()
	}

	anim = "CUSTOMREPETITIVEANIMATION"
	if AnimationFromString(anim) != CUSTOMREPETITIVEANIMATION {
		t.Fail()
	}

	anim = "ALTERNATE"
	if AnimationFromString(anim) != ALTERNATE {
		t.Fail()
	}

	anim = "BOUNCE"
	if AnimationFromString(anim) != BOUNCE {
		t.Fail()
	}

	anim = "BOUNCETOCOLOR"
	if AnimationFromString(anim) != BOUNCETOCOLOR {
		t.Fail()
	}

	anim = "CATTOY"
	if AnimationFromString(anim) != CATTOY {
		t.Fail()
	}

	anim = "METEOR"
	if AnimationFromString(anim) != METEOR {
		t.Fail()
	}

	anim = "MULTIPIXELRUN"
	if AnimationFromString(anim) != MULTIPIXELRUN {
		t.Fail()
	}

	anim = "MULTIPIXELRUNTOCOLOR"
	if AnimationFromString(anim) != MULTIPIXELRUNTOCOLOR {
		t.Fail()
	}

	anim = "RIPPLE"
	if AnimationFromString(anim) != RIPPLE {
		t.Fail()
	}

	anim = "PIXELMARATHON"
	if AnimationFromString(anim) != PIXELMARATHON {
		t.Fail()
	}

	anim = "PIXELRUN"
	if AnimationFromString(anim) != PIXELRUN {
		t.Fail()
	}

	anim = "SMOOTHCHASE"
	if AnimationFromString(anim) != SMOOTHCHASE {
		t.Fail()
	}

	anim = "SMOOTHFADE"
	if AnimationFromString(anim) != SMOOTHFADE {
		t.Fail()
	}

	anim = "SPARKLE"
	if AnimationFromString(anim) != SPARKLE {
		t.Fail()
	}

	anim = "SPARKLEFADE"
	if AnimationFromString(anim) != SPARKLEFADE {
		t.Fail()
	}

	anim = "SPARKLETOCOLOR"
	if AnimationFromString(anim) != SPARKLETOCOLOR {
		t.Fail()
	}

	anim = "SPLAT"
	if AnimationFromString(anim) != SPLAT {
		t.Fail()
	}

	anim = "STACK"
	if AnimationFromString(anim) != STACK {
		t.Fail()
	}

	anim = "STACKOVERFLOW"
	if AnimationFromString(anim) != STACKOVERFLOW {
		t.Fail()
	}

	anim = "WIPE"
	if AnimationFromString(anim) != WIPE {
		t.Fail()
	}

	anim = "ENDANIMATION"
	if AnimationFromString(anim) != ENDANIMATION {
		t.Fail()
	}

	anim = "NONANIMATION"
	if AnimationFromString(anim) != COLOR {
		t.Fail()
	}
}
