package dsl

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
