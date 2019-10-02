formit

##Overview

 ver 0.0.1-SNAPSHOT 
 
 - Will provision an environment by overloading a cloud / template definition with deployment profile   
 - In a predefined sequence will execute remote tasks to install a Tomcat 6 and MySQL DB Server in two VMs
 - Configure a script that runs on the tomcat server to configure the data source
 - Deploy an application on the Tomcat Server 

 Component and Step definitions will not be expressed as xml file
 
Further detail (optional)

## Binary Releases

You can find published releases (compiled for Java 6 and above) on JPaaS production release 

    <dependency>
        <groupId>com.typesafe</groupId>
        <artifactId>config</artifactId>
        <version>0.5.2</version>
    </dependency>

## Release Notes

Please see RELEASE_NOTE.md in this directory,
RELEASE_NOTE.md

## API docs

 - Online: Online API Documentation URL
 - also published in jar form
 - consider reading this README first for an intro
 

## Bugs and Patches

Mention where bugs and patches can be submitted

## Build

Mention the CI instance, runtime required and dependencies requires for test execution

## API Example

    Provide API usage example

## Longer Examples

See the examples in the `examples/` directory.

You can run these from the sbt console with the commands `project
simple-app` and then `run`.

### Examples for using the utility


#### Optional system or env variable overrides

## Debugging

Debugging information

## Java version

Currently the library is maintained against Java 6. It does not
build with Java 5.

## Rationale

(For the curious.)

A brief idea behind the working strategy of the component
