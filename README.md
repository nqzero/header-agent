## add header via java agent



```
# either (automatic):
docker build -t bb/header .
image=bb/header

# or (low-bandwidth, requires local maven):
mvn clean package
image="-v $PWD/target/agent.jar:/agent.jar tomcat:8.0-alpine"



# and then:

args="--name tmp -d --rm -p 8080:8080"
agent="-e JAVA_OPTS='-javaagent:/agent.jar'"

docker run $args $image 2>&1 > /dev/null
sleep 2
curl -I localhost:8080
docker kill tmp

docker run $args $agent $image 2>&1 > /dev/null
sleep 2
curl -I localhost:8080
docker kill tmp

```


the 2 second delays should be sufficient on most machines,
but for slower machines allow accordingly

