# Wildfly K8s Demo

A demo of a clustering application that enables HTTP session data caching using the WildFly distributable session manager in Kubernetes.

Build and run
============= 

* To build: `mvn package`
* To build archived container image: `mvn k8s:build`
* To build K8s resources: `mvn k8s:resource`
* To build helm chart: `mvn k8s:helm`

Notes
=========

* https://github.com/wildfly-extras/wildfly-jar-maven-plugin
* https://github.com/eclipse/jkube
* https://github.com/GoogleContainerTools/jib

References
==========

* https://docs.wildfly.org/bootablejar/#wildfly_jar_configuring_cloud
* https://docs.wildfly.org/bootablejar/#wildfly_jar_advanced_slim
* https://docs.wildfly.org/bootablejar/#_out_of_memory_error_when_building
* https://www.eclipse.org/jkube/docs/kubernetes-maven-plugin/#generator-wildfly-jar
* https://www.eclipse.org/jkube/docs/kubernetes-maven-plugin/#ingress-generation
* https://github.com/jgroups-extras/jgroups-kubernetes
* https://docs.wildfly.org/27/Galleon_Guide.html#wildfly_layers
