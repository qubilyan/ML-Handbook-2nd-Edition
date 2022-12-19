# Learning how to design automatically updating AI with Apache Kafka and Deeplearning4J.


This is the codebase for Chapter 12 of the Machine Learning Hands On book.

## Requirements
In order to run this system you require the following components:

* [Kafka](http://kafka.apache.org) (I'm using the supplied Zookeeper distribution).
* [MySQL](http://www.mysql.com)
* [Leiningen](https://leiningen.org/) (for building the Clojure projects)
* [Maven](http://maven.apache.org) (for building the Java projects) 
* Java 1.8 (parts of the system use Clojure 1.9 but there's no need to download this, Leiningen takes care of that for you)

## Breakdown of the project

The project uses a mixture of Java (for the model creation) and Clojure (for Kafka Streams and the HTTP API). The directory structure of this project is broken down as follows:

`config` - Kafka Connect configurations: one for the event_topic persistance and the other to save the training data ready for model training.

`crontab` - Scheduled jobs for model creation.

`db` - A schema for the MySQL database which holds information on the training and model accuracy and another table to hold the slope/intercept of a simple linear regression model.

`messages` - Simple JSON messages for the cronjob to send to the event stream.

`projects` - The main bulk of the coding is here in four projects: Model builds (`dlj4.mlp`), Kafka Streaming applications (`kafka.stream.events` and `kafka.stream.prediction`) and a very basic HTTP API (`prediction.http.api`)

`scripts` - Shell scripts to create the required Kafka topics, environment variables and event trigger for the cron job.

`slides` - Slides from the talk will be added once the t