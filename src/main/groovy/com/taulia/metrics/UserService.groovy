package com.taulia.metrics

class UserService {
  static List<User> getUsers() {

    Map<String, Team> teams = new HashMap<>()

    List<User> users = new ArrayList<>()
    new File(getClass().getResource("/users.csv").toURI()).eachLine { line, num ->

      if (num > 1) {
        String[] parts = line.split(',')

        def user = new User(
          firstName: parts[0],
          lastName: parts[1],
          userName: parts[2],
          role: parts[4]
        )

        def teamName = parts[3]

        if (!teams.containsKey(teamName)) {
          teams.put(teamName, new Team(name: teamName))
        }

        teams.get(teamName).addUser(user)

        users.add(user)
      }
    }

    users
  }
}
