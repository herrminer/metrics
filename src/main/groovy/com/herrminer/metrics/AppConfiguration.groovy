package com.herrminer.metrics

class AppConfiguration {

    private static Properties properties

    private static List<String> excludedUsers
    private static List<String> excludedTeams

    static Properties getProperties() {
        if (properties == null) {
            initialize()
        }
        properties
    }

    static String getConfiguration(String name) {
        getProperties().getProperty(name)
    }

    static boolean getConfigurationAsBoolean(String name) {
        "yes" == getConfiguration(name) || "true" == getConfiguration(name)
    }

    static List<String> getExcludedUsers() {
        if (excludedUsers == null) {
            excludedUsers = getExcludedList('github.org.excluded.users')
        }
        excludedUsers
    }

    static List<String> getExcludedTeams() {
        if (excludedTeams == null) {
            excludedTeams = getExcludedList('github.org.excluded.teams')
        }
        excludedTeams
    }

    private static List<String> getExcludedList(String propertyName) {
        getConfiguration(propertyName).split(',')
    }

    private static initialize() {
        properties = new Properties()
        properties.load(this.getResourceAsStream('/metrics.properties') as InputStream)
    }
}
