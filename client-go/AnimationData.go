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
	"encoding/json"
	"fmt"
	"strconv"
	"strings"
)

type animationData struct {
	Animation  Animation
	Colors     []*ColorContainer
	Center     int
	Continuous Continuous
	Delay      int
	DelayMod   float64
	Direction  Direction
	Distance   int
	EndPixel   int
	Id         string
	Spacing    int
	StartPixel int
}

func AnimationData() *animationData {
	return &animationData{
		Animation:  COLOR,
		Center:     -1,
		Continuous: DEFAULT,
		Delay:      -1,
		DelayMod:   1.0,
		Direction:  FORWARD,
		Distance:   -1,
		EndPixel:   -1,
		Id:         "",
		Spacing:    -1,
		StartPixel: 0,
	}
}

func (d *animationData) SetAnimation(anim Animation) *animationData {
	d.Animation = anim
	return d
}

func (d *animationData) AddColor(color *ColorContainer) *animationData {
	d.Colors = append(d.Colors, color)
	return d
}

func (d *animationData) SetCenter(pixel int) *animationData {
	d.Center = pixel
	return d
}

func (d *animationData) SetContinuous(c Continuous) *animationData {
	d.Continuous = c
	return d
}

func (d *animationData) SetDelay(time int) *animationData {
	d.Delay = time
	return d
}

func (d *animationData) SetDelayMod(multiplier float64) *animationData {
	d.DelayMod = multiplier
	return d
}

func (d *animationData) SetDirection(dir Direction) *animationData {
	d.Direction = dir
	return d
}

func (d *animationData) SetDistance(pixels int) *animationData {
	d.Distance = pixels
	return d
}

func (d *animationData) SetEndPixel(pixel int) *animationData {
	d.EndPixel = pixel
	return d
}

func (d *animationData) SetID(i string) *animationData {
	d.Id = i
	return d
}

func (d *animationData) SetSpacing(pixels int) *animationData {
	d.Spacing = pixels
	return d
}

func (d *animationData) SetStartPixel(pixel int) *animationData {
	d.StartPixel = pixel
	return d
}

func (d animationData) Json() string {
	var stringParts []string
	stringParts = append(stringParts, "DATA:{")
	stringParts = append(stringParts, `"animation":"`)
	stringParts = append(stringParts, d.Animation.String())
	stringParts = append(stringParts, `","colors":[`)

	if len(d.Colors) != 0 {
		for _, c := range d.Colors {
			stringParts = append(stringParts, c.Json())
			stringParts = append(stringParts, ",")
		}
		stringParts = stringParts[:len(stringParts)-1]
	}

	stringParts = append(stringParts, `],"center":`)
	stringParts = append(stringParts, strconv.Itoa(d.Center))
	stringParts = append(stringParts, `,"continuous":`)
	stringParts = append(stringParts, d.Continuous.String())
	stringParts = append(stringParts, `,"delay":`)
	stringParts = append(stringParts, strconv.Itoa(d.Delay))
	stringParts = append(stringParts, `,"delayMod":`)
	stringParts = append(stringParts, fmt.Sprintf("%f", d.DelayMod))
	stringParts = append(stringParts, `,"direction":"`)
	stringParts = append(stringParts, d.Direction.String())
	stringParts = append(stringParts, `","distance":`)
	stringParts = append(stringParts, strconv.Itoa(d.Distance))
	stringParts = append(stringParts, `,"endPixel":`)
	stringParts = append(stringParts, strconv.Itoa(d.EndPixel))
	stringParts = append(stringParts, `,"id":"`)
	stringParts = append(stringParts, d.Id)
	stringParts = append(stringParts, `","spacing":`)
	stringParts = append(stringParts, strconv.Itoa(d.Spacing))
	stringParts = append(stringParts, `,"startPixel":`)
	stringParts = append(stringParts, strconv.Itoa(d.StartPixel))
	stringParts = append(stringParts, "}")

	return strings.Join(stringParts, "")
}

func FromJson(data string) *animationData {
	animData := AnimationData()

	dataStr := strings.TrimPrefix(data, "DATA:")
	var animJson interface{}
	_ = json.Unmarshal([]byte(dataStr), &animJson)
	d := animJson.(map[string]interface{})

	animation, _ := d["animation"].(string)
	animData.Animation = AnimationFromString(animation)
	// No need to specify a default here because
	// AnimationFromString will return COLOR for an empty string

	colors, _ := d["colors"].([]interface{})
	for _, cc := range colors {
		animData.AddColor(ColorContainerFromJson(cc))
	}

	center, ok := d["center"].(float64)
	if !ok {
		center = -1
	}
	animData.Center = int(center)

	continuous, _ := d["continuous"]
	switch t := continuous.(type) {
	case nil:
		animData.Continuous = DEFAULT
	case bool:
		if t {
			animData.Continuous = CONTINUOUS
		} else {
			animData.Continuous = NONCONTINUOUS
		}
	default:
		animData.Continuous = DEFAULT
	}

	delay, ok := d["delay"].(float64)
	if !ok {
		delay = -1
	}
	animData.Delay = int(delay)

	delayMod, ok := d["delayMod"].(float64)
	if !ok {
		delayMod = 1.0
	}
	animData.DelayMod = delayMod

	direction, _ := d["direction"].(string)
	animData.Direction = DirectionFromString(direction)
	// No need to specify a default here because
	// DirectionFromString returns FORWARD for an
	// empty string

	distance, ok := d["distance"].(float64)
	if !ok {
		distance = -1
	}
	animData.Distance = int(distance)

	endPixel, ok := d["endPixel"].(float64)
	if !ok {
		endPixel = -1
	}
	animData.EndPixel = int(endPixel)

	id, _ := d["id"].(string)
	animData.Id = id
	// No need to specify a default here because
	// default for id is an empty string

	spacing, ok := d["spacing"].(float64)
	if !ok {
		spacing = -1
	}
	animData.Spacing = int(spacing)

	startPixel, _ := d["startPixel"].(float64)
	animData.StartPixel = int(startPixel)
	// No need to specify a default here because
	// default for spacing is 0

	return animData
}
