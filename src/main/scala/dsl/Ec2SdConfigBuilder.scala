package dsl

import scala.concurrent.duration._

object Ec2SdConfigBuilder {
  def ec2(): Ec2SdConfigBuilder = Ec2SdConfigBuilder(None, None, None, None, None)
}

case class Ec2SdConfigBuilder(region: Option[String], accessKey: Option[String], secretKey: Option[String], refreshInterval: Option[Duration], port: Option[Int]) {
  def inRegion(region: String): Ec2SdConfigBuilder = copy(region = Some(region))

  def fromPort(port: Int): Ec2SdConfigBuilder = copy(port = Some(port))

  def withCredentials(accessKey: String, secretKey: String): Ec2SdConfigBuilder = copy(accessKey = Some(accessKey), secretKey = Some(secretKey))

  def every(d: Duration): Ec2SdConfigBuilder = copy(refreshInterval = Some(d))

  def build: Ec2SdConfig = Ec2SdConfig(
    region.getOrElse(""),
    accessKey.getOrElse(""),
    secretKey.getOrElse(""),
    refreshInterval.getOrElse(30 seconds),
    port.getOrElse(80)
  )
}
