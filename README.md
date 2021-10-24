# Spring Cloud Stream for kafka Streams

### Apache Kafka Setup

Download a **binary package** of Apache Kafka (e.g., `kafka_2.13-2.8.0.tgz`) from `https://kafka.apache.org/downloads`.

**[IMPORTANT] Do not recommend to use the recently released Kafka 3.0.0!**

#### Linux and MacOS
In the Terminal, `cd` to the unzip folder, and start Kakfa with the following commands (each in a separate Terminal session):
```bash
./bin/zookeeper-server-start.sh
```
```bash
./bin/kafka-server-start.sh
```
 
#### Windows 
Unzip the Kakfa package to 
such a directory as `C:\kafka`&mdash;Windows does not like a complex path name (!). 
Use the following two commands in the Windows CMD (one in each window) to start Kafka:
```bash
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
```
```bash
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```

#### Kafka Topic Data
Sometimes you need to clean up data in the Kafka topics to start over. For this purpose, in Linux/MacOS, delete the folders `/tmp/zookeeper`, `/tmp/kafka-logs` and `/tmp/kafka-streams`. In Windows, delete the folders  `C:\tmp\zookeeper`,  `C:\tmp\kafka-logs` and `C:\tmp\kafka-streams`.

## The Applications 
The producer application gets `Appliance` JSON data from a public [API](https://random-data-api.com/api/appliance/random_appliance) and 
publishes it to a Kafka topic named `appliance-topic`. 
The processor application subscribes to that Kafka topic, computes a total count for each (appliance) `Brand` 
and publishes the results to another Kafka topic named `brand-topic`. 
The processor application also implements two state stores for interactive queries which can be accessed via REST requests (see below).
The consumer application simply subscribes to `brand-topic` and logs the records.

### Interactive queries
After running the producer and processor, 
to get a list of `Brand` names, request the following URL:
```url
http://localhost:8183/brands/all
```
To get the updating counts for a `Brand` (e.g. `IKEA`), request the following URL:
```url
http://localhost:8183/brand/IKEA/quantity
```
To get a list of equipments (names) for a `Brand` (e.g. `IKEA`), request the following URL:
```url
http://localhost:8183/brand/IKEA/equipments
```
