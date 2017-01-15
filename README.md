# oaktree
Oaktree is a maven plugin witch can output generate full dependence tree. 
The generated YAML file can be shown as graphic by [mindmapit](http://josetomastocino.github.io/mindmapit/)

### Usage
>currently havn't commit to the central Repository.
```
mvn io.github.wanghuayao:maven-plugin-oaktree:0.0.1-alpha-1:dependency-artifacts [OPTIONS]
```
##### OPTIONS

|OPTION |DEFAULT| DESCRIBE	|   EXAMPLE	|  
|---	|---	|---	|---	|  
| like 	|    | the Regex value, match \[groupid:artifactId:packaging:version:scope\] then include.|  -Dlike=^apache* 	|
| hate 	|    | the Regex value, match \[groupid:artifactId:packaging:version:scope\] then exclude.|  -Dhate=^javax*	|  
| deep |  Integer.MAX_VALUE | the top of the dependence 	|  -Ddeep=1 	|  



### View result
the dependence RESULT is place in target/oaktree/oaktree.txt  
copy the content of oaktree.txt and past it to [mindmapit](http://josetomastocino.github.io/mindmapit/) page left bottom textbox  
then the dependence graphy is shown at the right panel.

#### add to pom file
```xml
<build>
    <plugins>
        <!--  .... -->
        <plugin>
            <groupId>io.github.wanghuayao</groupId>
            <artifactId>maven-plugin-oaktree</artifactId>
            <version>[version]</version>
        </plugin>
        <!--  .... -->
    </plugins>
</build>
```

#### out put example
```yaml
- io.github.wanghuayao:maven-plugin-oaktree:maven-plugin:0.0.1-alpha-1:default 
  - org.apache.maven:maven-project:jar:2.2.1:compile 
    - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
      - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
        - junit:junit:jar:3.8.2:test 
      - junit:junit:jar:3.8.2:test 
    - org.apache.maven:maven-artifact:jar:2.2.1:compile 
      - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
        - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.2:test 
      - junit:junit:jar:3.8.1:test 
      - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:test 
        - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
        - junit:junit:jar:3.8.1:compile 
        - classworlds:classworlds:jar:1.1-alpha-2:compile 
    - org.apache.maven:maven-settings:jar:2.2.1:compile 
      - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
        - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.2:test 
      - junit:junit:jar:3.8.1:test 
      - org.codehaus.plexus:plexus-interpolation:jar:1.11:compile 
        - junit:junit:jar:3.8.2:test 
      - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
        - junit:junit:jar:3.8.1:compile 
        - classworlds:classworlds:jar:1.1-alpha-2:compile 
      - org.apache.maven:maven-model:jar:2.2.1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
          - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
            - junit:junit:jar:3.8.2:test 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.1:test 
    - junit:junit:jar:3.8.1:test 
    - org.codehaus.plexus:plexus-interpolation:jar:1.11:compile 
      - junit:junit:jar:3.8.2:test 
    - org.apache.maven:maven-artifact-test:jar:2.2.1:test 
    - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:compile 
      - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
      - junit:junit:jar:3.8.1:compile 
      - classworlds:classworlds:jar:1.1-alpha-2:compile 
    - org.apache.maven:maven-artifact-manager:jar:2.2.1:compile 
      - org.apache.maven.wagon:wagon-provider-api:jar:1.0-beta-6:compile 
        - easymock:easymock:jar:1.2_Java1.3:test 
        - org.codehaus.plexus:plexus-utils:jar:1.4.2:compile 
          - junit:junit:jar:3.8.1:test 
        - junit:junit:jar:3.8.1:test 
      - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
        - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.2:test 
      - org.apache.maven.wagon:wagon-file:jar:1.0-beta-6:test 
      - org.apache.maven:maven-artifact:jar:2.2.1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
          - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
            - junit:junit:jar:3.8.2:test 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.1:test 
        - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:test 
          - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
          - junit:junit:jar:3.8.1:compile 
          - classworlds:classworlds:jar:1.1-alpha-2:compile 
      - easymock:easymock:jar:1.2_Java1.3:test 
      - edu.umd.cs.mtc:multithreadedtc-jdk14:jar:1.01:test 
      - org.apache.maven.wagon:wagon-http:jar:1.0-beta-6:test 
      - backport-util-concurrent:backport-util-concurrent:jar:3.1:compile 
      - junit:junit:jar:3.8.1:test 
      - org.apache.maven:maven-repository-metadata:jar:2.2.1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
          - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
            - junit:junit:jar:3.8.2:test 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.1:test 
      - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
        - junit:junit:jar:3.8.1:compile 
        - classworlds:classworlds:jar:1.1-alpha-2:compile 
    - org.apache.maven:maven-profile:jar:2.2.1:compile 
      - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
        - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.2:test 
      - junit:junit:jar:3.8.1:test 
      - org.codehaus.plexus:plexus-interpolation:jar:1.11:compile 
        - junit:junit:jar:3.8.2:test 
      - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
        - junit:junit:jar:3.8.1:compile 
        - classworlds:classworlds:jar:1.1-alpha-2:compile 
      - org.apache.maven:maven-model:jar:2.2.1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
          - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
            - junit:junit:jar:3.8.2:test 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.1:test 
    - org.apache.maven:maven-model:jar:2.2.1:compile 
      - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
        - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.2:test 
      - junit:junit:jar:3.8.1:test 
    - org.apache.maven:maven-plugin-registry:jar:2.2.1:compile 
      - org.codehaus.plexus:plexus-utils:jar:1.5.15:compile 
        - org.codehaus.plexus:plexus-interpolation:jar:1.11:provided 
          - junit:junit:jar:3.8.2:test 
        - junit:junit:jar:3.8.2:test 
      - junit:junit:jar:3.8.1:test 
      - org.codehaus.plexus:plexus-container-default:jar:1.0-alpha-9-stable-1:compile 
        - org.codehaus.plexus:plexus-utils:jar:1.0.4:compile 
        - junit:junit:jar:3.8.1:compile 
        - classworlds:classworlds:jar:1.1-alpha-2:compile 
  - junit:junit:jar:3.8.1:test 
  - org.apache.maven:maven-artifact:jar:3.0:compile 
    - junit:junit:jar:3.8.2:test 
    - org.codehaus.plexus:plexus-utils:jar:2.0.4:compile 
      - junit:junit:jar:3.8.2:test 
```