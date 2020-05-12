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
from typing import List, Optional, AnyStr

from .animation import *
from .color_container import ColorContainer
from .direction import *
from .utils import check_data_type, nullable_str


class AnimationData(object):

    def __init__(self):
        self.animation: 'Animation' = Animation.COLOR
        self.colors: List['ColorContainer'] = []
        self.center: int = -1
        self.continuous: Optional[bool] = None
        self.delay: int = -1
        self.delay_mod: float = 1.0
        self.direction: 'Direction' = Direction.FORWARD
        self.distance: int = -1
        self.end_pixel: int = -1
        self.id: str = ''
        self.spacing: int = -1
        self.start_pixel: int = 0

    def add_color(self, color: 'ColorContainer') -> 'AnimationData':
        if check_data_type('color', color, ColorContainer) is True:
            self.colors.append(color)
        return self

    def colors_json(self) -> str:
        return ','.join([color.json() for color in self.colors])

    def json(self) -> str:
        if self.check_data_types() is False:
            return ''
        else:
            return ''.join(['DATA:{',
                            ','.join([
                                '"animation":"{}"'.format(self.animation.name),
                                '"colors":[{}]'.format(self.colors_json()),
                                '"center":{}'.format(str(self.center)),
                                '"continuous":{}'.format(nullable_str(self.continuous)),
                                '"delay":{}'.format(str(self.delay)),
                                '"delayMod":{}'.format(str(self.delay_mod)),
                                '"direction":"{}"'.format(self.direction.name),
                                '"distance":{}'.format(str(self.distance)),
                                '"endPixel":{}'.format(str(self.end_pixel)),
                                '"id":"{}"'.format(self.id),
                                '"spacing":{}'.format(str(self.spacing)),
                                '"startPixel":{}'.format(str(self.start_pixel))]),
                            '}'])

    @classmethod
    def from_json(cls, input_str: AnyStr) -> 'AnimationData':
        input_json = json.loads(input_str[5:])

        new_instance = cls()
        new_instance.animation = Animation[input_json.get('animation', new_instance.animation.name)]
        new_instance.colors = [ColorContainer.from_json(cc_arr) for cc_arr in input_json.get('colors', [])]
        new_instance.center = input_json.get('center', new_instance.center)
        new_instance.continuous = input_json.get('continuous', new_instance.continuous)
        new_instance.delay = input_json.get('delay', new_instance.delay)
        new_instance.delay_mod = input_json.get('delayMod', new_instance.delay_mod)
        new_instance.direction = Direction[input_json.get('direction', new_instance.direction)]
        new_instance.distance = input_json.get('distance', new_instance.distance)
        new_instance.end_pixel = input_json.get('endPixel', new_instance.end_pixel)
        new_instance.id = input_json.get('id', new_instance.id)
        new_instance.spacing = input_json.get('spacing', new_instance.spacing)
        new_instance.start_pixel = input_json.get('startPixel', new_instance.start_pixel)

        new_instance.check_data_types()
        return new_instance

    def check_data_types(self) -> bool:
        good_types = True

        good_types = good_types and check_data_type('animation', self.animation, Animation)
        for color_container in self.colors:
            good_types = good_types and check_data_type('color', color_container, ColorContainer)
        good_types = good_types and check_data_type('center', self.center, int)
        good_types = good_types and check_data_type('continuous', self.continuous, bool, allow_none=True)
        good_types = good_types and check_data_type('delay', self.delay, int)
        good_types = good_types and check_data_type('delayMod', self.delay_mod, float)
        good_types = good_types and check_data_type('direction', self.direction, Direction)
        good_types = good_types and check_data_type('distance', self.distance, int)
        good_types = good_types and check_data_type('endPixel', self.end_pixel, int)
        good_types = good_types and check_data_type('id', self.id, str)
        good_types = good_types and check_data_type('spacing', self.spacing, int)
        good_types = good_types and check_data_type('startPixel', self.start_pixel, int)

        return good_types
