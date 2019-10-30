from client.ColorContainer import ColorContainer


def test_colorcontainer():
    color = ColorContainer()

    color.add_color(0xFF)

    try:
        color.add_color(None)
        raise AssertionError
    except ValueError:
        pass
