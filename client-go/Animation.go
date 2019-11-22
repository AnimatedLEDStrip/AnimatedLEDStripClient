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

type Animation int

const (
	COLOR Animation = iota
	CUSTOMANIMATION
	CUSTOMREPETITIVEANIMATION
	ALTERNATE
	BOUNCE
	BOUNCETOCOLOR
	CATTOY
	METEOR
	MULTIPIXELRUN
	MULTIPIXELRUNTOCOLOR
	RIPPLE
	PIXELMARATHON
	PIXELRUN
	SMOOTHCHASE
	SMOOTHFADE
	SPARKLE
	SPARKLEFADE
	SPARKLETOCOLOR
	SPLAT
	STACK
	STACKOVERFLOW
	WIPE
	ENDANIMATION
)

func (a Animation) String() string {
	switch a {
	case COLOR:
		return "COLOR"
	case CUSTOMANIMATION:
		return "CUSTOMANIMATION"
	case CUSTOMREPETITIVEANIMATION:
		return "CUSTOMREPETITIVEANIMATION"
	case ALTERNATE:
		return "ALTERNATE"
	case BOUNCE:
		return "BOUNCE"
	case BOUNCETOCOLOR:
		return "BOUNCETOCOLOR"
	case CATTOY:
		return "CATTOY"
	case METEOR:
		return "METEOR"
	case MULTIPIXELRUN:
		return "MULTIPIXELRUN"
	case MULTIPIXELRUNTOCOLOR:
		return "MULTIPIXELRUNTOCOLOR"
	case RIPPLE:
		return "RIPPLE"
	case PIXELMARATHON:
		return "PIXELMARATHON"
	case PIXELRUN:
		return "PIXELRUN"
	case SMOOTHCHASE:
		return "SMOOTHCHASE"
	case SMOOTHFADE:
		return "SMOOTHFADE"
	case SPARKLE:
		return "SPARKLE"
	case SPARKLEFADE:
		return "SPARKLEFADE"
	case SPARKLETOCOLOR:
		return "SPARKLETOCOLOR"
	case SPLAT:
		return "SPLAT"
	case STACK:
		return "STACK"
	case STACKOVERFLOW:
		return "STACKOVERFLOW"
	case WIPE:
		return "WIPE"
	case ENDANIMATION:
		return "ENDANIMATION"
	default:
		return "COLOR"
	}
}

func AnimationFromString(anim string) Animation {
	switch anim {
	case "COLOR":
		return COLOR
	case "CUSTOMANIMATION":
		return CUSTOMANIMATION
	case "CUSTOMREPETITIVEANIMATION":
		return CUSTOMREPETITIVEANIMATION
	case "ALTERNATE":
		return ALTERNATE
	case "BOUNCE":
		return BOUNCE
	case "BOUNCETOCOLOR":
		return BOUNCETOCOLOR
	case "CATTOY":
		return CATTOY
	case "METEOR":
		return METEOR
	case "MULTIPIXELRUN":
		return MULTIPIXELRUN
	case "MULTIPIXELRUNTOCOLOR":
		return MULTIPIXELRUNTOCOLOR
	case "RIPPLE":
		return RIPPLE
	case "PIXELMARATHON":
		return PIXELMARATHON
	case "PIXELRUN":
		return PIXELRUN
	case "SMOOTHCHASE":
		return SMOOTHCHASE
	case "SMOOTHFADE":
		return SMOOTHFADE
	case "SPARKLE":
		return SPARKLE
	case "SPARKLEFADE":
		return SPARKLEFADE
	case "SPARKLETOCOLOR":
		return SPARKLETOCOLOR
	case "SPLAT":
		return SPLAT
	case "STACK":
		return STACK
	case "STACKOVERFLOW":
		return STACKOVERFLOW
	case "WIPE":
		return WIPE
	case "ENDANIMATION":
		return ENDANIMATION
	default:
		return COLOR
	}
}
