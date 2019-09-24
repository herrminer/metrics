package com.herrminer.metrics.service.reports

import com.herrminer.metrics.model.User

class TeamColumn implements ReportColumn {

  @Override
  String getColumnHeader() {
    'Team'
  }

  @Override
  String getColumnValue(User user) {
    user.team.name
  }

}
