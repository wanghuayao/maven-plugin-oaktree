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
            <version>0.0.1-alpha-1</version>
        </plugin>
        <!--  .... -->
    </plugins>
</build>
```