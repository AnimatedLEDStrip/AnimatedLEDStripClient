#   Copyright (c) 2019-2020 AnimatedLEDStrip
#
#   Permission is hereby granted, free of charge, to any person obtaining a copy
#   of this software and associated documentation files (the "Software"), to deal
#   in the Software without restriction, including without limitation the rights
#   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#   copies of the Software, and to permit persons to whom the Software is
#   furnished to do so, subject to the following conditions:
#
#   The above copyright notice and this permission notice shall be included in
#   all copies or substantial portions of the Software.
#
#   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#   THE SOFTWARE.

import json

from .Animation import *
from .ColorContainer import ColorContainer
from .Direction import *


class AnimationData(object):

    def __init__(self):
        self.animation = Animation.COLOR
        self.colors = []
        self.center = -1
        self.continuous = None
        self.delay = -1
        self.delayMod = 1.0
        self.direction = Direction.FORWARD
        self.distance = -1
        self.endPixel = -1
        self.id = ""
        self.spacing = -1
        self.startPixel = 0

    def add_color(self, color):
        if not isinstance(color, ColorContainer):
            raise ValueError("Bad data type: color")
        self.colors.append(color)

    def colors_json(self):
        if len(self.colors) == 0:
            return ""
        else:
            arr_str = ""
            for color in self.colors:
                arr_str = arr_str + color.json() + ","
            return arr_str[:-1]

    def json(self):
        self.check_data_types()

        return "DATA:{" + '"animation":"{}","colors":[{}],"center":{},"continuous":{},"delay":{},"delayMod":{},' \
                          '"direction":"{}","distance":{},"endPixel":{},"id":"{}","spacing":{},"startPixel":{}' \
            .format(self.animation.name,
                    self.colors_json(),
                    str(self.center),
                    "null" if self.continuous is None else str(self.continuous),
                    str(self.delay),
                    str(self.delayMod),
                    self.direction.name,
                    str(self.distance),
                    str(self.endPixel),
                    self.id,
                    str(self.spacing),
                    str(self.startPixel)
                    ) + "}"

    def from_json(self, input_str):
        input_json = json.loads(input_str[5:])
        self.animation = Animation[input_json.get('animation', self.animation.name)]
        self.colors = [ColorContainer().from_json(cc_arr) for cc_arr in input_json.get('colors', [])]
        self.center = input_json.get('center', self.center)
        self.continuous = input_json.get('continuous', self.continuous)
        self.delay = input_json.get('delay', self.delay)
        self.delayMod = input_json.get('delayMod', self.delayMod)
        self.direction = Direction[input_json.get('direction', self.direction)]
        self.distance = input_json.get('distance', self.distance)
        self.endPixel = input_json.get('endPixel', self.endPixel)
        self.id = input_json.get('id', self.id)
        self.spacing = input_json.get('spacing', self.spacing)
        self.startPixel = input_json.get('startPixel', self.startPixel)

        self.check_data_types()
        return self

    def check_data_types(self):
        def bad_data_type(name, data_type):
            raise ValueError("Bad data type for {}: {}".format(name, str(data_type)))

        if not isinstance(self.animation, Animation):
            bad_data_type("animation", type(self.animation))
        for color_container in self.colors:
            if not isinstance(color_container, ColorContainer):
                bad_data_type("color", type(color_container))
        if not isinstance(self.center, int):
            bad_data_type("center", type(self.center))
        if not isinstance(self.continuous, bool) and self.continuous is not None:
            bad_data_type("continuous", type(self.continuous))
        if not isinstance(self.delay, int):
            bad_data_type("delay", type(self.delay))
        if not isinstance(self.delayMod, float):
            bad_data_type("delayMod", type(self.delayMod))
        if not isinstance(self.direction, Direction):
            bad_data_type("direction", type(self.direction))
        if not isinstance(self.distance, int):
            bad_data_type("distance", type(self.distance))
        if not isinstance(self.endPixel, int):
            bad_data_type("endPixel", type(self.endPixel))
        if not isinstance(self.id, str):
            bad_data_type("id", type(self.id))
        if not isinstance(self.spacing, int):
            bad_data_type("spacing", type(self.spacing))
        if not isinstance(self.startPixel, int):
            bad_data_type("startPixel", type(self.startPixel))
