package com.taulia.metrics.service

import com.taulia.metrics.model.User

class UsernameColumn implements CsvColumn {

  @Override
  String getColumnHeader() {
    'User Name'
  }

  @Override
  String getColumnValue(User user) {
    user.userName
  }

}
