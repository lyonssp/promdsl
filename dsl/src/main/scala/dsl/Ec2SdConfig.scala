package dsl

import scala.concurrent.duration.Duration

case class Ec2SdConfig(region: String, accessKey: String, secretKey: String, refreshInterval: Duration, port: Int) {}
