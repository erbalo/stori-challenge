# Stori challennge

## A little beginning ####

>If at first you don't succeed, call it version 1.0 __~ Erick Barrera__

### Author ####
* Erick Barrera - **ebarreral.isc@gmail.com**

## Technology stack ###

- Java 11
- Maven
- Spring
- Docker
- DynamoDB
- RabbitMQ

## Requirements

- Operating System based on UNIX
- Docker
- AWS CLI

## Proposal architecture

![Architecture](stori-challenge.jpg)

### How to run the application? ###

To run the entire system correctly, you will need to use __two terminals__.

In the first terminal you must follow the following instructions:

1. run `make services` (this may take a few minutes the first time, as it will create the necessary images for its execution)

>__NOTE:__ The services will be ready for execution when in the first terminal you see the message: *"Started XApplication in Y seconds (JVM running for ..)"*

In the second terminal you must follow the following instructions:

1. run `make tables` (this will create the dynamo tables the system requires)
2. run `make app` (it will be the initial application of the system, where the system will do the actions that the user enters)

>__NOTE:__ It's important that you read the instructions for the second terminal (interactive menu), as you must enter the correct data for its correct use.

### Please read this section

In the second terminal ("make app") you have 3 options:

1. Read CSV file

In this process, as a first step, you will be asked for the email where the transaction reading report will be sent.

The second step will be to include the path where the file is located, being dockerized and running in a container, the image was created to create an anchor volume on the root where this project is located and you must place the name of the internal volume + the file name.

Ex.

```shell
/file-repository/transacciones-chidas.csv
or
/file-repository/transacciones-fantasma.csv
```

In your folder, where you have cloned this repository, a temporary `/tmp/transactions` folder will be created and it is in this folder that you should place your files from your local computer.

Ex.

```shell
/Users/mago-justiciero/stori-challenge/tmp/transactions/transacciones-chidas.csv
or
/Users/mago-justiciero/stori-challenge/tmp/transactions/transacciones-fantasma.csv
```

The above is managed by the __*Makefile*__ and by the clause that has `$(shell pwd)/tmp/transactions:/file-repository`.

You can change the first parameter, but not the second (__file-repository__) since it was created from the base of the image

2. Send email
3. End application

## Considerations

You can terminate and execute the `make app` command as many times as you like, but if the first terminal (`make services`) is destroyed or terminates its process with CTRL+C you will have to start the process again.