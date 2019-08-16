package com.taulia.metrics

class PullRequest {
  /*      "url": "https://api.github.com/repos/taulia/app-buyer/issues/288",
      "repository_url": "https://api.github.com/repos/taulia/app-buyer",
      "labels_url": "https://api.github.com/repos/taulia/app-buyer/issues/288/labels{/name}",
      "comments_url": "https://api.github.com/repos/taulia/app-buyer/issues/288/comments",
      "events_url": "https://api.github.com/repos/taulia/app-buyer/issues/288/events",
      "html_url": "https://github.com/taulia/app-buyer/pull/288",
      "id": 475932652,
      "node_id": "MDExOlB1bGxSZXF1ZXN0MzAzNTkwNjA5",
      "number": 288,
      "title": "Support additional subdomain depth for global session cookie",
*/
  String url
  String title
  GithubUser user
}
