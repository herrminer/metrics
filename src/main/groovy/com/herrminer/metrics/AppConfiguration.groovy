package com.herrminer.metrics

class AppConfiguration {

    private static Properties properties

    private static List<String> excludedUsers

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
            excludedUsers = getConfiguration('github.org.excluded.users').split(',')
        }
        excludedUsers
    }

    private static initialize() {
        properties = new Properties()
        properties.load(this.getResourceAsStream('/metrics.properties') as InputStream)
    }
}
