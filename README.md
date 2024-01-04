# WHAT ARE QUARKUS APP TEMPLATES?

Resuable app templates containing cross-cutting concerns like scalability, availability, best practices, security,observability.

The app template will be based on Source and Sink with extensible business logic. 

Inspiration is from spotify backstage app templates.

# WHY

Quarkus is not a mere framework approach. It is a platform approach to solving problems in a reactive fashion. Now let us look at a trusted public benchmark site i.e. -> https://www.techempower.com/benchmarks/ . Let us compare a Reactive Spring based JDBC application vs a Reactive Quarkus based application. Both use postgres db.

There is a 10x difference inspite of being both reactive.

Why? Let us deep dive into details.

To make sure you get performance and memory footprint to likes of golang, all famous db drivers are vertically integrated with vertx event loop threadpool (that is what quarkus uses underneath).

Basically the whole async definition is rewritten by quarkus by essentially not using any other hidden threadpool. Everything vertically integrates with vertx event pool.

Other frameworks/libs which simply handover the request to another threadpool will never be able to achieve similar performance and memory utilization.

Remember in java it is incredibly important not to use another threadpool/thread as the minimum stack size is 1 mb for each thread and there is a negative performance overhead when context switching.

Also, remember distributive computing frameworks like Apache Spark, flink etc use only 2x amount of threads where x is number of cpu cores alloted in your application. Even the Java Fork/Join api works in the same fashion.

This means instead of using ton of threads, quarkus brings same performance by sticking to strong fundamentals of cores to threads ratio.

The second biggest difference that all annotations in quarkus do not use reflections which have a bad reputation for performance like spring especially in native mode with graalvm.

Please refer: https://dzone.com/articles/think-twice-before-using-reflection to understand why ?

The third fundamental difference is incredible startup time and memory overhead in quarkus for native mode compared to spring in native mode.

Why is that? What is happening underneath?

The central idea behind Quarkus is to do at build-time what traditional frameworks do at runtime: configuration parsing, classpath scanning, feature toggle based on classloading, and so on.

Quarkus Native application only contains the classes used at runtime out of ton of classes mentioned in your pom file. How and Why?

Process in Quarkus: In traditional frameworks, all the classes required to perform the initial application deployment hang around for the application’s life, even though they are only used once. With Quarkus, they are not even loaded into the production JVM!

During the build-time processing, it prepares the initialization of all components used by your application.

Consequence: It results in less memory usage and faster startup time as all metadata processing has already been done.

Refer: https://quarkus.io/vision/container-first for more in depth information how native builds in quarkus are way ahead in terms to spring native.

Kudos to Quarkus for redefining the future of java.

# HOW

## SCOPE/ROADMAP
 - GENERIC OBSERVABILITY GRAFANA DASHBOARDS. 
 - MTLS BAKED IN WITH INTEGRATION WITH LINKERD
 - KEDA BASED AUTOSCALING BASED ON SERVICE TEMPLATE SOURCE. So for instance if a template source is http then it will have keda http scaler.
 - PERFORMANCE TEST BASED ON K6 Framework BAKED IN AS PART OF CI/CD PIPELINE.
 - LOCAL STACK EXPERIENCE WITH TILT.
 - CHAOS TESTING BAKED IN AS PART OF CI/CD PIPELINE.
 - CONTAINER VULNERABILITY MANAGEMENT BAKED IN AS PART OF CI/CD
 - CONTIOUS UPDATES BAKED IN WITH UPSTREAM QUARKUS VERSION INTEGRATION. THIS IS PART OF CI/CD.
 - SUPER TOKENS INTEGRATION FOR USER AUTHENTICATION IF REQUIRED.
 - POLICY MANAGEMENT USING OPA.
 - Signed Docker Images using notary.
 - Container Image management using GoHarbor.
 - Container Vulnerability Management using Trivy.
 - Helm Chart Security Management using KubeScape.
 - Junits 5 Baked In
 - BDD Templates using Cucumber Baked In.
 - Helm Reconcilation Using Flux/CD Baked In.
 - Cost Monitoring baked In.
 - Spotify Backstage App/Developer Portal Template Integration for Easy Discovery and Collaboration across Teams.

