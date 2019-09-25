# Github Metrics Generator (GMG)

## Prerequisites

GMG was developed and tested with the following:
* Java 1.8.0_211
* Groovy 2.4.15
* Gradle 4.10

## Getting Started
1. Create a [Personal Access Token](https://help.github.com/en/enterprise/2.17/user/articles/creating-a-personal-access-token-for-the-command-line) with `repo` scope
2. Download the [source code](https://github.com/herrminer/metrics) from Github
3. Modify the following entries in `src/main/resources/metrics.properties`:
	* `github.username` Your user name on Github
	* `github.access.token` A personal access token for your account; must have `repo` scope
	* `github.org` The Github organization that owns the repositories you want to query
4. Execute the following on the command line:
```
gradle metrics
```

## Additional configuration options
Additional options in `src/main/resources/metrics.properties` are as follows:
* `fromDate` The beginning date for metrics. Must be formatted `yyyy-MM-dd`
* `toDate` The end date for metrics. Must be formatted `yyyy-MM-dd`
* `chunkSize` Chunk size (in days) used to work around [the Github Search API](https://developer.github.com/v3/search/) limitation of 1,000 search results.