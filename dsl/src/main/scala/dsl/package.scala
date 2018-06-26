import configuration.Ec2Targets
import label.Label

import scala.concurrent.duration._
import scala.language.postfixOps

package object aws {

  object Ec2MetaLabels {
    def __meta_ec2_availability_zone: Label = Label("__meta_ec2_availability_zone")

    def __meta_ec2_instance_id: Label = Label("__meta_ec2_instance_id")

    def __meta_ec2_instance_state: Label = Label("__meta_ec2_instance_state")

    def __meta_ec2_instance_type: Label = Label("__meta_ec2_instance_type")

    def __meta_ec2_private_ip: Label = Label("__meta_ec2_private_ip")

    def __meta_ec2_public_dns_name: Label = Label("__meta_ec2_public_dns_name")

    def __meta_ec2_public_ip: Label = Label("__meta_ec2_public_ip")

    def __meta_ec2_subnet_id: Label = Label("__meta_ec2_subnet_id")

    def __meta_ec2_tag_(tagKey: String): Label = Label(s"__meta_ec2_tag_$tagKey")

    def __meta_ec2_vpc_id: Label = Label("__meta_ec2_vpc_id")
  }

  case class Ec2SdConfig(region: String, accessKey: String, secretKey: String, refreshInterval: Duration, port: Int) {}

  object Ec2SdConfigBuilder {
    def ec2(): Ec2SdConfigBuilder = Ec2SdConfigBuilder(None, None, None, None, None)
  }

  case class Ec2SdConfigBuilder(region: Option[String], accessKey: Option[String], secretKey: Option[String], refreshInterval: Option[Duration], port: Option[Int]) {
    def inRegion(region: String): Ec2SdConfigBuilder = copy(region = Some(region))

    def fromPort(port: Int): Ec2SdConfigBuilder = copy(port = Some(port))

    def withCredentials(ac: AwsCredentials): Ec2SdConfigBuilder = copy(accessKey = Some(ac.accessKey), secretKey = Some(ac.secretKey))

    def every(d: Duration): Ec2SdConfigBuilder = copy(refreshInterval = Some(d))

    def build: Ec2Targets = Ec2Targets(
      region.getOrElse(""),
      accessKey.getOrElse(""),
      secretKey.getOrElse(""),
      refreshInterval.getOrElse(30 seconds),
      port.getOrElse(80)
    )
  }

  case class AwsCredentials(accessKey: String, secretKey: String)

}
