package com.taulia.metrics.service

import com.taulia.metrics.model.User

class RoleColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'Role'
  }

  @Override
  String getColumnValue(User user) {
    user.role
  }

}
