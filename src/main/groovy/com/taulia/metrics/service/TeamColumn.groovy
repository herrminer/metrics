package com.taulia.metrics.service

import com.taulia.metrics.model.User

class TeamColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'Team'
  }

  @Override
  String getColumnValue(User user) {
    user.team.name
  }

}
