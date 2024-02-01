# SERAPH DEMONSTRATION WEBSITE

{% include youtube.html id="nr7TwK-NjgQ" %}

# A Bit of Documentation

## Query Model

![Query Model](images/querymodel.png)

## Syntax

The syntax of Seraph extends the syntax of Cypher 9 as follows:

![](images/syntax.png) 

### Usage example

### Your First Query
```
REGISTER QUERY <query_name> STARTING AT 2019-09-07T15:50:30.022+01:00 {
MATCH (a) - [b] -> (c) WITHIN PT1H WHERE a = 1 OR b = 2
EMIT c
ON EXIT 
EVERY PT10M }
```

### Use Cases

## License

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
