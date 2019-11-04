package animatedledstrip

import (
	"fmt"
	"strconv"
	"strings"
)

type animationData struct {
	animation  Animation
	colors     []*ColorContainer
	center     int
	continuous Continuous
	delay      int
	delayMod   float32
	direction  Direction
	distance   int
	endPixel   int
	id         string
	spacing    int
	startPixel int
}

//noinspection GoExportedFuncWithUnexportedType
func AnimationData() animationData {
	return animationData{
		animation:  COLOR,
		center:     -1,
		continuous: DEFAULT,
		delay:      -1,
		delayMod:   1.0,
		direction:  FORWARD,
		distance:   -1,
		endPixel:   -1,
		id:         "",
		spacing:    -1,
		startPixel: 0,
	}
}

func (d *animationData) SetAnimation(anim Animation) *animationData {
	d.animation = anim
	return d
}

func (d *animationData) AddColor(color *ColorContainer) *animationData {
	d.colors = append(d.colors, color)
	return d
}

func (d *animationData) SetCenter(pixel int) *animationData {
	d.center = pixel
	return d
}

func (d *animationData) SetContinuous(c Continuous) *animationData {
	d.continuous = c
	return d
}

func (d *animationData) SetDelay(time int) *animationData {
	d.delay = time
	return d
}

func (d *animationData) SetDelayMod(multiplier float32) *animationData {
	d.delayMod = multiplier
	return d
}

func (d *animationData) SetDirection(dir Direction) *animationData {
	d.direction = dir
	return d
}

func (d *animationData) SetDistance(pixels int) *animationData {
	d.distance = pixels
	return d
}

func (d *animationData) SetEndPixel(pixel int) *animationData {
	d.endPixel = pixel
	return d
}

func (d *animationData) SetID(i string) *animationData {
	d.id = i
	return d
}

func (d *animationData) SetSpacing(pixels int) *animationData {
	d.spacing = pixels
	return d
}

func (d *animationData) SetStartPixel(pixel int) *animationData {
	d.startPixel = pixel
	return d
}

func (d animationData) Json() string {
	var stringParts []string
	stringParts = append(stringParts, "DATA:{")
	stringParts = append(stringParts, `"animation":"`)
	stringParts = append(stringParts, d.animation.String())
	stringParts = append(stringParts, `","colors":[`)

	for _, c := range d.colors {
		stringParts = append(stringParts, c.Json())
		stringParts = append(stringParts, ",")
	}
	stringParts = stringParts[:len(stringParts)-1]

	stringParts = append(stringParts, `],"center":`)
	stringParts = append(stringParts, strconv.Itoa(d.center))
	stringParts = append(stringParts, `,"continuous":`)
	stringParts = append(stringParts, d.continuous.String())
	stringParts = append(stringParts, `,"delay":`)
	stringParts = append(stringParts, strconv.Itoa(d.delay))
	stringParts = append(stringParts, `,"delayMod":`)
	stringParts = append(stringParts, fmt.Sprintf("%f", d.delayMod))
	stringParts = append(stringParts, `,"direction":"`)
	stringParts = append(stringParts, d.direction.String())
	stringParts = append(stringParts, `","distance":`)
	stringParts = append(stringParts, strconv.Itoa(d.distance))
	stringParts = append(stringParts, `,"endPixel":`)
	stringParts = append(stringParts, strconv.Itoa(d.endPixel))
	stringParts = append(stringParts, `,"id":"`)
	stringParts = append(stringParts, d.id)
	stringParts = append(stringParts, `","spacing":`)
	stringParts = append(stringParts, strconv.Itoa(d.spacing))
	stringParts = append(stringParts, `,"startPixel":`)
	stringParts = append(stringParts, strconv.Itoa(d.startPixel))
	stringParts = append(stringParts, "}")

	return strings.Join(stringParts, "")
}
