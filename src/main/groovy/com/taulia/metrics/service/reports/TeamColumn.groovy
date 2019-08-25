package com.taulia.metrics.service.reports

import com.taulia.metrics.model.User
import com.taulia.metrics.service.reports.ReportColumn

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
