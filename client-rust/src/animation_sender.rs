use std::net::{TcpStream, IpAddr, SocketAddr};
use std::time::Duration;
use std::io::Write;
use crate::animation_data::AnimationData;

pub struct AnimationSender {
    address: IpAddr,
    port: u16,
    connection: Option<TcpStream>,
    timeout: u64,
}

impl AnimationSender {
    pub fn new(address: IpAddr, port: u16) -> AnimationSender {
        AnimationSender {
            address,
            port,
            connection: None,
            timeout: 2,
        }
    }

    pub fn new_with_timeout(address: IpAddr, port: u16, timeout: u64) -> AnimationSender {
        AnimationSender {
            address,
            port,
            connection: None,
            timeout,
        }
    }

    pub fn start(&mut self) -> std::io::Result<()> {
        self.connection =
            Some(TcpStream::connect_timeout(&SocketAddr::new(self.address, self.port),
                                            Duration::from_secs(self.timeout))?);
        Ok(())
    }

    pub fn send_animation(&mut self, anim: &AnimationData) -> std::io::Result<()> {
        self.connection
            .as_ref()
            .expect("Stream is None")
            .write_all(anim.json().as_bytes())
    }
}

#[cfg(test)]
#[cfg_attr(tarpaulin, skip)]
mod tests {
    use crate::animation_sender::AnimationSender;
    use std::net::{IpAddr, Ipv4Addr};

    fn test_start() {
        let mut sender =
            AnimationSender::new(
                IpAddr::V4(Ipv4Addr::new(127, 0, 0, 1)),
                5);

        sender.start();
    }
}