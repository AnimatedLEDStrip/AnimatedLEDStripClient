package main

import (
	"net"
	"strconv"
)

type AnimationSender struct {
	ip         string
	port       int
	connection *net.Conn
}

func (s *AnimationSender) connect() {
	conn, err := net.Dial("tcp", s.ip+":"+strconv.Itoa(s.port))
	if err != nil {
		print("Error connecting")
	} else {
		s.connection = &conn
	}
}

func (s *AnimationSender) Start() {
	s.connect()
}

func (s *AnimationSender) End() {
	_ = (*s.connection).Close()
}

func (s *AnimationSender) SendAnimation(data animationData) {
	println("A")
	_, err := (*s.connection).Write([]byte(data.Json()))
	if err != nil {
		print("error sending")
	} else {
		print("sent")
	}
}
