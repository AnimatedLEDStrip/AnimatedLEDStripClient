from client.AnimationSender import AnimationSender


def test_constructor():
    sender = AnimationSender("10.0.0.254", 5)

    assert sender.address == "10.0.0.254"
    assert sender.port == 5
