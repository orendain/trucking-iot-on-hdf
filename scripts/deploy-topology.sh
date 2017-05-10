#!/usr/bin/env bash

projectDir="$(cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd)"

storm jar $projectDir/trucking-storm-topology/target/trucking-storm-topology-0.3.2.jar com.orendainx.hortonworks.trucking.storm.topologies.KafkaToKafka