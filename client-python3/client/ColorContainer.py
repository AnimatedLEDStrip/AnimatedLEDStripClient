

class ColorContainer(object):

    def __init__(self):
        self.colors = []

    def add_color(self, color):
        if not isinstance(color, int):
            raise ValueError("Bad data type: color")
        self.colors.append(color)
