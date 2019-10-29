from client.AnimationData import AnimationData
from client.Animation import *
from client.Direction import *


def test_constructor():
    data = AnimationData()

    assert data.animation == Animation.COLOR
    assert data.center == -1
    assert data.continuous is None
    assert data.delay == -1
    assert data.delayMod == 1.0
    assert data.direction == Direction.FORWARD
    assert data.distance == -1
    assert data.endPixel == -1
    assert data.id == ""
    assert data.spacing == -1
    assert data.startPixel == 0
