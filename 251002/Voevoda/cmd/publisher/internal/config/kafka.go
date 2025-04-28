package config

type Kafka struct {
	Brokers  []string `mapstructure:"brokers" validate:"required,min=1,dive"`
	InTopic  string   `mapstructure:"in_topic" validate:"required"`
	OutTopic string   `mapstructure:"out_topic" validate:"required"`
}
