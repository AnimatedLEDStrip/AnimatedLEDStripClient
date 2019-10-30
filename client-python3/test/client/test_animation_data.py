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


def test_animation():
    data = AnimationData()

    data.animation = Animation.SPARKLE
    assert data.animation == Animation.SPARKLE

    try:
        data.animation = 3
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_center():
    data = AnimationData()

    data.center = 5
    assert data.center == 5

    try:
        data.center = Direction.FORWARD
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_continuous():
    data = AnimationData()

    data.continuous = True
    assert data.continuous

    data.continuous = None
    assert data.continuous is None

    try:
        data.continuous = 5
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_delay():
    data = AnimationData()

    data.delay = 10
    assert data.delay == 10

    try:
        data.delay = Animation.BOUNCE
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_delaymod():
    data = AnimationData()

    data.delayMod = 0.5
    assert data.delayMod == 0.5

    try:
        data.delayMod = 3
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_direction():
    data = AnimationData()

    data.direction = Direction.BACKWARD
    assert data.direction == Direction.BACKWARD

    try:
        data.direction = 1
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_distance():
    data = AnimationData()

    data.distance = 5
    assert data.distance == 5

    try:
        data.distance = 5.0
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_endpixel():
    data = AnimationData()

    data.endPixel = 10
    assert data.endPixel == 10

    try:
        data.endPixel = 30.0
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_id():
    data = AnimationData()

    data.id = "TEST"
    assert data.id == "TEST"

    try:
        data.id = 5
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_spacing():
    data = AnimationData()

    data.spacing = 10
    assert data.spacing == 10

    try:
        data.spacing = 3.0
        data.json()
        raise AssertionError
    except ValueError:
        pass


def test_start_pixel():
    data = AnimationData()

    data.startPixel = 5
    assert data.startPixel == 5

    try:
        data.startPixel = 1.0
        data.json()
        raise AssertionError
    except ValueError:
        pass
